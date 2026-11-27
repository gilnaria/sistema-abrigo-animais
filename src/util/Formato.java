package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Formato {
    public static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Formato() {
    }

    public static String data(LocalDate data) {
        return data == null ? "-" : data.format(DATA);
    }

    public static String hora(LocalTime hora) {
        return hora == null ? "-" : hora.format(HORA);
    }

    public static String dataHora(LocalDateTime dataHora) {
        return dataHora == null ? "-" : dataHora.format(DATA_HORA);
    }
}
