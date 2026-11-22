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
import java.time.LocalDate;

public class AdocaoService {
    private final AdocaoRepository adocaoRepository;

    public AdocaoService(AdocaoRepository adocaoRepository){
        this.adocaoRepository = adocaoRepository;
    }

    public Adocao solicitarAdocao(Voluntario voluntario, Animal animal){
        if(animal.getStatusAdocao() != Animal.StatusAdocao.DISPONIVEL){
            //EXCEPTION
        }

        Adocao adocao = new Adocao (voluntario, animal, StatusSolicitacao.PENDENTE);

        adocaoRepository.cadastrarAdocao(adocao);

        animal.setStatusAdocao(Animal.StatusAdocao.EM_PROCESSO);

        return adocao;
    }

    public void aprovarSolicitacao(Animal animal, Usuario usuario){

        if(!(usuario instanceof Administrador)){
            //exception
        }

        Adocao adocao = adocaoRepository.buscarSolicitacaoPorAnimal(animal);

        if(adocao == null){
            //exception
        }

        if(adocao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE){
            //exception
        }

        adocao.setStatusSolicitacao(StatusSolicitacao.APROVADA);

        animal.setStatusAdocao(Animal.StatusAdocao.ADOTADO);
    }

    public void rejeitarSolicitacao(Animal animal, Usuario usuario){

        if(!(usuario instanceof Administrador)){
            //exception
        }

        Adocao adocao = adocaoRepository.buscarSolicitacaoPorAnimal(animal);

        if(adocao == null){
            //exception
        }

        if(adocao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE){
            //exception
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

    public void registrarDevolucao(Animal animal){
        Adocao adocao = adocaoRepository.buscarSolicitacaoAprovadaPorAnimal(animal);

        if(adocao == null){
            //exception
        }

        if(animal.getStatusAdocao() != Animal.StatusAdocao.ADOTADO){
            //exception
        }

        adocao.setDataDevolucao(LocalDate.now());

        animal.setStatusAdocao(Animal.StatusAdocao.DISPONIVEL);
    }
}
