package controller;

import model.atividades.Evento;
import repository.EventosRepository;
import exception.atividades.DadoInvalidoException;
import exception.atividades.EventoNaoEncontradoException;

import java.util.List;
import java.time.LocalDate;


public class EventosController {
    private EventosRepository repositorio;

    public EventosController(EventosRepository repositorio){
        this.repositorio = repositorio;
    }

    public void cadastrarEvento(Evento evento) throws DadoInvalidoException{
        if(evento == null){
            throw new DadoInvalidoException("[ EVENTO NÃO PODE SER NULO. ]");
        }

        if(evento.getNomeEvento() == null || evento.getNomeEvento().isBlank()){
            throw new DadoInvalidoException("[ NOME DA ATIVIDADE NÃO PODE SER VAZIO. ]");
        }

        if(evento.getTipo() == null) {
            throw new DadoInvalidoException("[ TIPO DE ATIVIDADE É OBRIGÁTORIO. ]");
        }

        if(evento.getLocal() == null || evento.getLocal().isBlank()){
           throw new DadoInvalidoException("[ NOME DO LOCAL NÃO PODE SER VAZIO. ]"); 
        }

        if(evento.getDataEvento() == null || evento.getDataEvento().isBefore(LocalDate.now())){
            throw new DadoInvalidoException("[ DATA DA ATIVIDADE NÃO PODE ESTAR NO PASSADO. ]");
        }

        if(evento.getHorarioEvento() == null){
            throw new DadoInvalidoException("[ HORÁRIO DA ATIVIDADE É OBRIGÁTORIO. ]");
        }

        if(evento.getVagas() <- 0){
            throw new DadoInvalidoException("[ NÚMERO DE VAGAS DEVE SER MAIOR QUE ZERO. ]");
        }
        
        
        repositorio.adicionarEventos(evento);
    }

    public List<Evento> listarEventos() {
        return repositorio.listarEventos();
    }

    public void remover(Evento evento) throws EventoNaoEncontradoException{
        if(evento == null || !repositorio.listarEventos().contains(evento)){
            throw new EventoNaoEncontradoException("[ EVENTO NÃO ENCONTRADO. ]");
        }
        repositorio.removerEventos(evento);
    }

}
