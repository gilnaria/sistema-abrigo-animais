package service;

import exception.acesso.AcessoNegadoException;
import exception.saude.RegistroInvalidoException;
import exception.saude.VacinaDuplicadaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import model.saude.HistoricoAnimal;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import model.saude.Vacina;
import repository.HistoricoAnimalRepository;
import util.Formato;
import util.Permissoes;
import util.Validador;

public class SaudeService {
    public static final int DIAS_AVISO_VACINA = 7;
    private static final String MSG_SO_VETERINARIO = "[ APENAS VETERINÁRIOS PODEM ALTERAR INFORMAÇÕES MÉDICAS. ]";

    private final HistoricoAnimalRepository repositorio;

    public SaudeService(HistoricoAnimalRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarRegistro(Usuario usuario, RegistroMedico registro)
            throws AcessoNegadoException, RegistroInvalidoException, VacinaDuplicadaException {
        Permissoes.exigirVeterinario(usuario, MSG_SO_VETERINARIO);
        validar(registro);

        if (registro instanceof Vacina) {
            verificarVacinaDuplicada((Vacina) registro);
        }

        HistoricoAnimal historico = repositorio.buscarHistorico(registro.getAnimal());

        if (historico == null) {
            historico = new HistoricoAnimal(registro.getAnimal());
            repositorio.salvarHistorico(historico);
        }

        historico.adicionarRegistro(registro);

        if (registro instanceof Tratamento) {
            bloquearAdocaoSeCritico(registro.getAnimal());
        }
    }

    public void atualizarStatusTratamento(Usuario usuario, Tratamento tratamento, StatusTratamento novoStatus)
            throws AcessoNegadoException, RegistroInvalidoException {
        Permissoes.exigirVeterinario(usuario, MSG_SO_VETERINARIO);

        if (tratamento == null || novoStatus == null) {
            throw new RegistroInvalidoException("[ TRATAMENTO E STATUS SÃO OBRIGATÓRIOS. ]");
        }

        tratamento.setStatusTratamento(novoStatus);
        bloquearAdocaoSeCritico(tratamento.getAnimal());
    }

    public void atualizarEstadoSaude(Usuario usuario, Animal animal, String novoEstado)
            throws AcessoNegadoException, RegistroInvalidoException {
        Permissoes.exigirVeterinario(usuario, MSG_SO_VETERINARIO);

        if (animal == null || !Validador.textoValido(novoEstado)) {
            throw new RegistroInvalidoException("[ ANIMAL E ESTADO DE SAÚDE SÃO OBRIGATÓRIOS. ]");
        }

        animal.setEstadoSaude(novoEstado.trim());
    }

    public HistoricoAnimal buscarHistorico(Animal animal) {
        return repositorio.buscarHistorico(animal);
    }

    public List<HistoricoAnimal> listarHistoricos() {
        return repositorio.listarHistoricos();
    }

    public List<RegistroMedico> listarRegistros(Animal animal) {
        HistoricoAnimal historico = repositorio.buscarHistorico(animal);
        return historico == null ? new ArrayList<RegistroMedico>() : new ArrayList<>(historico.getRegistros());
    }

    public List<Tratamento> listarTratamentos(Animal animal) {
        HistoricoAnimal historico = repositorio.buscarHistorico(animal);
        return historico == null ? new ArrayList<Tratamento>() : historico.getTratamentos();
    }

    public List<Tratamento> listarTratamentosAtivos(Animal animal) {
        List<Tratamento> ativos = new ArrayList<>();

        for (Tratamento tratamento : listarTratamentos(animal)) {
            if (tratamento.isAtivo()) {
                ativos.add(tratamento);
            }
        }
        return ativos;
    }

    public boolean possuiTratamentoCritico(Animal animal) {
        for (Tratamento tratamento : listarTratamentos(animal)) {
            if (tratamento.getStatusTratamento() == StatusTratamento.CRITICO) {
                return true;
            }
        }
        return false;
    }

    public List<Animal> listarAnimaisEmTratamento() {
        List<Animal> animais = new ArrayList<>();

        for (HistoricoAnimal historico : repositorio.listarHistoricos()) {
            if (!listarTratamentosAtivos(historico.getAnimal()).isEmpty()) {
                animais.add(historico.getAnimal());
            }
        }
        return animais;
    }

    public List<Vacina> listarVacinasProximasVencimento() {
        List<Vacina> proximas = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(DIAS_AVISO_VACINA);

        for (HistoricoAnimal historico : repositorio.listarHistoricos()) {
            for (Vacina vacina : historico.getVacinas()) {
                LocalDate proxima = vacina.getDataProximaDose();

                if (proxima != null && !proxima.isBefore(hoje) && !proxima.isAfter(limite)) {
                    proximas.add(vacina);
                }
            }
        }
        return proximas;
    }

    public String emitirRelatorioSaude(Usuario usuario, Animal animal) throws AcessoNegadoException,
            RegistroInvalidoException {
        Permissoes.exigirAdministradorOuVeterinario(usuario,
                "[ APENAS VETERINÁRIOS E ADMINISTRADORES PODEM EMITIR RELATÓRIOS DE SAÚDE. ]");

        if (animal == null) {
            throw new RegistroInvalidoException("[ ANIMAL É OBRIGATÓRIO. ]");
        }

        List<RegistroMedico> registros = listarRegistros(animal);
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("RELATÓRIO DE SAÚDE - ").append(animal.getNome())
                .append(" (código ").append(animal.getCodigo()).append(")\n");
        relatorio.append("Espécie: ").append(animal.getEspecie()).append(" | Raça: ").append(animal.getRaca()).append("\n");
        relatorio.append("Estado de saúde atual: ").append(animal.getEstadoSaude()).append("\n");
        relatorio.append("Status de adoção: ").append(animal.getStatusAdocao()).append("\n");
        relatorio.append("Tratamento crítico: ").append(possuiTratamentoCritico(animal) ? "SIM" : "NÃO").append("\n");
        relatorio.append("Emitido em: ").append(Formato.data(LocalDate.now())).append(" por ")
                .append(usuario.getNome()).append("\n");
        relatorio.append("Total de registros: ").append(registros.size()).append("\n");

        for (RegistroMedico registro : registros) {
            relatorio.append("\n").append(registro.resumo()).append("\n");
        }
        return relatorio.toString();
    }

    private void validar(RegistroMedico registro) throws RegistroInvalidoException {
        if (registro == null || registro.getAnimal() == null) {
            throw new RegistroInvalidoException("[ REGISTRO E ANIMAL SÃO OBRIGATÓRIOS. ]");
        }
        if (!Validador.textoValido(registro.getVeterinario()) || !Validador.textoValido(registro.getDescricao())) {
            throw new RegistroInvalidoException("[ VETERINÁRIO E DESCRIÇÃO SÃO OBRIGATÓRIOS. ]");
        }
        if (registro.getDataRegistroMedico() == null || registro.getDataRegistroMedico().isAfter(LocalDate.now())) {
            throw new RegistroInvalidoException("[ DATA DO REGISTRO NÃO PODE ESTAR NO FUTURO. ]");
        }
        if (registro instanceof Vacina) {
            Vacina vacina = (Vacina) registro;

            if (!Validador.textoValido(vacina.getNomeVacina())) {
                throw new RegistroInvalidoException("[ NOME DA VACINA É OBRIGATÓRIO. ]");
            }
            if (vacina.getDataProximaDose() != null
                    && !vacina.getDataProximaDose().isAfter(vacina.getDataRegistroMedico())) {
                throw new RegistroInvalidoException("[ PRÓXIMA DOSE DEVE SER POSTERIOR À APLICAÇÃO. ]");
            }
        }
    }

    private void verificarVacinaDuplicada(Vacina nova) throws VacinaDuplicadaException {
        HistoricoAnimal historico = repositorio.buscarHistorico(nova.getAnimal());

        if (historico == null) {
            return;
        }

        for (Vacina existente : historico.getVacinas()) {
            boolean mesmaVacina = existente.getNomeVacina().equalsIgnoreCase(nova.getNomeVacina());
            boolean mesmaData = existente.getDataRegistroMedico().equals(nova.getDataRegistroMedico());

            if (mesmaVacina && mesmaData) {
                throw new VacinaDuplicadaException("[ VACINA JÁ REGISTRADA NESTA DATA PARA ESTE ANIMAL. ]");
            }
        }
    }

    private void bloquearAdocaoSeCritico(Animal animal) {
        if (possuiTratamentoCritico(animal) && !animal.isAdotado()) {
            animal.setStatusAdocao(Animal.StatusAdocao.EM_TRATAMENTO);
        }
    }
}
