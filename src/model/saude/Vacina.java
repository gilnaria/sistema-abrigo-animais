package model.saude;

import java.time.LocalDate;

import model.Animal;

public class Vacina extends RegistroMedico {
    private String nomeVacina;
    private LocalDate dataProximaDose;

    public Vacina(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
            String nomeVacina, LocalDate dataProximaDose) {
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.nomeVacina = nomeVacina;
        this.dataProximaDose = dataProximaDose;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public LocalDate getDataProximaDose() {
        return dataProximaDose;
    }

    @Override
    public void exibirInfo() {
        super.exibirDadosBasicos();
        System.out.println("Vacina: " + nomeVacina);
        System.out.println("Próxima dose: " + dataProximaDose);
    }
}
