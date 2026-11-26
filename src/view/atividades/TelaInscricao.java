package view.atividades;

import controller.InscricoesController;
import controller.EventosController;
import exception.AbrigoException;
import model.acesso.Voluntario;
import model.atividades.Evento;
import model.atividades.Inscricao;
import util.Entrada;
import view.TelaBase;

public class TelaInscricao extends TelaBase {
    private final InscricoesController inscricoes;
    private final EventosController eventos;

    public TelaInscricao(InscricoesController inscricoes, EventosController eventos) {
        this.inscricoes = inscricoes;
        this.eventos = eventos;
    }

    public void exibirMenu(Voluntario voluntario) {
        int opcao;

        do {
            menu("ATIVIDADES DE APOIO",
                    "1 - Ver atividades disponíveis",
                    "2 - Inscrever-me em uma atividade",
                    "3 - Meu histórico de participações",
                    "4 - Cancelar uma inscrição",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    exibirEventos();
                    break;
                case 2:
                    inscrever(voluntario);
                    break;
                case 3:
                    listar("HISTÓRICO DE PARTICIPAÇÕES", inscricoes.listarHistorico(voluntario));
                    break;
                case 4:
                    cancelar(voluntario);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void exibirEventos() {
        titulo("ATIVIDADES DISPONÍVEIS");

        if (eventos.listarEventosDisponiveis().isEmpty()) {
            aviso("Não há atividades disponíveis.");
            return;
        }

        for (Evento evento : eventos.listarEventosDisponiveis()) {
            linha();
            evento.exibirInfo();
            System.out.println("Vagas restantes: " + inscricoes.vagasRestantes(evento));
        }
        linha();
    }

    private void inscrever(Voluntario voluntario) {
        Evento evento = (Evento) escolher("ESCOLHA A ATIVIDADE", eventos.listarEventosDisponiveis());

        if (evento == null) {
            return;
        }

        try {
            inscricoes.realizarInscricao(voluntario, evento);
            sucesso("Inscrição efetuada com sucesso.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void cancelar(Voluntario voluntario) {
        Inscricao inscricao = (Inscricao) escolher("ESCOLHA A INSCRIÇÃO", inscricoes.listarHistorico(voluntario));

        if (inscricao == null) {
            return;
        }

        try {
            inscricoes.cancelarInscricao(inscricao);
            sucesso("Inscrição cancelada.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}