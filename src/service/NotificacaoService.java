package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.AcessoNegadoException;
import exception.NotificacaoInvalidaException;
import model.Adocao;
import model.Animal;
import model.acesso.Administrador;
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

public class NotificacaoService {
    public static final int DIAS_AVISO_EVENTO = 7;

    private List<Notificacao> notificacoes = new ArrayList<>();
    private SaudeService saudeService;

    public NotificacaoService(SaudeService saudeService) {
        this.saudeService = saudeService;
    }

    public void notificar(Notificavel item) throws NotificacaoInvalidaException {
        if (item == null) {
            throw new NotificacaoInvalidaException("[ O ITEM A NOTIFICAR É OBRIGATÓRIO. ]");
        }

        notificacoes.add(new Notificacao(item.getTipoNotificacao(), item.gerarMensagemNotificacao()));
    }

    public void notificarNovoAnimal(Animal animal) throws NotificacaoInvalidaException {
        if (animal == null) {
            throw new NotificacaoInvalidaException("[ O ANIMAL É OBRIGATÓRIO. ]");
        }

        notificar(new NotificacaoNovoAnimal(animal));
    }

    public void notificarEvento(Evento evento) throws NotificacaoInvalidaException {
        if (evento == null) {
            throw new NotificacaoInvalidaException("[ O EVENTO É OBRIGATÓRIO. ]");
        }

        notificar(new NotificacaoEvento(evento));
    }

    public void notificarAtualizacaoAdocao(Adocao adocao) throws NotificacaoInvalidaException {
        if (adocao == null) {
            throw new NotificacaoInvalidaException("[ A ADOÇÃO É OBRIGATÓRIA. ]");
        }

        notificar(new NotificacaoAdocao(adocao));
    }

    public void notificarAvisoGeral(Usuario usuario, String mensagem)
            throws AcessoNegadoException, NotificacaoInvalidaException {
        exigirAdministrador(usuario);

        if (mensagem == null || mensagem.isBlank()) {
            throw new NotificacaoInvalidaException("[ A MENSAGEM DO AVISO É OBRIGATÓRIA. ]");
        }

        notificar(new AvisoGeral(mensagem));
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
                notificarSeNovo(new NotificacaoEvento(evento));
            }
        }
    }

    public List<Notificacao> listarNotificacoes() {
        return new ArrayList<>(notificacoes);
    }

    public List<Notificacao> listarNaoLidas() {
        List<Notificacao> naoLidas = new ArrayList<>();

        for (Notificacao notificacao : notificacoes) {
            if (!notificacao.isLida()) {
                naoLidas.add(notificacao);
            }
        }

        return naoLidas;
    }

    public void marcarTodasComoLidas() {
        for (Notificacao notificacao : notificacoes) {
            notificacao.marcarComoLida();
        }
    }

    private void notificarSeNovo(Notificavel item) {
        String mensagem = item.gerarMensagemNotificacao();

        for (Notificacao notificacao : notificacoes) {
            if (notificacao.getTipo() == item.getTipoNotificacao() && notificacao.getMensagem().equals(mensagem)) {
                return;
            }
        }

        notificacoes.add(new Notificacao(item.getTipoNotificacao(), mensagem));
    }

    private void exigirAdministrador(Usuario usuario) throws AcessoNegadoException {
        if (!(usuario instanceof Administrador)) {
            throw new AcessoNegadoException("[ APENAS ADMINISTRADORES PODEM PUBLICAR AVISOS. ]");
        }
    }
}