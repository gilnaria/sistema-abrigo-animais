package model.saude;

import java.time.LocalDate;

import model.Animal;

public abstract class RegistroMedico {
    private Animal animal;
    private LocalDate dataRegistroMedico;
    private String veterinario;
    private String descricao;

    public RegistroMedico(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao) {
        this.animal = animal;
        this.dataRegistroMedico = dataRegistroMedico;
        this.veterinario = veterinario;
        this.descricao = descricao;
    }

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

    protected void exibirDadosBasicos() {
        System.out.println("Animal: " + animal.getNome() + " (código " + animal.getCodigo() + ")");
        System.out.println("Data: " + dataRegistroMedico);
        System.out.println("Veterinário: " + veterinario);
        System.out.println("Descrição: " + descricao);
    }
    
    public abstract void exibirInfo();
}

