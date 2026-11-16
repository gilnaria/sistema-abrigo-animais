package model.saude;
import model.Animal;

public abstract class RegistroMedico {
    private Animal animal;
    private String dataRegistroMedico;
    private String veterinario;

    public RegistroMedico(Animal animal, String dataRegistroMedico, String veterinario) {
    
    super(animal);
    this.dataRegistroMedico = dataRegistroMedico;
    this.veterinario = veterinario;

    }

    public Animal getAnimal(){
        return animal;
    }

    public String getDataRegistroMedico(){
        return dataRegistroMedico;
    }

    public String getVeterinario(){
        return veterinario;
    }

   
}

