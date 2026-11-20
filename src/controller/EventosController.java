package controller;

import model.atividades.Evento;
import repository.EventosRepository;

import java.util.List;


public class EventosController {
    private EventosRepository repositorio;

    public EventosController(EventosRepository repositorio){
        this.repositorio = repositorio;
    }

    public void cadastrarEvento(Evento evento){
        repositorio.adicionarEventos(evento);
    }

    public List<Evento> listarEventos() {
        return repositorio.listarEventos();
    }

    public void remover(Evento evento) {
        repositorio.removerEventos(evento);
    }

}
