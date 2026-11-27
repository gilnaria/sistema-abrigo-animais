package exception.notificacao;

public class NotificacaoInvalidaException extends RuntimeException {
    public NotificacaoInvalidaException(String msg) {
        super(msg);
    }
}