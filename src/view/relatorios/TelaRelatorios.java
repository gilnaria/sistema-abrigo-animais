package view.relatorios;
 
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 
import controller.RelatorioController;
import exception.PermissaoInvalidaException;
import model.acesso.Usuario;
import model.saude.Tratamento;
 
public class TelaRelatorios {
    private final RelatorioController controller;
    private final Usuario usuario;
    private final Scanner scanner;
 
    public TelaRelatorios(RelatorioController controller, Usuario usuario, Scanner scanner) {
        this.controller = controller;
        this.usuario = usuario;
        this.scanner = scanner;
    }
 
    public void exibirMenu() {
        int opcao;
 
        do {
            System.out.println("┌────────────────────────────────────────────────────────────┐");
            System.out.println("|                  RELATÓRIOS E ESTATÍSTICAS                 |");
            System.out.println("├────────────────────────────────────────────────────────────┤");
            System.out.println("| 1 - Quantidade de animais resgatados                       |");
            System.out.println("| 2 - Quantidade de adoções realizadas                       |");
            System.out.println("| 3 - Espécies mais resgatadas                               |");
            System.out.println("| 4 - Taxa de adoção mensal                                  |");
            System.out.println("| 5 - Número de voluntários ativos                           |");
            System.out.println("| 6 - Relatório de animais em tratamento                     |");
            System.out.println("| 7 - Relatório completo                                     |");
            System.out.println("| 0 - Voltar                                                 |");
            System.out.println("└────────────────────────────────────────────────────────────┘");
            System.out.print("│ > ");
 
            opcao = lerOpcao();
 
            try {
                switch (opcao) {
                    case 1:
                        exibirAnimaisResgatados();
                        break;
                    case 2:
                        exibirAdocoesRealizadas();
                        break;
                    case 3:
                        exibirEspeciesMaisResgatadas();
                        break;
                    case 4:
                        exibirTaxaAdocaoMensal();
                        break;
                    case 5:
                        exibirVoluntariosAtivos();
                        break;
                    case 6:
                        exibirAnimaisEmTratamento();
                        break;
                    case 7:
                        exibirRelatorioCompleto();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("[ OPÇÃO INVÁLIDA. ]");
                }
            } catch (PermissaoInvalidaException e) {
                System.out.println("[ " + e.getMessage() + " ]");
            }
 
            System.out.println();
        } while (opcao != 0);
    }
 
    public void exibirRelatorioCompleto() throws PermissaoInvalidaException {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("|                     RELATÓRIO COMPLETO                     |");
        System.out.println("└────────────────────────────────────────────────────────────┘");
 
        exibirAnimaisResgatados();
        exibirAdocoesRealizadas();
        exibirEspeciesMaisResgatadas();
        exibirTaxaAdocaoMensal();
        exibirVoluntariosAtivos();
        exibirAnimaisEmTratamento();
    }
 
    public void exibirAnimaisResgatados() throws PermissaoInvalidaException {
        System.out.println("\n● Animais resgatados: " + controller.quantidadeAnimaisResgatados(usuario));
    }
 
    public void exibirAdocoesRealizadas() throws PermissaoInvalidaException {
        System.out.println("\n● Adoções realizadas: " + controller.quantidadeAdocoesRealizadas(usuario));
    }
 
    public void exibirEspeciesMaisResgatadas() throws PermissaoInvalidaException {
        Map<String, Integer> ranking = controller.especiesMaisResgatadas(usuario);
 
        System.out.println("\n● Espécies mais resgatadas:");
 
        if (ranking.isEmpty()) {
            System.out.println("  Nenhum animal cadastrado.");
            return;
        }
 
        int posicao = 1;
 
        for (Map.Entry<String, Integer> entrada : ranking.entrySet()) {
            System.out.println("  " + posicao + "º " + entrada.getKey() + " - " + entrada.getValue());
            posicao++;
        }
    }
 
    public void exibirTaxaAdocaoMensal() throws PermissaoInvalidaException {
        Map<YearMonth, Double> taxas = controller.taxaAdocaoMensal(usuario);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yyyy");
 
        System.out.println("\n● Taxa de adoção mensal:");
 
        if (taxas.isEmpty()) {
            System.out.println("  Nenhum animal cadastrado.");
            return;
        }
 
        for (Map.Entry<YearMonth, Double> entrada : taxas.entrySet()) {
            System.out.println("  " + entrada.getKey().format(formato) + " - "
                    + String.format("%.1f%%", entrada.getValue()));
        }
    }
 
    public void exibirVoluntariosAtivos() throws PermissaoInvalidaException {
        System.out.println("\n● Voluntários ativos: " + controller.quantidadeVoluntariosAtivos(usuario));
    }
 
    public void exibirAnimaisEmTratamento() throws PermissaoInvalidaException {
        List<Tratamento> tratamentos = controller.tratamentosEmAndamento(usuario);
 
        System.out.println("\n● Animais em tratamento:");
 
        if (tratamentos.isEmpty()) {
            System.out.println("  Nenhum animal em tratamento.");
            return;
        }
 
        for (Tratamento tratamento : tratamentos) {
            System.out.println("  Animal: " + tratamento.getAnimal().getNome()
                    + " (código " + tratamento.getAnimal().getCodigo() + ")");
            System.out.println("  Tratamento: " + tratamento.getTipoTratamento());
            System.out.println("  Status: " + tratamento.getStatusTratamento());
            System.out.println("  Veterinário: " + tratamento.getVeterinario());
            System.out.println("  Início: " + tratamento.getDataRegistroMedico());
            System.out.println("  ----------------------------------------");
        }
 
        System.out.println("  Total: " + tratamentos.size());
    }
 
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
 
