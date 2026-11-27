package exception.animal;

import exception.AbrigoException;

public class AnimalNaoEncontradoException extends AbrigoException {
    public AnimalNaoEncontradoException(String msg) {
        super(msg);
    }
}
