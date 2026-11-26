package view.atividades;

import controller.EventosController;
import controller.InscricoesController;
import exception.AbrigoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.acesso.Usuario;
import model.atividades.Evento;
import model.atividades.Inscricao;
import model.atividades.TipoAtividade;
import util.Entrada;
import view.TelaBase;

public class TelaCadastroEvento extends TelaBase {
    private final EventosController eventos;
    private final InscricoesController inscricoes;

    public TelaCadastroEvento(EventosController eventos, InscricoesController inscricoes) {
        this.eventos = eventos;
        this.inscricoes = inscricoes;
    }

    public void exibirMenuEventos(Usuario administrador) {
        int opcao;

        do {
            menu("MENU DE ATIVIDADES",
                    "1 - Cadastrar atividade",
                    "2 - Listar atividades",
                    "3 - Consultar atividade e inscritos",
                    "4 - Cancelar atividade",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    cadastrar(administrador);
                    break;
                case 2:
                    listar("ATIVIDADES CADASTRADAS", eventos.listarEventos());
                    break;
                case 3:
                    consultar();
                    break;
                case 4:
                    cancelar(administrador);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar(Usuario administrador) {
        titulo("CADASTRO DE ATIVIDADE");

        String nome = Entrada.lerTexto("Nome");
        TipoAtividade tipo = (TipoAtividade) Entrada.lerEscolha("Tipo de atividade:", TipoAtividade.values());
        String local = Entrada.lerTexto("Local");
        LocalDate data = Entrada.lerData("Data");
        LocalTime horario = Entrada.lerHora("Horário");
        int vagas = Entrada.lerInt("Vagas disponíveis");

        try {
            eventos.cadastrarEvento(administrador, new Evento(nome, tipo, local, data, horario, vagas));
            sucesso("Atividade cadastrada.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void consultar() {
        Evento evento = (Evento) escolher("ESCOLHA A ATIVIDADE", eventos.listarEventos());

        if (evento == null) {
            return;
        }

        linha();
        evento.exibirInfo();
        System.out.println("Vagas restantes: " + inscricoes.vagasRestantes(evento));
        linha();

        List<Inscricao> inscritos = inscricoes.listarInscritos(evento);
        System.out.println("Voluntários inscritos: " + inscritos.size());

        for (Inscricao inscricao : inscritos) {
            System.out.println(" - " + inscricao.getVoluntario().getNome());
        }
    }

    private void cancelar(Usuario administrador) {
        Evento evento = (Evento) escolher("ESCOLHA A ATIVIDADE A CANCELAR", eventos.listarEventos());

        if (evento == null || !Entrada.confirmar("Confirma o cancelamento de " + evento.getNomeEvento() + "?")) {
            return;
        }

        try {
            eventos.remover(administrador, evento);
            sucesso("Atividade cancelada e inscrições removidas.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}
