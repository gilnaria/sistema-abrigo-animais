package controller;

import model.atividades.Evento;
import exception.atividades.DadoInvalidoException;
import exception.atividades.EventoNaoEncontradoException;
import service.EventoService;

import java.util.List;

public class EventosController {
    private EventoService service;

    public EventosController(EventoService service){
        this.service = service;
    }

    public void cadastrarEvento(Evento evento) throws DadoInvalidoException{
        service.cadastrarEvento(evento);
    
    }

    public List<Evento> listarEventos() {
        return service.listarEventos();
    }

    public void remover(Evento evento) throws EventoNaoEncontradoException{
        service.remover(evento);
    }
}