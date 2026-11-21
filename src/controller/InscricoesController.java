package controller;

import model.atividades.Evento;
import model.atividades.Inscricao;
import model.acesso.Voluntario;
import repository.InscricaoRepository;
import java.time.LocalDate;

public class InscricoesController {
    private InscricaoRepository repositorio;

    public InscricoesController(InscricaoRepository repositorio){
        this.repositorio = repositorio;
    }

    public void realizarInscricao(Voluntario voluntario, Evento evento){
        int quantidade = repositorio.contarInscricoes(evento);

        if(quantidade < evento.getVagas()){
            Inscricao inscricao = new Inscricao(voluntario, evento, LocalDate.now()); 
        
            repositorio.adicionarInscricao(inscricao);
        }
        else{
            System.out.println("Não há mais vagas disponíveis.");
        }

    }

    public void cancelarInscricao(Inscricao inscricao){
        repositorio.remover(inscricao);
    }

    }
    

