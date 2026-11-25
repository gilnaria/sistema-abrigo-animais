package model.notificacao;

public interface Notificavel {
    TipoNotificacao  getTipoNotificacao();

    String gerarMensagemNotificacao();
    
}
