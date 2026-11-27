package view.principal;

import controller.UsuarioController;
import exception.AbrigoException;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.acesso.Veterinario;
import model.acesso.Voluntario;
import util.Entrada;
import util.Validador;
import view.TelaBase;
import view.animais.TelaAnimais;
import view.usuarios.TelaUsuarios;

public class MenuPrincipal extends TelaBase {
    private final UsuarioController usuarios;
    private final TelaAnimais telaAnimais;
    private final MenuVoluntario menuVoluntario;
    private final MenuVeterinario menuVeterinario;
    private final MenuAdministrador menuAdministrador;

    public MenuPrincipal(UsuarioController usuarios, TelaAnimais telaAnimais,
            MenuVoluntario menuVoluntario, MenuVeterinario menuVeterinario,
            MenuAdministrador menuAdministrador) {
        this.usuarios = usuarios;
        this.telaAnimais = telaAnimais;
        this.menuVoluntario = menuVoluntario;
        this.menuVeterinario = menuVeterinario;
        this.menuAdministrador = menuAdministrador;
    }

    public void exibirMenuPrincipal() {
        int opcao;

        do {
            menu("SISTEMA DE GERENCIAMENTO DE ABRIGOS",
                    "1 - Fazer login",
                    "2 - Realizar cadastro",
                    "3 - Visualizar animais disponíveis",
                    "4 - Sobre o sistema",
                    "0 - Encerrar o programa");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    telaAnimais.exibirDisponiveis();
                    break;
                case 4:
                    sobre();
                    break;
                case 0:
                    sucesso("Sistema encerrado.");
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void login() {
        titulo("LOGIN");
        String email = Entrada.lerTexto("E-mail");
        String senha = Entrada.lerTexto("Senha");

        try {
            Usuario usuario = usuarios.autenticar(email, senha);

            if (usuario instanceof Administrador) {
                menuAdministrador.exibir((Administrador) usuario);
            } else if (usuario instanceof Veterinario) {
                menuVeterinario.exibir((Veterinario) usuario);
            } else if (usuario instanceof Voluntario) {
                menuVoluntario.exibir((Voluntario) usuario);
            }
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void cadastrar() {
        titulo("CADASTRO");
        int perfil = Entrada.lerInt("Perfil (1 - Voluntário | 2 - Veterinário | 3 - Administrador)");

        if (perfil < 1 || perfil > 3) {
            erro("Perfil inválido.");
            return;
        }

        String nome = Entrada.lerTexto("Nome");
        String cpf = Entrada.lerTexto("CPF");
        String email = Entrada.lerTexto("E-mail");
        String senha = Entrada.lerTexto("Senha (mínimo " + Validador.TAMANHO_MINIMO_SENHA + " caracteres)");

        if (!Validador.senhaValida(senha)) {
            erro("Senha muito curta.");
            return;
        }

        try {
            usuarios.cadastrar(TelaUsuarios.criarUsuario(perfil, nome, cpf, email, senha));
            sucesso("Cadastro realizado. Faça login para continuar.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void sobre() {
        titulo("SOBRE O SISTEMA");
        System.out.println("Plataforma para ONGs e instituições de proteção animal organizarem");
        System.out.println("resgates, saúde dos animais, adoções, voluntários e atividades.");
        System.out.println();
        System.out.println("Perfis: Voluntário, Veterinário e Administrador do Abrigo.");
        System.out.println("Trabalho Final de POO - UFC Crateús (CRT0007).");
    }
}
