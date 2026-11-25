package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.saude.AcessoNegadoException;
import exception.saude.RegistroInvalidoException;
import exception.saude.VacinaDuplicadaException;
import model.Animal;
import model.acesso.Usuario;
import model.acesso.Veterinario;
import model.saude.HistoricoAnimal;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import model.saude.Vacina;
import repository.HistoricoAnimalRepository;

public class SaudeService {
    public static final int DIAS_AVISO_VACINA = 7;

    private HistoricoAnimalRepository repositorio;

    public SaudeService(HistoricoAnimalRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void adicionarRegistro(Usuario usuario, RegistroMedico registro)
            throws AcessoNegadoException, RegistroInvalidoException, VacinaDuplicadaException {
        exigirVeterinario(usuario);

        if (registro == null || registro.getAnimal() == null) {
            throw new RegistroInvalidoException("[ REGISTRO E ANIMAL SÃO OBRIGATÓRIOS. ]");
        }

        if (registro instanceof Vacina) {
            verificarVacinaDuplicada((Vacina) registro);
        }

        HistoricoAnimal historico = repositorio.buscarHistorico(registro.getAnimal());

        if (historico != null) {
            historico.adicionarRegistro(registro);
        } else {
            historico = new HistoricoAnimal(registro.getAnimal());
            historico.adicionarRegistro(registro);
            repositorio.salvarHistorico(historico);
        }
    }

    public HistoricoAnimal buscarHistorico(Animal animal) {
        return repositorio.buscarHistorico(animal);
    }

    public List<HistoricoAnimal> listarHistoricos() {
        return repositorio.listarHistoricos();
    }

    public void atualizarStatusTratamento(Usuario usuario, Tratamento tratamento, StatusTratamento novoStatus)
            throws AcessoNegadoException, RegistroInvalidoException {
        exigirVeterinario(usuario);

        if (tratamento == null || novoStatus == null) {
            throw new RegistroInvalidoException("[ TRATAMENTO E STATUS SÃO OBRIGATÓRIOS. ]");
        }

        tratamento.setStatusTratamento(novoStatus);
    }

    public List<Tratamento> listarTratamentos(Animal animal) {
        List<Tratamento> tratamentos = new ArrayList<>();
        HistoricoAnimal historico = repositorio.buscarHistorico(animal);

        if (historico == null) {
            return tratamentos;
        }

        for (RegistroMedico registro : historico.getRegistros()) {
            if (registro instanceof Tratamento) {
                tratamentos.add((Tratamento) registro);
            }
        }

        return tratamentos;
    }

    public List<Tratamento> listarTratamentosAtivos(Animal animal) {
        List<Tratamento> ativos = new ArrayList<>();

        for (Tratamento tratamento : listarTratamentos(animal)) {
            
            if (tratamento.getStatusTratamento() != StatusTratamento.CONCLUIDO) {
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

    public List<Vacina> listarVacinasProximasVencimento() {
        List<Vacina> proximas = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(DIAS_AVISO_VACINA);

        for (HistoricoAnimal historico : listarHistoricos()) {
            for (RegistroMedico registro : historico.getRegistros()) {
                if (registro instanceof Vacina) {
                    Vacina vacina = (Vacina) registro;
                    LocalDate proxima = vacina.getDataProximaDose();

                    if (proxima != null && !proxima.isBefore(hoje) && !proxima.isAfter(limite)) {
                        proximas.add(vacina);
                    }
                }
            }
        }

        return proximas;
    }

    private void verificarVacinaDuplicada(Vacina nova) throws VacinaDuplicadaException {
        HistoricoAnimal historico = repositorio.buscarHistorico(nova.getAnimal());

        if (historico == null) {
            return;
        }

        for (RegistroMedico registro : historico.getRegistros()) {
            if (registro instanceof Vacina) {
                Vacina existente = (Vacina) registro;
                boolean mesmaVacina = existente.getNomeVacina().equalsIgnoreCase(nova.getNomeVacina());
                boolean mesmaData = existente.getDataRegistroMedico().equals(nova.getDataRegistroMedico());

                if (mesmaVacina && mesmaData) {
                    throw new VacinaDuplicadaException("[ VACINA JÁ REGISTRADA NESTA DATA. ]");
                }
            }
        }
    }

    private void exigirVeterinario(Usuario usuario) throws AcessoNegadoException {
        if (!(usuario instanceof Veterinario)) {
            throw new AcessoNegadoException("[ APENAS VETERINÁRIOS PODEM REALIZAR ESTA AÇÃO. ]");
        }
    }
}
