package exception.animal;

import exception.AbrigoException;

public class RemocaoProibidaException extends AbrigoException {
    public RemocaoProibidaException(String msg) {
        super(msg);
    }
}