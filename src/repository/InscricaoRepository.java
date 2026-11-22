package repository;

import model.acesso.Voluntario;
import model.atividades.Evento;
import model.atividades.Inscricao;

import java.util.ArrayList;
import java.util.List;


public class InscricaoRepository {
    private final List<Inscricao> inscricoes = new ArrayList<>();

    public List<Inscricao> listarInscricoes(){
    return new ArrayList<>(inscricoes);
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

    public boolean existeInscricao(Voluntario voluntario, Evento evento) {
    for (Inscricao inscricao : inscricoes) {
        if (inscricao.getVoluntario().equals(voluntario) && inscricao.getEvento().equals(evento)) {
            return true;
        }
    }
    return false;
   }

    public void remover(Inscricao inscricao) {
        inscricoes.remove(inscricao);
    }

}