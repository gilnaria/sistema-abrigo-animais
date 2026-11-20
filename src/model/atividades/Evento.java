package model.atividades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class Evento {
    private String nomeEvento;
    private TipoAtividade tipo;
    private String local;
    private LocalDate dataEvento;
    private LocalTime horarioEvento;
    private int vagas;
    
    public Evento(String nomeEvento, TipoAtividade tipo, String local, LocalDate dataEvento,
        LocalTime horarioEvento, int vagas){

            this.nomeEvento = nomeEvento;
            this.tipo = tipo;
            this.local = local;
            this.dataEvento = dataEvento;
            this.horarioEvento = horarioEvento;
            this.vagas = vagas;

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

    public int getVagas(){
        return vagas;
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

public void setVagas(int vagas) {
    this.vagas = vagas;
}

public void exibirInfo(){
    System.out.println("Nome: " + nomeEvento);
    System.out.println("Tipo de atividade: " + tipo);
    System.out.println("Local: " + local);
    System.out.println("Data: " + dataEvento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    System.out.println("Horário: " + horarioEvento);
    System.out.println("Vagas: " + vagas);
}

}
