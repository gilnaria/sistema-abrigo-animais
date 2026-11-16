package model.saude;
import model.Animal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Cirurgia extends RegistroMedico {
    private String nomeCirurgia;
    private LocalDate dataCirurgia;
    private LocalTime horarioCirurgia;
     private String resultado;

    public Cirurgia(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
        String nomeCirurgia, LocalDate dataCirurgia, LocalTime horarioCirurgia, String resultado){

            super(animal, dataRegistroMedico, veterinario, descricao);
            this.nomeCirurgia = nomeCirurgia;
            this.dataCirurgia = dataCirurgia;
            this.horarioCirurgia = horarioCirurgia;
            this.resultado = resultado;
           
        }
   

    public String getNomeCirurgia(){
        return nomeCirurgia;
    }
    public LocalDate getDataCirurgia(){
        return dataCirurgia;
    } 

    public LocalTime getHorarioCirurgia(){
        return horarioCirurgia;
    }

    public String getResultado(){
        return resultado;
    }

    public void setNomeCirurgia(String nomeCirurgia){
        this.nomeCirurgia = nomeCirurgia;
    }

    public void setDataCirurgia(LocalDate dataCirurgia){
        this.dataCirurgia = dataCirurgia;
    }

     public void setHorarioCirurgia(LocalTime horarioCirurgia){
        this.horarioCirurgia = horarioCirurgia;
    }

    public void exibirInfo(){
 System.out.println("Cirurgia: " + nomeCirurgia);
        System.out.println("Veterinário: " + getVeterinario());
        System.out.println("Resultado: " + resultado);
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Data: " + getDataCirurgia());
        System.out.println("Horário: " + getHorarioCirurgia());
    }
}
