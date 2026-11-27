package controller;

import exception.acesso.AcessoNegadoException;
import exception.adocao.AnimalIndisponivelException;
import exception.adocao.SolicitacaoInvalidaException;
import exception.saude.TratamentoCriticoException;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.Candidatura;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import service.AdocaoService;
import service.CandidaturaService;

public class AdocaoController {
    private final AdocaoService adocaoService;
    private final CandidaturaService candidaturaService;

    public AdocaoController(AdocaoService adocaoService, CandidaturaService candidaturaService) {
        this.adocaoService = adocaoService;
        this.candidaturaService = candidaturaService;
    }

    public Adocao solicitarAdocao(Voluntario voluntario, Animal animal)
            throws SolicitacaoInvalidaException, AnimalIndisponivelException {
        return adocaoService.solicitarAdocao(voluntario, animal);
    }

    public void aprovarAdocao(Usuario usuario, Adocao adocao) throws AcessoNegadoException,
            SolicitacaoInvalidaException, TratamentoCriticoException, AnimalIndisponivelException {
        adocaoService.aprovarSolicitacao(usuario, adocao);
    }

    public void rejeitarAdocao(Usuario usuario, Adocao adocao)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        adocaoService.rejeitarSolicitacao(usuario, adocao);
    }

    public void registrarDevolucao(Usuario usuario, Animal animal)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        adocaoService.registrarDevolucao(usuario, animal);
    }

    public List<Adocao> listarPendentes(Usuario usuario) throws AcessoNegadoException {
        return adocaoService.listarPendentes(usuario);
    }

    public List<Adocao> listarAdocoesAtivas(Usuario usuario) throws AcessoNegadoException {
        return adocaoService.listarAdocoesAtivas(usuario);
    }

    public List<Adocao> listarSolicitacoesDoVoluntario(Voluntario voluntario) {
        return adocaoService.listarSolicitacoesDoVoluntario(voluntario);
    }

    public List<Adocao> historicoAdocao() {
        return adocaoService.historicoAdocao();
    }

    public Candidatura candidatar(Voluntario voluntario, Animal animal) throws SolicitacaoInvalidaException {
        return candidaturaService.cadastrarCandidatura(voluntario, animal);
    }

    public void aprovarCandidatura(Usuario usuario, Candidatura candidatura)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        candidaturaService.aprovarCandidatura(usuario, candidatura);
    }

    public void rejeitarCandidatura(Usuario usuario, Candidatura candidatura)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        candidaturaService.rejeitarCandidatura(usuario, candidatura);
    }

    public List<Candidatura> listarCandidaturasPendentes(Usuario usuario) throws AcessoNegadoException {
        return candidaturaService.listarPendentes(usuario);
    }

    public List<Candidatura> listarCandidaturasDoVoluntario(Voluntario voluntario) {
        return candidaturaService.listarCandidaturasDoVoluntario(voluntario);
    }
}
