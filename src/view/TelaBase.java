package view;

import java.util.List;
import util.Entrada;

public abstract class TelaBase {
    private static final int LARGURA = 60;

    private static String centralizar(String texto) {
        int espacos = Math.max(0, (LARGURA - texto.length()) / 2);
        return String.format("%-" + LARGURA + "s", " ".repeat(espacos) + texto);
    }

    protected void titulo(String texto) {
        System.out.println();
        System.out.println("┌" + "─".repeat(LARGURA) + "┐");
        System.out.println("│" + centralizar(texto) + "│");
        System.out.println("└" + "─".repeat(LARGURA) + "┘");
    }

    protected void menu(String titulo, String... opcoes) {
        System.out.println();
        System.out.println("┌" + "─".repeat(LARGURA) + "┐");
        System.out.println("│" + centralizar(titulo) + "│");
        System.out.println("├" + "─".repeat(LARGURA) + "┤");
        for (String opcao : opcoes) {
            System.out.println("│" + String.format(" %-" + (LARGURA - 1) + "s", opcao) + "│");
        }
        System.out.println("└" + "─".repeat(LARGURA) + "┘");
    }

    protected void linha() {
        System.out.println("─".repeat(LARGURA + 2));
    }

    protected void sucesso(String mensagem) {
        System.out.println("[OK] " + mensagem);
    }

    protected void erro(String mensagem) {
        System.out.println("[ERRO] " + mensagem);
    }

    protected void aviso(String mensagem) {
        System.out.println("[!] " + mensagem);
    }

    protected void listar(String titulo, List<?> itens) {
        titulo(titulo);

        if (itens.isEmpty()) {
            aviso("Nenhum registro encontrado.");
            return;
        }

        for (Object item : itens) {
            System.out.println(item);
        }
    }

    protected Object escolher(String titulo, List<?> itens) {
        if (itens.isEmpty()) {
            aviso("Nenhum registro disponível.");
            return null;
        }

        titulo(titulo);

        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + " - " + itens.get(i));
        }
        System.out.println("0 - Cancelar");

        int escolha = Entrada.lerInt("Escolha");

        if (escolha == 0) {
            return null;
        }
        if (escolha < 1 || escolha > itens.size()) {
            erro("Opção inválida.");
            return null;
        }
        return itens.get(escolha - 1);
    }

    protected void tratarErro(Exception e) {
        erro(e.getMessage());
    }
}
