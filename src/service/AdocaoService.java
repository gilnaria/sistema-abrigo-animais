package service;

import repository.AdocaoRepository;
import model.acesso.Voluntario;
import model.StatusSolicitacao;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.Animal;
import model.Adocao;
import java.util.ArrayList;
import java.util.List;

import exception.DadoInvalidoException;
import exception.OperacaoInvalidaException;
import exception.PermissaoInvalidaException;

import java.time.LocalDate;

public class AdocaoService {
    private final AdocaoRepository adocaoRepository;

    public AdocaoService(AdocaoRepository adocaoRepository){
        this.adocaoRepository = adocaoRepository;
    }

    public Adocao solicitarAdocao(Voluntario voluntario, Animal animal) throws OperacaoInvalidaException{
        if(animal.getStatusAdocao() != Animal.StatusAdocao.DISPONIVEL){
            throw new OperacaoInvalidaException("Animal indisponivel!");
        }

        Adocao adocao = new Adocao (voluntario, animal, StatusSolicitacao.PENDENTE);

        adocaoRepository.cadastrarAdocao(adocao);

        animal.setStatusAdocao(Animal.StatusAdocao.EM_PROCESSO);

        return adocao;
    }

    public void aprovarSolicitacao(Animal animal, Usuario usuario) throws PermissaoInvalidaException, DadoInvalidoException, OperacaoInvalidaException{

        if(!(usuario instanceof Administrador)){
            throw new PermissaoInvalidaException("Permissão negada! (somente administradores)");
        }

        Adocao adocao = adocaoRepository.buscarSolicitacaoPorAnimal(animal);

        if(adocao == null){
            throw new DadoInvalidoException("Adoção não pode ser nula!");
        }

        if(adocao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE){
            throw new OperacaoInvalidaException("Não é possivel aprovar a solicitação");
        }

        adocao.setStatusSolicitacao(StatusSolicitacao.APROVADA);

        animal.setStatusAdocao(Animal.StatusAdocao.ADOTADO);

        adocao.setDataAdocao(LocalDate.now());
    }

    public void rejeitarSolicitacao(Animal animal, Usuario usuario) throws PermissaoInvalidaException, DadoInvalidoException, OperacaoInvalidaException{

        if(!(usuario instanceof Administrador)){
            throw new PermissaoInvalidaException("Permissão negada! (somente administradores)");
        }

        Adocao adocao = adocaoRepository.buscarSolicitacaoPorAnimal(animal);

        if(adocao == null){
            throw new DadoInvalidoException("Adoção não pode ser nula!");
        }

        if(adocao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE){
            throw new OperacaoInvalidaException("Não é possivel rejeitar a solicitação");
        }

        adocao.setStatusSolicitacao(StatusSolicitacao.REJEITADA);

        animal.setStatusAdocao(Animal.StatusAdocao.DISPONIVEL);
    }

    public List<Adocao> historicoAdocao(){
        List<Adocao> adocoesAprovadas = new ArrayList<>();

        for(Adocao adocao : adocaoRepository.listarSolicitacoes()){
            if(adocao.getStatusSolicitacao() == StatusSolicitacao.APROVADA){
                adocoesAprovadas.add(adocao);
            }
        }
        return adocoesAprovadas;
    }

    public void registrarDevolucao(Animal animal) throws DadoInvalidoException, OperacaoInvalidaException{
        Adocao adocao = adocaoRepository.buscarSolicitacaoAprovadaPorAnimal(animal);

        if(adocao == null){
            throw new DadoInvalidoException("Adoção não pode ser nula!");
        }

        if(animal.getStatusAdocao() != Animal.StatusAdocao.ADOTADO){
            throw new OperacaoInvalidaException("Devolução invalida!");
        }

        adocao.setDataDevolucao(LocalDate.now());

        animal.setStatusAdocao(Animal.StatusAdocao.DISPONIVEL);
    }
}
