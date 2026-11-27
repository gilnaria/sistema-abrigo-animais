package view.principal;

import model.acesso.Veterinario;
import util.Entrada;
import view.TelaBase;
import view.animais.TelaAnimais;
import view.notificacao.TelaNotificacoes;
import view.saude.TelaCadastroRegistro;

public class MenuVeterinario extends TelaBase {
    private final TelaAnimais telaAnimais;
    private final TelaCadastroRegistro telaSaude;
    private final TelaNotificacoes telaNotificacoes;

    public MenuVeterinario(TelaAnimais telaAnimais, TelaCadastroRegistro telaSaude, TelaNotificacoes telaNotificacoes) {
        this.telaAnimais = telaAnimais;
        this.telaSaude = telaSaude;
        this.telaNotificacoes = telaNotificacoes;
    }

    public void exibir(Veterinario veterinario) {
        int opcao;

        do {
            titulo("BEM-VINDO(A), " + veterinario.getNome().toUpperCase());
            telaNotificacoes.exibirResumo(veterinario);
            menu("MENU DO VETERINÁRIO",
                    "1 - Controle de saúde (consultas, vacinas, tratamentos)",
                    "2 - Buscar / filtrar animais",
                    "3 - Notificações",
                    "0 - Sair da conta");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    telaSaude.exibirMenuSaude(veterinario);
                    break;
                case 2:
                    telaAnimais.buscar();
                    break;
                case 3:
                    telaNotificacoes.exibirMenu(veterinario);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
