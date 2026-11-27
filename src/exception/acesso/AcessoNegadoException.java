package exception.acesso;

import exception.AbrigoException;

public class AcessoNegadoException extends AbrigoException {
    public AcessoNegadoException(String msg){
        super(msg);
    }
}
