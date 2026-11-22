package model.saude;
import model.Animal;
import java.time.LocalDate;

public class Consulta extends RegistroMedico {
    private String diagnostico;
    private String observacoes;

    public Consulta(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
    String diagnostico, String observacoes){
      
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.diagnostico = diagnostico;
        this.observacoes = observacoes;
    }

    public String getDiagnostico(){
        return diagnostico;
    }

    public String getObservacoes(){
        return observacoes;
    }
    
    @Override
    public void exibirInfo(){
        System.out.println("Data: " + getDataRegistroMedico());
        System.out.println("Veterinario: " + getVeterinario());
        System.out.println("Motivo: " + getDescricao());
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.println("Observações: " + observacoes);
    }
}
