package model.saude;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Animal;
import util.Formato;

public class Medicamento extends RegistroMedico {
    private final String nomeMedicamento;
    private final String dosagem;
    private final LocalTime horarioMedicamento;

    public Medicamento(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
            String nomeMedicamento, String dosagem, LocalTime horarioMedicamento) {
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.nomeMedicamento = nomeMedicamento;
        this.dosagem = dosagem;
        this.horarioMedicamento = horarioMedicamento;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public LocalTime getHorarioMedicamento() {
        return horarioMedicamento;
    }

    public boolean verificarHorarioMedicamento() {
        LocalTime agora = LocalTime.now();
        return agora.getHour() == horarioMedicamento.getHour()
                && agora.getMinute() == horarioMedicamento.getMinute();
    }

    @Override
    public String getTipoRegistro() {
        return "Medicamento";
    }

    @Override
    protected String detalhar() {
        return "\n   Medicamento: " + nomeMedicamento + "\n   Dosagem: " + dosagem
                + "\n   Horário: " + Formato.hora(horarioMedicamento);
    }
}