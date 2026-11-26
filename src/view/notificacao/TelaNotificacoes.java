package view.notificacao;

import controller.NotificacaoController;
import exception.AbrigoException;
import java.util.List;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.notificacao.Notificacao;
import util.Entrada;
import view.TelaBase;

public class TelaNotificacoes extends TelaBase {
    private final NotificacaoController notificacoes;

    public TelaNotificacoes(NotificacaoController notificacoes) {
        this.notificacoes = notificacoes;
    }

    public void exibirMenu(Usuario usuario) {
        int opcao;

        do {
            if (usuario instanceof Administrador) {
                menu("NOTIFICAÇÕES",
                        "1 - Ver notificações novas",
                        "2 - Ver todas as notificações",
                        "3 - Publicar aviso geral",
                        "0 - Voltar");
            } else {
                menu("NOTIFICAÇÕES",
                        "1 - Ver notificações novas",
                        "2 - Ver todas as notificações",
                        "0 - Voltar");
            }
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    exibirNaoLidas(usuario);
                    break;
                case 2:
                    exibir("TODAS AS NOTIFICAÇÕES", notificacoes.listarTodas());
                    break;
                case 3:
                    if (usuario instanceof Administrador) {
                        publicarAviso(usuario);
                    } else {
                        erro("Opção inválida.");
                    }
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void exibirResumo(Usuario usuario) {
        int quantidade = notificacoes.contarNaoLidas(usuario);

        if (quantidade > 0) {
            aviso("Você tem " + quantidade + " notificação(ões) nova(s).");
        }
    }

    private void exibirNaoLidas(Usuario usuario) {
        exibir("NOTIFICAÇÕES NOVAS", notificacoes.obterNaoLidas(usuario));
        notificacoes.marcarTodasComoLidas(usuario);
    }

    private void exibir(String titulo, List<Notificacao> notificacoes) {
        titulo(titulo);

        if (notificacoes.isEmpty()) {
            aviso("Nenhuma notificação.");
            return;
        }

        for (Notificacao notificacao : notificacoes) {
            notificacao.exibirInfo();
        }
    }

    private void publicarAviso(Usuario administrador) {
        String mensagem = Entrada.lerTexto("Mensagem do aviso");

        try {
            notificacoes.publicarAvisoGeral(administrador, mensagem);
            sucesso("Aviso publicado.");
        } catch (AbrigoException | RuntimeException e) {
            tratarErro(e);
        }
    }
}
