package model.atividades;
import model.acesso.Voluntario;
import java.time.LocalDate;

public class Inscricao {
    private Voluntario voluntario;
    private Evento evento;
    private LocalDate dataInscricao;

    public Inscricao(Voluntario voluntario, Evento evento, LocalDate dataInscricao){
        this.voluntario = voluntario;
        this.evento = evento;
        this.dataInscricao = dataInscricao;

    }

    public Voluntario getVoluntario(){
        return voluntario;
    }

    public Evento getEvento(){
        return evento;
    }

    public LocalDate getDataInscricao(){
        return dataInscricao;
    }


}
