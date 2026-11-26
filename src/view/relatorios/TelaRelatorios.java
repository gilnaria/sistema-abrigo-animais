package view.relatorios;

import controller.RelatorioController;
import exception.AbrigoException;
import java.time.YearMonth;
import java.util.List;
import model.acesso.Usuario;
import util.Entrada;
import view.TelaBase;

public class TelaRelatorios extends TelaBase {
    public static final int MESES_EXIBIDOS = 6;

    private final RelatorioController relatorios;

    public TelaRelatorios(RelatorioController relatorios) {
        this.relatorios = relatorios;
    }

    public void exibirMenu(Usuario administrador) {
        int opcao;

        do {
            menu("RELATÓRIOS E ESTATÍSTICAS",
                    "1 - Animais resgatados",
                    "2 - Adoções realizadas",
                    "3 - Espécies mais resgatadas",
                    "4 - Taxa de adoção mensal",
                    "5 - Voluntários ativos",
                    "6 - Animais em tratamento",
                    "7 - Resumo geral",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            try {
                switch (opcao) {
                    case 1:
                        System.out.println("Animais resgatados: " + relatorios.totalAnimaisResgatados(administrador));
                        break;
                    case 2:
                        System.out.println("Adoções realizadas: " + relatorios.totalAdocoesRealizadas(administrador));
                        break;
                    case 3:
                        exibirEspecies(administrador);
                        break;
                    case 4:
                        exibirTaxas(administrador);
                        break;
                    case 5:
                        System.out.println("Voluntários ativos: " + relatorios.voluntariosAtivos(administrador));
                        break;
                    case 6:
                        listar("ANIMAIS EM TRATAMENTO", relatorios.animaisEmTratamento(administrador));
                        break;
                    case 7:
                        exibirResumo(administrador);
                        break;
                    case 0:
                        break;
                    default:
                        erro("Opção inválida.");
                }
            } catch (AbrigoException e) {
                tratarErro(e);
            }
        } while (opcao != 0);
    }

    private void exibirEspecies(Usuario administrador) throws AbrigoException {
        titulo("ESPÉCIES MAIS RESGATADAS");
        List<String> especies = relatorios.especiesMaisResgatadas(administrador);

        if (especies.isEmpty()) {
            aviso("Nenhum animal cadastrado.");
        }

        for (int i = 0; i < especies.size(); i++) {
            System.out.println((i + 1) + "º - " + especies.get(i));
        }
    }

    private void exibirTaxas(Usuario administrador) throws AbrigoException {
        titulo("TAXA DE ADOÇÃO MENSAL");
        System.out.println("(adoções concluídas no mês / animais resgatados até o fim do mês)");

        YearMonth mes = YearMonth.now();

        for (int i = 0; i < MESES_EXIBIDOS; i++) {
            double taxa = relatorios.taxaAdocaoMensal(administrador, mes);
            System.out.printf("%02d/%d: %.1f%%%n", mes.getMonthValue(), mes.getYear(), taxa);
            mes = mes.minusMonths(1);
        }
    }

    private void exibirResumo(Usuario administrador) throws AbrigoException {
        titulo("RESUMO GERAL");
        System.out.println("Animais resgatados: " + relatorios.totalAnimaisResgatados(administrador));
        System.out.println("Adoções realizadas: " + relatorios.totalAdocoesRealizadas(administrador));
        System.out.println("Voluntários ativos: " + relatorios.voluntariosAtivos(administrador));
        System.out.println("Animais em tratamento: " + relatorios.animaisEmTratamento(administrador).size());
        System.out.printf("Taxa de adoção do mês atual: %.1f%%%n",
                relatorios.taxaAdocaoMensal(administrador, YearMonth.now()));
        exibirEspecies(administrador);
    }
}
 
