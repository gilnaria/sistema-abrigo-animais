package model.saude;

import java.time.LocalDate;
import model.Animal;
import util.Formato;

public class Vacina extends RegistroMedico {
    private final String nomeVacina;
    private final LocalDate dataProximaDose;

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

    public boolean possuiProximaDose() {
        return dataProximaDose != null;
    }

    @Override
    public String getTipoRegistro() {
        return "Vacina";
    }

    @Override
    protected String detalhar() {
        return "\n   Vacina: " + nomeVacina + "\n   Próxima dose: " + Formato.data(dataProximaDose);
    }
}
