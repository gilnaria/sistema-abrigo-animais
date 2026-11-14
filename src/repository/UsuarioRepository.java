package repository;

import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UsuarioRepository {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void adicionar(Usuario usuario) {
        usuarios.add(usuario);
    }
}
