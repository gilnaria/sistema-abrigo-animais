package view.principal;

import model.acesso.Administrador;
import util.Entrada;
import view.TelaBase;
import view.adocao.TelaAdocao;
import view.animais.TelaAnimais;
import view.atividades.TelaCadastroEvento;
import view.notificacao.TelaNotificacoes;
import view.relatorios.TelaRelatorios;
import view.saude.TelaCadastroRegistro;
import view.usuarios.TelaUsuarios;

public class MenuAdministrador extends TelaBase {
    private final TelaAnimais telaAnimais;
    private final TelaAdocao telaAdocao;
    private final TelaCadastroEvento telaEventos;
    private final TelaNotificacoes telaNotificacoes;
    private final TelaRelatorios telaRelatorios;
    private final TelaUsuarios telaUsuarios;
    private final TelaCadastroRegistro telaSaude;

    public MenuAdministrador(TelaAnimais telaAnimais, TelaAdocao telaAdocao, TelaCadastroEvento telaEventos, TelaNotificacoes telaNotificacoes, TelaRelatorios telaRelatorios, TelaUsuarios telaUsuarios, TelaCadastroRegistro telaSaude) {
        this.telaAnimais = telaAnimais;
        this.telaAdocao = telaAdocao;
        this.telaEventos = telaEventos;
        this.telaNotificacoes = telaNotificacoes;
        this.telaRelatorios = telaRelatorios;
        this.telaUsuarios = telaUsuarios;
        this.telaSaude = telaSaude;
    }

    public void exibir(Administrador administrador) {
        int opcao;

        do {
            titulo("BEM-VINDO(A), " + administrador.getNome().toUpperCase());
            telaNotificacoes.exibirResumo(administrador);
            menu("MENU DO ADMINISTRADOR",
                    "1 - Gestão de animais",
                    "2 - Controle de adoções",
                    "3 - Atividades (mutirões, campanhas, feiras)",
                    "4 - Gerenciar usuários",
                    "5 - Relatórios e estatísticas",
                    "6 - Notificações e avisos gerais",
                    "7 - Emitir relatório de saúde de um animal",
                    "0 - Sair da conta");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    telaAnimais.exibirMenuAdministrador(administrador);
                    break;
                case 2:
                    telaAdocao.exibirMenuAdministrador(administrador);
                    break;
                case 3:
                    telaEventos.exibirMenuEventos(administrador);
                    break;
                case 4:
                    telaUsuarios.exibirMenu(administrador);
                    break;
                case 5:
                    telaRelatorios.exibirMenu(administrador);
                    break;
                case 6:
                    telaNotificacoes.exibirMenu(administrador);
                    break;
                case 7:
                    telaSaude.emitirRelatorioAdministrador(administrador);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
