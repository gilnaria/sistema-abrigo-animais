package model.saude;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Animal;
import util.Formato;

public class Cirurgia extends RegistroMedico {
    private final String nomeCirurgia;
    private final LocalDate dataCirurgia;
    private final LocalTime horarioCirurgia;
    private final String resultado;

    public Cirurgia(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
            String nomeCirurgia, LocalDate dataCirurgia, LocalTime horarioCirurgia, String resultado) {
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.nomeCirurgia = nomeCirurgia;
        this.dataCirurgia = dataCirurgia;
        this.horarioCirurgia = horarioCirurgia;
        this.resultado = resultado;
    }

    public String getNomeCirurgia() {
        return nomeCirurgia;
    }

    public LocalDate getDataCirurgia() {
        return dataCirurgia;
    }

    public LocalTime getHorarioCirurgia() {
        return horarioCirurgia;
    }

    public String getResultado() {
        return resultado;
    }

    @Override
    public String getTipoRegistro() {
        return "Cirurgia";
    }

    @Override
    protected String detalhar() {
        return "\n   Cirurgia: " + nomeCirurgia + "\n   Data: " + Formato.data(dataCirurgia) + " às "
                + Formato.hora(horarioCirurgia) + "\n   Resultado: " + resultado;
    }
}
