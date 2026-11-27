package model.notificacao;

import model.atividades.Evento;
import util.Formato;

public class NotificacaoEvento implements Notificavel {
    private final Evento evento;
    private final boolean lembrete;

    public NotificacaoEvento(Evento evento, boolean lembrete) {
        this.evento = evento;
        this.lembrete = lembrete;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.PROXIMO_EVENTO;
    }

    @Override
    public String gerarMensagemNotificacao() {
        String data = Formato.data(evento.getDataEvento());

        if (lembrete) {
            return "Lembrete: " + evento.getNomeEvento() + " acontece em " + data + ".";
        }
        return "Novo evento cadastrado: " + evento.getNomeEvento() + " em " + data + ".";
    }
}