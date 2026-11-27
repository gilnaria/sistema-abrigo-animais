package util;

import exception.acesso.AcessoNegadoException;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.acesso.Veterinario;

public final class Permissoes {
    private Permissoes() {
    }

    public static void exigirAdministrador(Usuario usuario, String mensagem) throws AcessoNegadoException {
        if (!(usuario instanceof Administrador)) {
            throw new AcessoNegadoException(mensagem);
        }
    }

    public static void exigirVeterinario(Usuario usuario, String mensagem) throws AcessoNegadoException {
        if (!(usuario instanceof Veterinario)) {
            throw new AcessoNegadoException(mensagem);
        }
    }

    public static void exigirAdministradorOuVeterinario(Usuario usuario, String mensagem)
            throws AcessoNegadoException {
        if (!(usuario instanceof Administrador) && !(usuario instanceof Veterinario)) {
            throw new AcessoNegadoException(mensagem);
        }
    }
}
