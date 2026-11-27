package exception.adocao;

import exception.AbrigoException;

public class AnimalIndisponivelException extends AbrigoException {
    public AnimalIndisponivelException(String msg) {
        super(msg);
    }
}