package exception.acesso;

import exception.AbrigoException;

public class CredenciaisInvalidasException extends AbrigoException {
    public CredenciaisInvalidasException(String msg) {
        super(msg);
    }
}