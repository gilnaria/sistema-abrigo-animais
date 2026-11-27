package view.usuarios;

import controller.UsuarioController;
import exception.AbrigoException;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.acesso.Veterinario;
import model.acesso.Voluntario;
import util.Entrada;
import view.TelaBase;

public class TelaUsuarios extends TelaBase {
    private final UsuarioController usuarios;

    public TelaUsuarios(UsuarioController usuarios) {
        this.usuarios = usuarios;
    }

    public void exibirMenu(Usuario administrador) {
        int opcao;

        do {
            menu("GERENCIAMENTO DE USUÁRIOS",
                    "1 - Listar usuários",
                    "2 - Cadastrar usuário",
                    "3 - Editar usuário",
                    "4 - Remover usuário",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    listarUsuarios(administrador);
                    break;
                case 2:
                    cadastrar(administrador);
                    break;
                case 3:
                    editar(administrador);
                    break;
                case 4:
                    remover(administrador);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarUsuarios(Usuario administrador) {
        try {
            listar("USUÁRIOS CADASTRADOS", usuarios.listarUsuarios(administrador));
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    public static Usuario criarUsuario(int perfil, String nome, String cpf, String email, String senha) {
        switch (perfil) {
            case 1:
                return new Voluntario(nome, cpf, email, senha);
            case 2:
                return new Veterinario(nome, cpf, email, senha);
            default:
                return new Administrador(nome, cpf, email, senha);
        }
    }

    private void cadastrar(Usuario administrador) {
        titulo("CADASTRO DE USUÁRIO");
        int perfil = Entrada.lerInt("Perfil (1 - Voluntário | 2 - Veterinário | 3 - Administrador)");

        if (perfil < 1 || perfil > 3) {
            erro("Perfil inválido.");
            return;
        }

        String nome = Entrada.lerTexto("Nome");
        String cpf = Entrada.lerTexto("CPF");
        String email = Entrada.lerTexto("E-mail");
        String senha = Entrada.lerTexto("Senha");

        try {
            usuarios.cadastrarPorAdministrador(administrador, criarUsuario(perfil, nome, cpf, email, senha));
            sucesso("Usuário cadastrado.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void editar(Usuario administrador) {
        try {
            Usuario alvo = (Usuario) escolher("ESCOLHA O USUÁRIO", usuarios.listarUsuarios(administrador));

            if (alvo == null) {
                return;
            }

            String nome = Entrada.lerTexto("Nome [" + alvo.getNome() + "]");
            String email = Entrada.lerTexto("E-mail [" + alvo.getEmail() + "]");

            usuarios.editar(administrador, alvo,
                    nome.isBlank() ? alvo.getNome() : nome,
                    email.isBlank() ? alvo.getEmail() : email);
            sucesso("Usuário atualizado.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void remover(Usuario administrador) {
        try {
            Usuario alvo = (Usuario) escolher("ESCOLHA O USUÁRIO A REMOVER", usuarios.listarUsuarios(administrador));

            if (alvo == null) {
                return;
            }
            if (alvo.equals(administrador)) {
                erro("Você não pode remover a si mesmo.");
                return;
            }
            if (!Entrada.confirmar("Confirma a remoção de " + alvo.getNome() + "?")) {
                return;
            }

            usuarios.remover(administrador, alvo);
            sucesso("Usuário removido.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}
