package controller;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.acesso.CadastroDuplicadoException;
import exception.acesso.CredenciaisInvalidasException;
import java.util.List;
import model.acesso.Usuario;
import service.UsuarioService;

public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public void cadastrar(Usuario usuario)
            throws DadoInvalidoException, CadastroDuplicadoException, AcessoNegadoException {
        service.cadastrarUsuario(usuario);
    }

    public void cadastrarPorAdministrador(Usuario administrador, Usuario novo)
            throws DadoInvalidoException, CadastroDuplicadoException, AcessoNegadoException {
        service.cadastrarPorAdministrador(administrador, novo);
    }

    public Usuario autenticar(String email, String senha) throws CredenciaisInvalidasException {
        return service.autenticar(email, senha);
    }

    public List<Usuario> listarUsuarios(Usuario solicitante) throws AcessoNegadoException {
        return service.listarUsuarios(solicitante);
    }

    public void remover(Usuario solicitante, Usuario alvo) throws AcessoNegadoException, DadoInvalidoException {
        service.removerUsuario(solicitante, alvo);
    }

    public void editar(Usuario solicitante, Usuario alvo, String nome, String email)
            throws AcessoNegadoException, DadoInvalidoException, CadastroDuplicadoException {
        service.editarDados(solicitante, alvo, nome, email);
    }
}