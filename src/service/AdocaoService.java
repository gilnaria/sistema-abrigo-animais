package service;

import exception.acesso.AcessoNegadoException;
import exception.adocao.AnimalIndisponivelException;
import exception.adocao.SolicitacaoInvalidaException;
import exception.saude.TratamentoCriticoException;
import java.util.ArrayList;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.StatusSolicitacao;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import repository.AdocaoRepository;
import util.Permissoes;

public class AdocaoService {
    private final AdocaoRepository adocaoRepository;
    private final SaudeService saudeService;
    private final NotificacaoService notificacaoService;

    public AdocaoService(AdocaoRepository adocaoRepository, SaudeService saudeService,
            NotificacaoService notificacaoService) {
        this.adocaoRepository = adocaoRepository;
        this.saudeService = saudeService;
        this.notificacaoService = notificacaoService;
    }

    public Adocao solicitarAdocao(Voluntario voluntario, Animal animal)
            throws SolicitacaoInvalidaException, AnimalIndisponivelException {
        if (voluntario == null || animal == null) {
            throw new SolicitacaoInvalidaException("[ VOLUNTÁRIO E ANIMAL SÃO OBRIGATÓRIOS. ]");
        }
        if (!animal.aceitaSolicitacoes() || saudeService.possuiTratamentoCritico(animal)) {
            throw new AnimalIndisponivelException("[ ANIMAL INDISPONÍVEL PARA NOVAS SOLICITAÇÕES DE ADOÇÃO. ]");
        }
        if (adocaoRepository.existePendente(voluntario, animal)) {
            throw new SolicitacaoInvalidaException("[ JÁ EXISTE SOLICITAÇÃO PENDENTE DESTE VOLUNTÁRIO PARA O ANIMAL. ]");
        }

        Adocao adocao = new Adocao(voluntario, animal);
        adocaoRepository.cadastrarAdocao(adocao);
        animal.setStatusAdocao(Animal.StatusAdocao.EM_PROCESSO);
        notificacaoService.notificarAtualizacaoAdocao(adocao);
        return adocao;
    }

    public void aprovarSolicitacao(Usuario usuario, Adocao adocao) throws AcessoNegadoException,
            SolicitacaoInvalidaException, TratamentoCriticoException, AnimalIndisponivelException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM APROVAR ADOÇÕES. ]");
        exigirPendente(adocao);

        Animal animal = adocao.getAnimal();

        if (animal.isAdotado()) {
            throw new AnimalIndisponivelException("[ ANIMAL JÁ ADOTADO. ]");
        }
        if (saudeService.possuiTratamentoCritico(animal)) {
            throw new TratamentoCriticoException("[ ANIMAL EM TRATAMENTO CRÍTICO NÃO PODE SER ADOTADO. ]");
        }

        adocao.aprovar();
        animal.setStatusAdocao(Animal.StatusAdocao.ADOTADO);
        notificacaoService.notificarAtualizacaoAdocao(adocao);

        for (Adocao outra : adocaoRepository.buscarPendentesPorAnimal(animal)) {
            outra.rejeitar();
            notificacaoService.notificarAtualizacaoAdocao(outra);
        }
    }

    public void rejeitarSolicitacao(Usuario usuario, Adocao adocao)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM REJEITAR ADOÇÕES. ]");
        exigirPendente(adocao);

        Animal animal = adocao.getAnimal();
        adocao.rejeitar();

        boolean semPendentes = adocaoRepository.buscarPendentesPorAnimal(animal).isEmpty();

        if (semPendentes && animal.getStatusAdocao() == Animal.StatusAdocao.EM_PROCESSO) {
            animal.setStatusAdocao(Animal.StatusAdocao.DISPONIVEL);
        }

        notificacaoService.notificarAtualizacaoAdocao(adocao);
    }

    public void registrarDevolucao(Usuario usuario, Animal animal)
            throws AcessoNegadoException, SolicitacaoInvalidaException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM REGISTRAR DEVOLUÇÕES. ]");

        Adocao adocao = animal == null ? null : adocaoRepository.buscarAprovadaPorAnimal(animal);

        if (adocao == null) {
            throw new SolicitacaoInvalidaException("[ NÃO HÁ ADOÇÃO ATIVA PARA ESTE ANIMAL. ]");
        }

        adocao.registrarDevolucao();

        if (saudeService.possuiTratamentoCritico(animal)) {
            animal.setStatusAdocao(Animal.StatusAdocao.EM_TRATAMENTO);
        } else {
            animal.setStatusAdocao(Animal.StatusAdocao.DISPONIVEL);
        }

        notificacaoService.notificarAtualizacaoAdocao(adocao);
    }

    public List<Adocao> listarPendentes(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM CONTROLAR CANDIDATOS. ]");
        return adocaoRepository.listarPendentes();
    }

    public List<Adocao> listarAdocoesAtivas(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, "[ APENAS ADMINISTRADORES PODEM REGISTRAR DEVOLUÇÕES. ]");
        List<Adocao> ativas = new ArrayList<>();

        for (Adocao adocao : adocaoRepository.listarSolicitacoes()) {
            if (adocao.getStatusSolicitacao() == StatusSolicitacao.APROVADA) {
                ativas.add(adocao);
            }
        }
        return ativas;
    }

    public List<Adocao> listarSolicitacoesDoVoluntario(Voluntario voluntario) {
        return adocaoRepository.buscarPorVoluntario(voluntario);
    }

    public List<Adocao> historicoAdocao() {
        List<Adocao> concluidas = new ArrayList<>();

        for (Adocao adocao : adocaoRepository.listarSolicitacoes()) {
            if (adocao.isConcluida()) {
                concluidas.add(adocao);
            }
        }
        return concluidas;
    }

    private void exigirPendente(Adocao adocao) throws SolicitacaoInvalidaException {
        if (adocao == null) {
            throw new SolicitacaoInvalidaException("[ SOLICITAÇÃO NÃO ENCONTRADA. ]");
        }
        if (!adocao.isPendente()) {
            throw new SolicitacaoInvalidaException("[ A SOLICITAÇÃO NÃO ESTÁ PENDENTE. ]");
        }
    }
}
