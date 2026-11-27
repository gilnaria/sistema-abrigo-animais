package view.saude;

import controller.AnimalController;
import controller.SaudeController;
import controller.RelatorioController;
import exception.AbrigoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import model.saude.Cirurgia;
import model.saude.Consulta;
import model.saude.Medicamento;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import model.saude.Vacina;
import util.Entrada;
import view.TelaBase;

public class TelaCadastroRegistro extends TelaBase {
    private final AnimalController animais;
    private final SaudeController saude;
    private final RelatorioController relatorios;

    public TelaCadastroRegistro(AnimalController animais, SaudeController saude, RelatorioController relatorios) {
        this.animais = animais;
        this.saude = saude;
        this.relatorios = relatorios;
    }

    public void exibirMenuSaude(Usuario veterinario) {
        int opcao;

        do {
            menu("CONTROLE DE SAÚDE",
                    "1 - Registrar consulta",
                    "2 - Registrar vacina",
                    "3 - Registrar medicamento",
                    "4 - Registrar cirurgia",
                    "5 - Registrar tratamento",
                    "6 - Atualizar status de tratamento",
                    "7 - Atualizar estado de saúde do animal",
                    "8 - Ver histórico médico completo",
                    "9 - Emitir relatório de saúde",
                    "10 - Animais em tratamento",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    registrarConsulta(veterinario);
                    break;
                case 2:
                    registrarVacina(veterinario);
                    break;
                case 3:
                    registrarMedicamento(veterinario);
                    break;
                case 4:
                    registrarCirurgia(veterinario);
                    break;
                case 5:
                    registrarTratamento(veterinario);
                    break;
                case 6:
                    atualizarStatus(veterinario);
                    break;
                case 7:
                    atualizarEstado(veterinario);
                    break;
                case 8:
                    exibirHistorico();
                    break;
                case 9:
                    emitirRelatorio(veterinario);
                    break;
                case 10:
                    exibirEmTratamento(veterinario);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private Animal escolherAnimal() {
        return (Animal) escolher("ESCOLHA O ANIMAL", animais.listarTodos());
    }

    private void registrar(Usuario veterinario, RegistroMedico registro) {
        try {
            saude.adicionarRegistro(veterinario, registro);
            sucesso("Registro salvo no histórico de " + registro.getAnimal().getNome() + ".");

            if (registro instanceof Tratamento && ((Tratamento) registro).getStatusTratamento() == StatusTratamento.CRITICO) {
                aviso("Animal em tratamento crítico: indisponível para adoção.");
            }
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void registrarConsulta(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        LocalDate data = Entrada.lerData("Data da consulta");
        String motivo = Entrada.lerTexto("Motivo");
        String diagnostico = Entrada.lerTexto("Diagnóstico");
        String observacoes = Entrada.lerTexto("Observações");

        registrar(veterinario, new Consulta(animal, data, veterinario.getNome(), motivo, diagnostico, observacoes));
    }

    private void registrarVacina(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        LocalDate data = Entrada.lerData("Data da aplicação");
        String nome = Entrada.lerTexto("Nome da vacina");
        String descricao = Entrada.lerTexto("Descrição");
        LocalDate proxima = null;

        if (Entrada.confirmar("Há próxima dose?")) {
            proxima = Entrada.lerData("Data da próxima dose");
        }

        registrar(veterinario, new Vacina(animal, data, veterinario.getNome(), descricao, nome, proxima));
    }

    private void registrarMedicamento(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        LocalDate data = Entrada.lerData("Data da prescrição");
        String nome = Entrada.lerTexto("Medicamento");
        String dosagem = Entrada.lerTexto("Dosagem");
        LocalTime horario = Entrada.lerHora("Horário de administração");
        String descricao = Entrada.lerTexto("Descrição");

        registrar(veterinario, new Medicamento(animal, data, veterinario.getNome(), descricao, nome, dosagem,
                horario));
    }

    private void registrarCirurgia(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        LocalDate data = Entrada.lerData("Data do registro");
        String nome = Entrada.lerTexto("Nome da cirurgia");
        LocalDate dataCirurgia = Entrada.lerData("Data da cirurgia");
        LocalTime horario = Entrada.lerHora("Horário da cirurgia");
        String resultado = Entrada.lerTexto("Resultado");
        String descricao = Entrada.lerTexto("Descrição");

        registrar(veterinario, new Cirurgia(animal, data, veterinario.getNome(), descricao, nome, dataCirurgia,
                horario, resultado));
    }

    private void registrarTratamento(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        LocalDate data = Entrada.lerData("Data de início");
        String tipo = Entrada.lerTexto("Tipo de tratamento");
        String duracao = Entrada.lerTexto("Duração prevista");
        String descricao = Entrada.lerTexto("Descrição");
        StatusTratamento status = (StatusTratamento) Entrada.lerEscolha("Status:", StatusTratamento.values());

        registrar(veterinario, new Tratamento(animal, data, veterinario.getNome(), descricao, tipo, duracao, status));
    }

    private void atualizarStatus(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        Tratamento tratamento = (Tratamento) escolher("TRATAMENTOS ATIVOS", saude.listarTratamentosAtivos(animal));

        if (tratamento == null) {
            return;
        }

        StatusTratamento novo = (StatusTratamento) Entrada.lerEscolha("Novo status:", StatusTratamento.values());

        try {
            saude.atualizarStatusTratamento(veterinario, tratamento, novo);
            sucesso("Status atualizado.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void atualizarEstado(Usuario veterinario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        String estado = Entrada.lerTexto("Novo estado de saúde [" + animal.getEstadoSaude() + "]");

        try {
            saude.atualizarEstadoSaude(veterinario, animal, estado);
            sucesso("Estado de saúde atualizado.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void exibirHistorico() {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        List<RegistroMedico> registros = saude.listarRegistros(animal);
        titulo("HISTÓRICO MÉDICO - " + animal.getNome());

        if (registros.isEmpty()) {
            aviso("Sem registros médicos.");
            return;
        }

        for (RegistroMedico registro : registros) {
            registro.exibirInfo();
            linha();
        }
    }

    public void emitirRelatorioAdministrador(Usuario administrador) {
        emitirRelatorio(administrador);
    }

    private void emitirRelatorio(Usuario usuario) {
        Animal animal = escolherAnimal();

        if (animal == null) {
            return;
        }

        try {
            linha();
            System.out.println(saude.emitirRelatorioSaude(usuario, animal));
            linha();
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void exibirEmTratamento(Usuario usuario) {
        try {
            listar("ANIMAIS EM TRATAMENTO", relatorios.animaisEmTratamento(usuario));
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}
