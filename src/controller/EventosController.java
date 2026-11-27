package controller;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.atividades.EventoNaoEncontradoException;
import java.util.List;
import model.acesso.Usuario;
import model.atividades.Evento;
import service.EventoService;

public class EventosController {
    private final EventoService service;

    public EventosController(EventoService service) {
        this.service = service;
    }

    public void cadastrarEvento(Usuario usuario, Evento evento) throws AcessoNegadoException, DadoInvalidoException {
        service.cadastrarEvento(usuario, evento);
    }

    public List<Evento> listarEventos() {
        return service.listarEventos();
    }

    public List<Evento> listarEventosDisponiveis() {
        return service.listarEventosDisponiveis();
    }

    public void remover(Usuario usuario, Evento evento) throws AcessoNegadoException, EventoNaoEncontradoException {
        service.remover(usuario, evento);
    }
}