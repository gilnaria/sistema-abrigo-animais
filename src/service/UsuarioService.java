package service;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.acesso.CadastroDuplicadoException;
import exception.acesso.CredenciaisInvalidasException;
import java.util.ArrayList;
import java.util.List;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import repository.UsuarioRepository;
import util.Permissoes;
import util.Validador;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarUsuario(Usuario usuario)
            throws DadoInvalidoException, CadastroDuplicadoException, AcessoNegadoException {
        registrar(usuario);
    }

    public void cadastrarPorAdministrador(Usuario administrador, Usuario novo)
            throws DadoInvalidoException, CadastroDuplicadoException, AcessoNegadoException {
        Permissoes.exigirAdministrador(administrador, "[ APENAS ADMINISTRADORES PODEM GERENCIAR USUÁRIOS. ]");
        registrar(novo);
    }

    public Usuario autenticar(String email, String senha) throws CredenciaisInvalidasException {
        Usuario usuario = Validador.textoValido(email) ? usuarioRepository.buscarPorEmail(email.trim()) : null;

        if (usuario == null || senha == null || !usuario.verificarSenha(senha)) {
            throw new CredenciaisInvalidasException("[ E-MAIL OU SENHA INCORRETOS. ]");
        }
        return usuario;
    }

    public List<Usuario> listarUsuarios(Usuario solicitante) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(solicitante, "[ APENAS ADMINISTRADORES PODEM GERENCIAR USUÁRIOS. ]");
        return usuarioRepository.listarUsuarios();
    }

    public List<Voluntario> listarVoluntarios() {
        List<Voluntario> voluntarios = new ArrayList<>();

        for (Usuario usuario : usuarioRepository.listarUsuarios()) {
            if (usuario instanceof Voluntario) {
                voluntarios.add((Voluntario) usuario);
            }
        }
        return voluntarios;
    }

    public void removerUsuario(Usuario solicitante, Usuario alvo) throws AcessoNegadoException, DadoInvalidoException {
        Permissoes.exigirAdministrador(solicitante, "[ APENAS ADMINISTRADORES PODEM GERENCIAR USUÁRIOS. ]");

        if (alvo == null || !usuarioRepository.removerUsuario(alvo)) {
            throw new DadoInvalidoException("[ USUÁRIO NÃO ENCONTRADO. ]");
        }
    }

    public void editarDados(Usuario solicitante, Usuario alvo, String novoNome, String novoEmail)
            throws AcessoNegadoException, DadoInvalidoException, CadastroDuplicadoException {
        Permissoes.exigirAdministrador(solicitante, "[ APENAS ADMINISTRADORES PODEM GERENCIAR USUÁRIOS. ]");

        if (alvo == null) {
            throw new DadoInvalidoException("[ USUÁRIO NÃO ENCONTRADO. ]");
        }
        if (!Validador.textoValido(novoNome)) {
            throw new DadoInvalidoException("[ NOME NÃO PODE SER VAZIO. ]");
        }
        if (!Validador.emailValido(novoEmail)) {
            throw new DadoInvalidoException("[ E-MAIL INVÁLIDO. ]");
        }

        Usuario comEmail = usuarioRepository.buscarPorEmail(novoEmail.trim());

        if (comEmail != null && !comEmail.equals(alvo)) {
            throw new CadastroDuplicadoException("[ E-MAIL JÁ CADASTRADO. ]");
        }

        alvo.setNome(novoNome.trim());
        alvo.setEmail(novoEmail.trim());
    }

    private void registrar(Usuario usuario) throws DadoInvalidoException, CadastroDuplicadoException {
        if (usuario == null) {
            throw new DadoInvalidoException("[ USUÁRIO NÃO PODE SER NULO. ]");
        }
        if (!Validador.textoValido(usuario.getNome())) {
            throw new DadoInvalidoException("[ NOME NÃO PODE SER VAZIO. ]");
        }
        if (!Validador.cpfValido(usuario.getCpf())) {
            throw new DadoInvalidoException("[ CPF DEVE TER 11 DÍGITOS. ]");
        }
        if (!Validador.emailValido(usuario.getEmail())) {
            throw new DadoInvalidoException("[ E-MAIL INVÁLIDO. ]");
        }
        if (usuarioRepository.buscarPorCpf(usuario.getCpf()) != null) {
            throw new CadastroDuplicadoException("[ CPF JÁ CADASTRADO. ]");
        }
        if (usuarioRepository.buscarPorEmail(usuario.getEmail()) != null) {
            throw new CadastroDuplicadoException("[ E-MAIL JÁ CADASTRADO. ]");
        }

        usuarioRepository.adicionarUsuario(usuario);
    }
}
