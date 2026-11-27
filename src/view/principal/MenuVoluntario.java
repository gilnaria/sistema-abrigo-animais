package view.principal;

import model.acesso.Voluntario;
import util.Entrada;
import view.TelaBase;
import view.adocao.TelaAdocao;
import view.animais.TelaAnimais;
import view.atividades.TelaInscricao;
import view.notificacao.TelaNotificacoes;

public class MenuVoluntario extends TelaBase {
    private final TelaAnimais telaAnimais;
    private final TelaAdocao telaAdocao;
    private final TelaInscricao telaInscricao;
    private final TelaNotificacoes telaNotificacoes;

    public MenuVoluntario(TelaAnimais telaAnimais, TelaAdocao telaAdocao, TelaInscricao telaInscricao, TelaNotificacoes telaNotificacoes) {
        this.telaAnimais = telaAnimais;
        this.telaAdocao = telaAdocao;
        this.telaInscricao = telaInscricao;
        this.telaNotificacoes = telaNotificacoes;
    }

    public void exibir(Voluntario voluntario) {
        int opcao;

        do {
            titulo("BEM-VINDO(A), " + voluntario.getNome().toUpperCase());
            telaNotificacoes.exibirResumo(voluntario);
            menu("MENU DO VOLUNTÁRIO",
                    "1 - Animais disponíveis para adoção",
                    "2 - Buscar / filtrar animais",
                    "3 - Adoção e animais sob minha responsabilidade",
                    "4 - Atividades de apoio e meu histórico",
                    "5 - Notificações",
                    "0 - Sair da conta");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    telaAnimais.exibirDisponiveis();
                    break;
                case 2:
                    telaAnimais.buscar();
                    break;
                case 3:
                    telaAdocao.exibirMenuVoluntario(voluntario);
                    break;
                case 4:
                    telaInscricao.exibirMenu(voluntario);
                    break;
                case 5:
                    telaNotificacoes.exibirMenu(voluntario);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
