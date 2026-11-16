package model.atividades;
import model.atividades.TipoAtividade;
import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {
    private String nomeEvento;
    private TipoAtividade tipo;
    private String local;
    private LocalDate dataEvento;
    private LocalTime horarioEvento;
    private int numVoluntarios;
    
    public Evento(String nomeEvento, TipoAtividade tipo, String local, LocalDate dataEvento,
        LocalTime horarioEvento, int numVoluntarios){

            this.nomeEvento = nomeEvento;
            this.tipo = tipo;
            this.local = local;
            this.dataEvento = dataEvento;
            this.horarioEvento = horarioEvento;
            this.numVoluntarios = numVoluntarios;

    }

    public String getNomeEvento(){
        return nomeEvento;
    }

    public TipoAtividade getTipo(){
        return tipo;
    }

    public String getLocal(){
        return local;
    }

    public LocalDate getDataEvento(){
        return dataEvento;
    }

    public LocalTime getHorarioEvento(){
        return horarioEvento;
    }

    public int getNumVoluntarios(){
        return numVoluntarios;
    }

public void setNomeEvento(String nomeEvento) {
    this.nomeEvento = nomeEvento;
}

public void setTipo(TipoAtividade tipo) {
    this.tipo = tipo;
}

public void setLocal(String local) {
    this.local = local;
}

public void setDataEvento(LocalDate dataEvento) {
    this.dataEvento = dataEvento;
}

public void setHorarioEvento(LocalTime horarioEvento) {
    this.horarioEvento = horarioEvento;
}

public void setNumVoluntarios(int numVoluntarios) {
    this.numVoluntarios = numVoluntarios;
}


}
