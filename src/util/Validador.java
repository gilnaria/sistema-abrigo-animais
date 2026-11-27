package util;

public final class Validador {
    public static final int TAMANHO_CPF = 11;
    public static final int TAMANHO_MINIMO_SENHA = 6;

    private Validador() {
    }

    public static boolean textoValido(String texto) {
        return texto != null && !texto.isBlank();
    }

    public static String somenteDigitos(String texto) {
        if (texto == null) {
            return "";
        }

        String digitos = "";
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c >= '0' && c <= '9') {
                digitos = digitos + c;
            }
        }
        return digitos;
    }

    public static boolean cpfValido(String cpf) {
        return somenteDigitos(cpf).length() == TAMANHO_CPF;
    }

    public static boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        String texto = email.trim();
        int arroba = texto.indexOf('@');

        if (arroba <= 0 || arroba != texto.lastIndexOf('@') || texto.contains(" ")) {
            return false;
        }

        String dominio = texto.substring(arroba + 1);
        int ponto = dominio.indexOf('.');

        return ponto > 0 && !dominio.endsWith(".") && !dominio.contains("..");
    }

    public static boolean senhaValida(String senha) {
        return senha != null && senha.length() >= TAMANHO_MINIMO_SENHA;
    }
}
