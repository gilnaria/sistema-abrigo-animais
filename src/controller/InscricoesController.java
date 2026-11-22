package controller;

import model.atividades.Evento;
import model.atividades.Inscricao;
import model.acesso.Voluntario;
import repository.InscricaoRepository;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class InscricoesController {
    private InscricaoRepository repositorio;

    public InscricoesController(InscricaoRepository repositorio){
        this.repositorio = repositorio;
    }

    public boolean realizarInscricao(Voluntario voluntario, Evento evento) {
    int quantidade = repositorio.contarInscricoes(evento);

    if (quantidade < evento.getVagas()) {
        Inscricao inscricao = new Inscricao(voluntario, evento, LocalDate.now());

        repositorio.adicionarInscricao(inscricao);
        return true;
    } else {
        System.out.println("Não há mais vagas disponíveis.");
        // lancar uma excecao
        return false;
     }
    }
    
    public List<Inscricao> listarHistorico(Voluntario voluntario) {
    List<Inscricao> historico = new ArrayList<>();

    for (Inscricao inscricao : repositorio.listarInscricoes()) {
        if (inscricao.getVoluntario().equals(voluntario)) {
            historico.add(inscricao);
        }
    }

    return historico;
    }

    public void cancelarInscricao(Inscricao inscricao){
        repositorio.remover(inscricao);
    }

    }
    

