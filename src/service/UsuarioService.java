package service;

import repository.UsuarioRepository;
import service.CandidaturaService;
import model.Candidatura;
import model.acesso.Administrador;
import model.acesso.Usuario;
import exception.CpfCadastradoException;
import exception.DadoInvalidoException;
import exception.EmailCadastradoException;
import exception.OperacaoInvalidaException;
import exception.PermissaoInvalidaException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CandidaturaService candidaturaService;

    public UsuarioService (UsuarioRepository usuarioRepository, CandidaturaService candidaturaService){
        this.usuarioRepository = usuarioRepository;
        this.candidaturaService = candidaturaService;
    }

    public void cadastrarUsuario(Usuario usuario) throws DadoInvalidoException, CpfCadastradoException, EmailCadastradoException {
        if(usuario == null){
            throw new DadoInvalidoException("Usuário não pode ser nulo.");
        }
        if(usuario.getNome() == null || usuario.getNome().isBlank()){
            throw new DadoInvalidoException("Nome invalido!");
        }
        if(usuario.getCpf() == null || usuario.getCpf().length() < 11){
            throw new DadoInvalidoException("CPF invalido!");
        }
        if(usuario.getEmail() == null || usuario.getEmail().isBlank()){
            throw new DadoInvalidoException("Email invalido!");
        }
        if(usuarioRepository.buscarPorCpf(usuario.getCpf()) != null){
            throw new CpfCadastradoException("Cpf já cadastrado!");
        }
        if(usuarioRepository.buscarPorEmail(usuario.getEmail()) != null){
            throw new EmailCadastradoException("Email já cadastrado!");
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

    public boolean removerUsuario(Usuario usuario) throws PermissaoInvalidaException{
        if(usuario == null){
            return false;
        }
        if(!(usuario instanceof Administrador)){
            throw new PermissaoInvalidaException("Permissão negada! (somente Administradores)");
        }

        return usuarioRepository.removerUsuario(usuario);
    }

    public boolean editarUsuario(Usuario usuarioAtualizado) throws DadoInvalidoException, EmailCadastradoException{
        if(usuarioAtualizado == null){
            throw new DadoInvalidoException("Usuario não pode ser nulo!");
        }
        if(usuarioAtualizado.getNome() == null || usuarioAtualizado.getNome().isBlank()){
            throw new DadoInvalidoException("Nome invalido!");
        }
        if(usuarioAtualizado.getEmail() == null || usuarioAtualizado.getEmail().isBlank()){
            throw new DadoInvalidoException("Email invalido!");
        }

        Usuario usuarioExistente = usuarioRepository.buscarPorCpf(usuarioAtualizado.getCpf());

        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuario não encontrado!");
        }

        Usuario usuarioEncontrado = usuarioRepository.buscarPorEmail(usuarioAtualizado.getEmail());

        if(usuarioEncontrado != null && !usuarioEncontrado.getCpf().equals(usuarioAtualizado.getCpf())){
            throw new EmailCadastradoException ("Email já cadastrado");
        } 

        return usuarioRepository.editarUsuario(usuarioAtualizado);
    }

    public void aprovarCandidatura(Usuario usuario, Candidatura candidatura) throws DadoInvalidoException, PermissaoInvalidaException, OperacaoInvalidaException{
        if(usuario == null){
            throw new DadoInvalidoException("Usuario não pode ser nulo");
        }
        if(!(usuario instanceof Administrador)){
            throw new PermissaoInvalidaException ("Permissão invalida! (somente administradores)");
        }

        candidaturaService.aprovarCandidatura(candidatura);
    }

    public void rejeitarCandidatura(Usuario usuario, Candidatura candidatura) throws DadoInvalidoException, PermissaoInvalidaException, OperacaoInvalidaException{
        if(usuario == null){
            throw new DadoInvalidoException("Usuario não pode ser nulo");
        }
        if(!(usuario instanceof Administrador)){
            throw new PermissaoInvalidaException ("Permissão invalida! (somente administradores)");
        }

        candidaturaService.rejeitarCandidatura(candidatura);
    }
}
