package service;

import java.util.List;
import model.Candidatura;
import model.acesso.Voluntario;
import repository.CandidaturaRepository;

public class CandidaturaService {
    private final CandidaturaRepository candidaturaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository){
        this.candidaturaRepository = candidaturaRepository;
    }

    public void cadastrarCandidatura(Candidatura candidatura){
        if(candidatura == null){
            //Exception
        }
        if(candidatura.getVoluntario() == null){
            //exception
        }
        if(candidatura.getAnimal() == null){
            //excption
        }

        List<Candidatura> candidaturasDoVoluntario = candidaturaRepository.buscarPorVoluntario(candidatura.getVoluntario());

        for (Candidatura candidaturaExistente : candidaturasDoVoluntario) {
            if (candidaturaExistente.getAnimal().equals(candidatura.getAnimal()) 
                && candidaturaExistente.getStatusCandidatura() == Candidatura.StatusCandidatura.PENDENTE) {
                throw new IllegalArgumentException("Você já possui uma candidatura pendente para este animal.");
            }
        }

        candidaturaRepository.adicionarCandidatura(candidatura);
    }

    public void aprovarCandidatura(Candidatura candidatura){
        if(candidatura == null){
            //exception
        }
        if(candidatura.getStatusCandidatura() != Candidatura.StatusCandidatura.PENDENTE){
            //exception
        }
        if (candidatura.getVoluntario() == null) {
            //exception
        }
        if (candidatura.getAnimal() == null) {
            //exception
        }
        
        List<Candidatura> candidaturasDoAnimal = candidaturaRepository.buscarPorAnimal(candidatura.getAnimal());

        // Verifica se já existe uma candidatura aprovada para esse animal
        for (Candidatura existente : candidaturasDoAnimal) {
            if (existente.getStatusCandidatura() == Candidatura.StatusCandidatura.APROVADA) {
                //exception
            }
        }

        // Aprova a candidatura escolhida
        candidatura.setStatusCandidatura(Candidatura.StatusCandidatura.APROVADA);

        // Adiciona o animal à lista do voluntário
        candidatura.getVoluntario().adicionarAnimal(candidatura.getAnimal());

        // Rejeita as outras candidaturas pendentes
        for (Candidatura existente : candidaturasDoAnimal) {
            if (existente != candidatura && existente.getStatusCandidatura() == Candidatura.StatusCandidatura.PENDENTE) {
                existente.setStatusCandidatura(Candidatura.StatusCandidatura.REPROVADA);
            }
        }
    }

    public void rejeitarCandidatura(Candidatura candidatura){
        if(candidatura == null){
            //exception
        }
        if(candidatura.getStatusCandidatura() != Candidatura.StatusCandidatura.PENDENTE){
            //exception
        }
        candidatura.setStatusCandidatura(Candidatura.StatusCandidatura.REPROVADA);
    }

    public List<Candidatura> listarCandidaturasDoVoluntario(Voluntario voluntario) {
        if (voluntario == null) {
            throw new IllegalArgumentException("Voluntário não informado.");
        }

        return candidaturaRepository.buscarPorVoluntario(voluntario);
    }
}
