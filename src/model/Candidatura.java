package model;

import model.acesso.Voluntario;

public class Candidatura {
    public enum StatusCandidatura{
        APROVADA,
        PENDENTE,
        REPROVADA
    }

    private Animal animal;
    private Voluntario voluntario;
    private StatusCandidatura statusCandidatura;

    public Candidatura(Voluntario voluntario, Animal animal){
        this.voluntario = voluntario;
        this.animal = animal;
        this.statusCandidatura = statusCandidatura.PENDENTE;
    }

    public Voluntario getVoluntario(){
        return voluntario;
    }
    public Animal getAnimal(){
        return animal;
    }
    public StatusCandidatura getStatusCandidatura(){
        return statusCandidatura;
    }
    public void setStatusCandidatura(StatusCandidatura novoStatusCandidatura){
        this.statusCandidatura = novoStatusCandidatura;
    }
}
