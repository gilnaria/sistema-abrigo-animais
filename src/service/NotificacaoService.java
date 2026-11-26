package service;

import exception.acesso.AcessoNegadoException;
import exception.notificacao.NotificacaoInvalidaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.acesso.Usuario;
import model.atividades.Evento;
import model.notificacao.AvisoGeral;
import model.notificacao.Notificacao;
import model.notificacao.NotificacaoAdocao;
import model.notificacao.NotificacaoEvento;
import model.notificacao.NotificacaoNovoAnimal;
import model.notificacao.NotificacaoVacina;
import model.notificacao.Notificavel;
import model.saude.Vacina;
import util.Permissoes;
import util.Validador;

public class NotificacaoService {
    public static final int DIAS_AVISO_EVENTO = 7;

    private final List<Notificacao> notificacoes = new ArrayList<>();
    private final SaudeService saudeService;

    public NotificacaoService(SaudeService saudeService) {
        this.saudeService = saudeService;
    }

    public void notificar(Notificavel item) {
        if (item == null) {
            throw new NotificacaoInvalidaException("[ O ITEM A NOTIFICAR É OBRIGATÓRIO. ]");
        }

        notificacoes.add(new Notificacao(item.getTipoNotificacao(), item.gerarMensagemNotificacao()));
    }

    public void notificarNovoAnimal(Animal animal) {
        if (animal == null) {
            throw new NotificacaoInvalidaException("[ O ANIMAL É OBRIGATÓRIO. ]");
        }

        notificar(new NotificacaoNovoAnimal(animal));
    }

    public void notificarEvento(Evento evento) {
        if (evento == null) {
            throw new NotificacaoInvalidaException("[ O EVENTO É OBRIGATÓRIO. ]");
        }

        notificar(new NotificacaoEvento(evento, false));
    }

    public void notificarAtualizacaoAdocao(Adocao adocao) {
        if (adocao == null) {
            throw new NotificacaoInvalidaException("[ A ADOÇÃO É OBRIGATÓRIA. ]");
        }

        notificar(new NotificacaoAdocao(adocao));
    }

    public void notificarAvisoGeral(Usuario usuario, String mensagem) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM PUBLICAR AVISOS. ]");

        if (!Validador.textoValido(mensagem)) {
            throw new NotificacaoInvalidaException("[ A MENSAGEM DO AVISO É OBRIGATÓRIA. ]");
        }

        notificar(new AvisoGeral(mensagem.trim()));
    }

    public void verificarVacinasProximas() {
        for (Vacina vacina : saudeService.listarVacinasProximasVencimento()) {
            notificarSeNovo(new NotificacaoVacina(vacina));
        }
    }

    public void verificarEventosProximos(List<Evento> eventos) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(DIAS_AVISO_EVENTO);

        for (Evento evento : eventos) {
            LocalDate data = evento.getDataEvento();

            if (!data.isBefore(hoje) && !data.isAfter(limite)) {
                notificarSeNovo(new NotificacaoEvento(evento, true));
            }
        }
    }

    public int contarNaoLidas(Usuario usuario, List<Evento> eventos) {
        verificarVacinasProximas();
        verificarEventosProximos(eventos);
        return listarNaoLidas(usuario).size();
    }

    public List<Notificacao> listarNotificacoes() {
        return new ArrayList<>(notificacoes);
    }

    public List<Notificacao> listarNaoLidas(Usuario usuario) {
        List<Notificacao> naoLidas = new ArrayList<>();

        for (Notificacao notificacao : notificacoes) {
            if (!notificacao.foiLidaPor(usuario)) {
                naoLidas.add(notificacao);
            }
        }
        return naoLidas;
    }

    public void marcarTodasComoLidas(Usuario usuario) {
        for (Notificacao notificacao : notificacoes) {
            notificacao.marcarComoLida(usuario);
        }
    }

    private void notificarSeNovo(Notificavel item) {
        String mensagem = item.gerarMensagemNotificacao();

        for (Notificacao notificacao : notificacoes) {
            if (notificacao.getTipo() == item.getTipoNotificacao() && notificacao.getMensagem().equals(mensagem)) {
                return;
            }
        }

        notificar(item);
    }
}