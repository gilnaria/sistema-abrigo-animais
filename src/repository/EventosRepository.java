package repository;

import java.util.ArrayList;
import java.util.List;

import model.atividades.Evento;

public class EventosRepository {
    private final List<Evento> eventos = new ArrayList<>();

    public void adicionarEventos(Evento evento){
        eventos.add(evento);
    }
    
    public List<Evento> listarEventos() {
        return eventos;
    }

    public void removerEventos(Evento evento) {
        eventos.remove(evento);
    }

}
