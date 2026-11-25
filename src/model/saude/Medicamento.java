package model.saude;

import java.time.LocalDate;
import java.time.LocalTime;

import model.Animal;

public class Medicamento extends RegistroMedico {
    private String nomeMedicamento;
    private String dosagem;
    private LocalTime horarioMedicamento;

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

    @Override
    public void exibirInfo() {
        exibirDadosBasicos();
        System.out.println("Medicamento: " + nomeMedicamento);
        System.out.println("Dosagem: " + dosagem);
        System.out.println("Horário: " + horarioMedicamento);
    }
}