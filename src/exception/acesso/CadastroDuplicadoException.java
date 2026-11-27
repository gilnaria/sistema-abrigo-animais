package exception.acesso;

import exception.AbrigoException;

public class CadastroDuplicadoException extends AbrigoException {
    public CadastroDuplicadoException(String msg) {
        super(msg);
    }
}
