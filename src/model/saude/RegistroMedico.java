package model.saude;
import model.Animal;
import java.time.LocalDate;

public abstract class RegistroMedico {
    private Animal animal;
    // Significa que cada animal tem um registro 
    private LocalDate dataRegistroMedico;
    private String veterinario;
    private String descricao;

    public RegistroMedico(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao) {
    
    this.animal = animal;
    this.dataRegistroMedico = dataRegistroMedico;
    this.veterinario = veterinario;
    this.descricao = descricao;

    }

    public Animal getAnimal(){
        return animal;
    }

    public LocalDate getDataRegistroMedico(){
        return dataRegistroMedico;
    }

    public String getVeterinario(){
        return veterinario;
    }

    public String getDescricao(){
        return descricao;
    }
   
}

