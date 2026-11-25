package service;

import java.util.List;

import exception.DadoInvalidoException;
import exception.OperacaoInvalidaException;
import model.Candidatura;
import model.acesso.Voluntario;
import repository.CandidaturaRepository;

public class CandidaturaService {
    private final CandidaturaRepository candidaturaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository){
        this.candidaturaRepository = candidaturaRepository;
    }

    public void cadastrarCandidatura(Candidatura candidatura) throws DadoInvalidoException{
        if(candidatura == null){
            throw new DadoInvalidoException("Candidatura não pode ser nula");
        }
        if(candidatura.getVoluntario() == null){
            throw new DadoInvalidoException("Voluntario não pode ser nulo");
        }
        if(candidatura.getAnimal() == null){
            throw new DadoInvalidoException("Animal não pode ser nulo");
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

    public void aprovarCandidatura(Candidatura candidatura) throws DadoInvalidoException, OperacaoInvalidaException{
        if(candidatura == null){
            throw new DadoInvalidoException("Candidatura não pode ser nula");
        }
        if(candidatura.getStatusCandidatura() != Candidatura.StatusCandidatura.PENDENTE){
            throw new DadoInvalidoException("Não foi possivel aprovar candidatura");
        }
        if (candidatura.getVoluntario() == null) {
            throw new DadoInvalidoException("Voluntario não pode ser nulo");
        }
        if (candidatura.getAnimal() == null) {
            throw new DadoInvalidoException("Animal não pode ser nulo");
        }
        
        List<Candidatura> candidaturasDoAnimal = candidaturaRepository.buscarPorAnimal(candidatura.getAnimal());

        // Verifica se já existe uma candidatura aprovada para esse animal
        for (Candidatura existente : candidaturasDoAnimal) {
            if (existente.getStatusCandidatura() == Candidatura.StatusCandidatura.APROVADA) {
                throw new OperacaoInvalidaException ("Animal já esta sendo acompanhado");
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

    public void rejeitarCandidatura(Candidatura candidatura) throws DadoInvalidoException, OperacaoInvalidaException{
        if(candidatura == null){
            throw new DadoInvalidoException("Candidatura não pode ser nula");
        }
        if(candidatura.getStatusCandidatura() != Candidatura.StatusCandidatura.PENDENTE){
            throw new OperacaoInvalidaException("Não foi possivel rejeitar candidatura");
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
