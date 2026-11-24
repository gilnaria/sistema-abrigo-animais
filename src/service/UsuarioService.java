package service;

import repository.UsuarioRepository;
import service.CandidaturaService;
import model.Candidatura;
import model.acesso.Administrador;
import model.acesso.Usuario;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CandidaturaService candidaturaService;

    public UsuarioService (UsuarioRepository usuarioRepository, CandidaturaService candidaturaService){
        this.usuarioRepository = usuarioRepository;
        this.candidaturaService = candidaturaService;
    }

    public void cadastrarUsuario(Usuario usuario){
        if(usuario == null){
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        if(!(usuario instanceof Administrador)){
            //Exception
        }
        if(usuario.getNome() == null || usuario.getNome().isBlank()){
            //Exception
        }
        if(usuario.getCpf() == null || usuario.getCpf().length() < 11){
            //Exception
        }
        if(usuario.getEmail() == null || usuario.getEmail().isBlank()){
            //Exception
        }
        if(usuarioRepository.buscarPorCpf(usuario.getCpf()) != null){
            //Exception CPF já cadastrado
        }
        if(usuarioRepository.buscarPorEmail(usuario.getEmail()) != null){
            //Exception email já cadastrado
        } 
        
        usuarioRepository.adicionarUsuario(usuario);
    }

    public boolean autenticar(String email, String senha){
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if(usuario == null){
            return false;
        }
        if(usuario.getEmail().equalsIgnoreCase(email) && usuario.verificarSenha(senha)){
            return true;
        }

        return false;
    }

    public List<Usuario> listarUsuario(){
        return usuarioRepository.listarUsuarios();
    }

    public boolean removerUsuario(Usuario usuario){
        if(usuario == null){
            return false;
        }
        if(!(usuario instanceof Administrador)){
            //exception
        }

        return usuarioRepository.removerUsuario(usuario);
    }

    public boolean editarUsuario(Usuario usuarioAtualizado){
        if(usuarioAtualizado == null){
            //Exception
        }
        if(usuarioAtualizado.getNome() == null || usuarioAtualizado.getNome().isBlank()){
            //Exception
        }
        if(usuarioAtualizado.getEmail() == null || usuarioAtualizado.getEmail().isBlank()){
            //Exception
        }

        Usuario usuarioExistente = usuarioRepository.buscarPorCpf(usuarioAtualizado.getCpf());

        if (usuarioExistente == null) {
            // Exception: usuário não encontrado
        }

        Usuario usuarioEncontrado = usuarioRepository.buscarPorEmail(usuarioAtualizado.getEmail());

        if(usuarioEncontrado != null && !usuarioEncontrado.getCpf().equals(usuarioAtualizado.getCpf())){
            //Exception email já cadastrado
        } 

        return usuarioRepository.editarUsuario(usuarioAtualizado);
    }

    public void aprovarCandidatura(Usuario usuario, Candidatura candidatura){
        if(usuario == null){
            //exception
        }
        if(!(usuario instanceof Administrador)){
            //exception
        }

        candidaturaService.aprovarCandidatura(candidatura);
    }

    public void rejeitarCandidatura(Usuario usuario, Candidatura candidatura){
        if(usuario == null){
            //exception
        }
        if(!(usuario instanceof Administrador)){
            //exception
        }

        candidaturaService.rejeitarCandidatura(candidatura);
    }
}
