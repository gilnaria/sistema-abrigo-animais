package model.saude;

import java.time.LocalDate;
import model.Animal;
import model.Exibivel;
import util.Formato;

public abstract class RegistroMedico implements Exibivel {
    private final Animal animal;
    private final LocalDate dataRegistroMedico;
    private final String veterinario;
    private final String descricao;

    public RegistroMedico(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao) {
        this.animal = animal;
        this.dataRegistroMedico = dataRegistroMedico;
        this.veterinario = veterinario;
        this.descricao = descricao;
    }

    public abstract String getTipoRegistro();

    protected abstract String detalhar();

    public Animal getAnimal() {
        return animal;
    }

    public LocalDate getDataRegistroMedico() {
        return dataRegistroMedico;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public String getDescricao() {
        return descricao;
    }

    public String resumo() {
        return "[" + Formato.data(dataRegistroMedico) + "] " + getTipoRegistro() + " - Vet.: " + veterinario
                + "\n   " + descricao + detalhar();
    }

    @Override
    public void exibirInfo() {
        System.out.println(resumo());
    }
}

