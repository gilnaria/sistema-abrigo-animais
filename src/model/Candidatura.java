package model;

import model.acesso.Voluntario;

public class Candidatura {
    public enum StatusCandidatura {
        PENDENTE("Pendente"),
        APROVADA("Aprovada"),
        REPROVADA("Reprovada");

        private final String descricao;

        StatusCandidatura(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }

    private final Animal animal;
    private final Voluntario voluntario;
    private StatusCandidatura statusCandidatura;

    public Candidatura(Voluntario voluntario, Animal animal) {
        this.voluntario = voluntario;
        this.animal = animal;
        this.statusCandidatura = StatusCandidatura.PENDENTE;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public StatusCandidatura getStatusCandidatura() {
        return statusCandidatura;
    }

    public boolean isPendente() {
        return statusCandidatura == StatusCandidatura.PENDENTE;
    }

    public void aprovar() {
        this.statusCandidatura = StatusCandidatura.APROVADA;
    }

    public void reprovar() {
        this.statusCandidatura = StatusCandidatura.REPROVADA;
    }

    @Override
    public String toString() {
        return animal.getNome() + " <- " + voluntario.getNome() + " [" + statusCandidatura + "]";
    }
}

