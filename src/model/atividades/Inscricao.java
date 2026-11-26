package model.atividades;

import java.time.LocalDate;
import model.acesso.Voluntario;
import util.Formato;

public class Inscricao {
    private final Voluntario voluntario;
    private final Evento evento;
    private final LocalDate dataInscricao;

    public Inscricao(Voluntario voluntario, Evento evento, LocalDate dataInscricao) {
        this.voluntario = voluntario;
        this.evento = evento;
        this.dataInscricao = dataInscricao;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    @Override
    public String toString() {
        String situacao = evento.isEncerrado() ? "realizado" : "agendado";
        return evento.getNomeEvento() + " (" + evento.getTipo() + ") - " + Formato.data(evento.getDataEvento())
                + " [" + situacao + "] - inscrito em " + Formato.data(dataInscricao);
    }
}
