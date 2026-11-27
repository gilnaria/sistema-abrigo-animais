package util;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class Entrada {
    private static final Scanner SCANNER = new Scanner(System.in, charsetDoConsole());

    private Entrada() {
    }

    private static Charset charsetDoConsole() {
        String nome = System.getProperty("stdin.encoding", System.getProperty("sun.stdin.encoding"));

        if (nome != null) {
            try {
                return Charset.forName(nome);
            } catch (IllegalArgumentException e) {
                return Charset.defaultCharset();
            }
        }
        return Charset.defaultCharset();
    }

    public static String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return SCANNER.nextLine().trim();
    }

    public static int lerInt(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número inteiro.");
            }
        }
    }

    public static Integer lerIntOpcional(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo + " (ENTER para ignorar)");
            if (texto.isBlank()) {
                return null;
            }
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite um número inteiro.");
            }
        }
    }

    public static LocalDate lerData(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo + " (DD/MM/AAAA)");
            try {
                return LocalDate.parse(texto, Formato.DATA);
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Data inválida. Use o formato DD/MM/AAAA.");
            }
        }
    }

    public static LocalTime lerHora(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo + " (HH:MM)");
            try {
                return LocalTime.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Horário inválido. Use o formato HH:MM.");
            }
        }
    }

    public static Object lerEscolha(String rotulo, Object[] valores) {
        while (true) {
            System.out.println(rotulo);
            for (int i = 0; i < valores.length; i++) {
                System.out.println("  " + (i + 1) + " - " + valores[i]);
            }
            int escolha = lerInt("Opção");
            if (escolha >= 1 && escolha <= valores.length) {
                return valores[escolha - 1];
            }
            System.out.println("[ERRO] Opção inválida.");
        }
    }

    public static Object lerEscolhaOpcional(String rotulo, Object[] valores) {
        while (true) {
            System.out.println(rotulo + " (0 para ignorar)");
            for (int i = 0; i < valores.length; i++) {
                System.out.println("  " + (i + 1) + " - " + valores[i]);
            }
            int escolha = lerInt("Opção");
            if (escolha == 0) {
                return null;
            }
            if (escolha >= 1 && escolha <= valores.length) {
                return valores[escolha - 1];
            }
            System.out.println("[ERRO] Opção inválida.");
        }
    }

    public static boolean confirmar(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo + " (S/N)");
            if (texto.equalsIgnoreCase("s")) {
                return true;
            }
            if (texto.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("[ERRO] Responda com S ou N.");
        }
    }

    public static void fechar() {
        SCANNER.close();
    }

    public static void pausar() {
        System.out.print("Pressione ENTER para continuar...");
        SCANNER.nextLine();
    }
}