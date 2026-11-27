package repository;

import java.util.ArrayList;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.StatusSolicitacao;
import model.acesso.Voluntario;

public class AdocaoRepository {
    private List<Adocao> adocoes = new ArrayList<>();

    public void cadastrarAdocao(Adocao adocao){
        adocoes.add(adocao);
    }

    public List<Adocao> listarSolicitacoes(){
        return new ArrayList<>(adocoes);
    }

    public List<Adocao> listarPendentes() {
        List<Adocao> resultado = new ArrayList<>();

        for (Adocao adocao : adocoes) {
            if (adocao.isPendente()) {
                resultado.add(adocao);
            }
        }
        return resultado;
    }

    public List<Adocao> buscarPendentesPorAnimal(Animal animal) {
        List<Adocao> resultado = new ArrayList<>();

        for (Adocao adocao : adocoes) {
            if (adocao.getAnimal().equals(animal) && adocao.isPendente()) {
                resultado.add(adocao);
            }
        }
        return resultado;
    }

    public List<Adocao> buscarPorVoluntario(Voluntario voluntario) {
        List<Adocao> resultado = new ArrayList<>();

        for (Adocao adocao : adocoes) {
            if (adocao.getVoluntario().equals(voluntario)) {
                resultado.add(adocao);
            }
        }
        return resultado;
    }

    public Adocao buscarAprovadaPorAnimal(Animal animal) {
        for (Adocao adocao : adocoes) {
            if (adocao.getAnimal().equals(animal) && adocao.getStatusSolicitacao() == StatusSolicitacao.APROVADA) {
                return adocao;
            }
        }
        return null;
    }

    public boolean existePendente(Voluntario voluntario, Animal animal) {
        for (Adocao adocao : adocoes) {
            if (adocao.isPendente() && adocao.getAnimal().equals(animal)
                    && adocao.getVoluntario().equals(voluntario)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeAdocaoConcluida(Animal animal) {
        for (Adocao adocao : adocoes) {
            if (adocao.getAnimal().equals(animal) && adocao.isConcluida()) {
                return true;
            }
        }
        return false;
    }
}
