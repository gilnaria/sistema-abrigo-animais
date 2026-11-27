package service;

import exception.acesso.AcessoNegadoException;
import exception.adocao.SolicitacaoInvalidaException;
import java.util.List;
import model.Animal;
import model.Candidatura;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import repository.CandidaturaRepository;
import util.Permissoes;

public class CandidaturaService {
    private final CandidaturaRepository candidaturaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository) {
        this.candidaturaRepository = candidaturaRepository;
    }

    public Candidatura cadastrarCandidatura(Voluntario voluntario, Animal animal)
            throws SolicitacaoInvalidaException {
        if (voluntario == null || animal == null) {
            throw new SolicitacaoInvalidaException("[ VOLUNTÁRIO E ANIMAL SÃO OBRIGATÓRIOS. ]");
        }
        if (animal.isAdotado()) {
            throw new SolicitacaoInvalidaException("[ ANIMAL ADOTADO NÃO RECEBE CANDIDATURAS DE CUIDADO. ]");
        }
        if (voluntario.getAnimaisResponsaveis().contains(animal)) {
            throw new SolicitacaoInvalidaException("[ VOCÊ JÁ É RESPONSÁVEL POR ESTE ANIMAL. ]");
        }

        for (Candidatura existente : candidaturaRepository.buscarPorVoluntario(voluntario)) {
            if (existente.getAnimal().equals(animal) && existente.isPendente()) {
                throw new SolicitacaoInvalidaException("[ VOCÊ JÁ POSSUI UMA CANDIDATURA PENDENTE PARA ESTE ANIMAL. ]");
            }
        }

        Candidatura candidatura = new Candidatura(voluntario, animal);
        candidaturaRepository.adicionarCandidatura(candidatura);
        return candidatura;
    }

    public void aprovarCandidatura(Usuario usuario, Candidatura candidatura)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM APROVAR CANDIDATURAS. ]");
        exigirPendente(candidatura);

        candidatura.aprovar();
        candidatura.getVoluntario().adicionarAnimal(candidatura.getAnimal());
    }

    public void rejeitarCandidatura(Usuario usuario, Candidatura candidatura)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM REJEITAR CANDIDATURAS. ]");
        exigirPendente(candidatura);

        candidatura.reprovar();
    }

    public List<Candidatura> listarPendentes(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM CONTROLAR CANDIDATURAS. ]");
        return candidaturaRepository.listarPendentes();
    }

    public List<Candidatura> listarCandidaturasDoVoluntario(Voluntario voluntario) {
        return candidaturaRepository.buscarPorVoluntario(voluntario);
    }

    private void exigirPendente(Candidatura candidatura) throws SolicitacaoInvalidaException {
        if (candidatura == null) {
            throw new SolicitacaoInvalidaException("[ CANDIDATURA NÃO ENCONTRADA. ]");
        }
        if (!candidatura.isPendente()) {
            throw new SolicitacaoInvalidaException("[ A CANDIDATURA NÃO ESTÁ PENDENTE. ]");
        }
    }
}
