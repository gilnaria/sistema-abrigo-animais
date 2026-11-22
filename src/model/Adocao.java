package model;

import model.acesso.Voluntario;
import java.time.LocalDate;

public class Adocao {
    private Voluntario voluntario;
    private Animal animal;
    private final LocalDate dataSolicitacao;
    private StatusSolicitacao statusSolicitacao;
    private LocalDate dataDevolucao;

    public void setDataDevolucao(LocalDate dataDevolucao){
        this.dataDevolucao = dataDevolucao;
    }
    public LocalDate getDataDevolucao(){
        return dataDevolucao;
    }

    public Adocao(Voluntario voluntario, Animal animal, StatusSolicitacao statusSolicitacao){
        this.voluntario = voluntario;
        this.animal = animal;
        this.statusSolicitacao = statusSolicitacao;
        this.dataSolicitacao = LocalDate.now();
    }

    public Voluntario getVoluntario(){
        return voluntario;
    }

    public Animal getAnimal(){
        return animal;
    }

    public StatusSolicitacao getStatusSolicitacao(){
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao){
        this.statusSolicitacao = statusSolicitacao;
    }

    public LocalDate getDataSolicitacao(){
        return dataSolicitacao;
    }
}
