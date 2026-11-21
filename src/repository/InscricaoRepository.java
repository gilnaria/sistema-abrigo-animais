package repository;

import java.util.ArrayList;
import java.util.List;

import model.atividades.Evento;
import model.atividades.Inscricao;


public class InscricaoRepository {
    private final List<Inscricao> inscricoes = new ArrayList<>();

    public List<Inscricao> listarInscricoes(){
        return inscricoes;
    }

    public void adicionarInscricao(Inscricao inscricao) {
        inscricoes.add(inscricao);
    }

    public int contarInscricoes(Evento evento){
        int quantidade = 0;

        for (Inscricao inscricao : inscricoes) {
            if (inscricao.getEvento().equals(evento)) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public void remover(Inscricao inscricao) {
        inscricoes.remove(inscricao);
    }

}



