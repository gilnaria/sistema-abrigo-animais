package repository;

import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.StatusSolicitacao;
import model.Adocao;

public class AdocaoRepository {
    private List<Adocao> adocoes = new ArrayList<>();

    public void cadastrarAdocao(Adocao adocao){
        adocoes.add(adocao);
    }

    public List<Adocao> listarSolicitacoes(){
        return new ArrayList<>(adocoes);
    }

    public Adocao buscarSolicitacaoPorAnimal(Animal animal){
        for(Adocao adocao : adocoes){
            if(animal.equals(adocao.getAnimal()) && adocao.getStatusSolicitacao() == StatusSolicitacao.PENDENTE){
                return adocao;
            }
        }
        return null;
    }

    public Adocao buscarSolicitacaoAprovadaPorAnimal(Animal animal){
        for(Adocao adocao : adocoes){
            if(animal.equals(adocao.getAnimal()) && adocao.getStatusSolicitacao() == StatusSolicitacao.APROVADA){
                return adocao;
            }
        }
        return null;
    }
}
