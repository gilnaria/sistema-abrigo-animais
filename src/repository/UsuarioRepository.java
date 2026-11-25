package repository;

import model.acesso.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarPorCpf(String cpf){
        for(Usuario usuario : usuarios){
            if(usuario.getCpf().equals(cpf)){
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email){
        for(Usuario usuario : usuarios){
            if(usuario.getEmail().equalsIgnoreCase(email)){
                return usuario;
            }
        }
        return null;
    }

    public boolean removerUsuario(Usuario usuario){
        return usuarios.remove(usuario);
    }

    public boolean editarUsuario(Usuario usuarioAtualizado){
        for(int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getCpf().equals(usuarioAtualizado.getCpf())){
                usuarios.set(i, usuarioAtualizado);
                return true;
            }
        }
        return false;
    }
}
