package model.saude;
import model.Animal;
import java.time.LocalDate;

public class Vacina extends RegistroMedico {
   
    private String nomeVacina;
    private LocalDate proximaDose;
  
    public Vacina(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao, String nomeVacina,
        LocalDate proximaDose){

        super(animal, dataRegistroMedico, veterinario, descricao);
        this.nomeVacina = nomeVacina;
        this.proximaDose = proximaDose;
    }


    public String getNomeVacina(){
        return nomeVacina;
    }

    public LocalDate getProximaDose(){
        return proximaDose;
    }

    public boolean verificarProximaDose() {
        return proximaDose != null;
       }
     
    public void exibirInfo() {
        System.out.println("Vacina: " + nomeVacina);
        System.out.println("Data da aplicação: " + getDataRegistroMedico());
        System.out.println("Próxima dose: " + proximaDose);
   
    }
}
