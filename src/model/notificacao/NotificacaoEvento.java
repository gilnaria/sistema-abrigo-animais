package model.notificacao;

import java.time.format.DateTimeFormatter;

import model.atividades.Evento;

public class NotificacaoEvento implements Notificavel {
    private final Evento evento;

    public NotificacaoEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.PROXIMO_EVENTO;
    }

    @Override
    public String gerarMensagemNotificacao() {
        return "Próximo evento: " + evento.getNomeEvento() + " (" + evento.getTipo() + ") em "
                + evento.getDataEvento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às "
                + evento.getHorarioEvento() + ", local: " + evento.getLocal() + ".";
    }
}