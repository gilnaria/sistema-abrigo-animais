package model.saude;
import model.Animal;
import java.time.LocalDate;

public class Tratamento extends RegistroMedico {
   private String tipoTratamento;
   private String duracao;
   private String statusTratamento;

   public Tratamento(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
   String tipoTratamento, String duracao, String statusTratamento){

    super(animal, dataRegistroMedico, veterinario, descricao);
    this.tipoTratamento = tipoTratamento;
    this.duracao = duracao;
    this.statusTratamento = statusTratamento;

   }

   public String getTipoTratamento(){
    return tipoTratamento;
   }
   
   public String getDuracao(){
    return duracao;
   }

   public String getStatusTratamento(){
    return statusTratamento;
   }

   public void finalizarTratamento() {
    this.statusTratamento = "Concluído";
}
  
  public void exibirInfo(){
    System.out.println("Data: " + getDataRegistroMedico());
    System.out.println("Veterinário: " + getVeterinario());
    System.out.println("Descrição: " + getDescricao());
    System.out.println("Duração: " + duracao);
    System.out.println("Status: " + statusTratamento);
  
   
}

}
