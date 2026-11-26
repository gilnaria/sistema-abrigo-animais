package model.saude;

import java.time.LocalDate;
import model.Animal;

public class Tratamento extends RegistroMedico {
    private final String tipoTratamento;
    private final String duracao;
    private StatusTratamento statusTratamento;

    public Tratamento(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
            String tipoTratamento, String duracao, StatusTratamento statusTratamento) {
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.tipoTratamento = tipoTratamento;
        this.duracao = duracao;
        this.statusTratamento = statusTratamento;
    }

    public String getTipoTratamento() {
        return tipoTratamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public StatusTratamento getStatusTratamento() {
        return statusTratamento;
    }

    public void setStatusTratamento(StatusTratamento statusTratamento) {
        this.statusTratamento = statusTratamento;
    }

    public boolean isAtivo() {
        return statusTratamento != StatusTratamento.CONCLUIDO;
    }

    public void finalizarTratamento() {
        this.statusTratamento = StatusTratamento.CONCLUIDO;
    }

    @Override
    public String getTipoRegistro() {
        return "Tratamento";
    }

    @Override
    protected String detalhar() {
        return "\n   Tratamento: " + tipoTratamento + "\n   Duração: " + duracao + "\n   Status: " + statusTratamento;
    }

    @Override
    public String toString() {
        return tipoTratamento + " - " + getAnimal().getNome() + " [" + statusTratamento + "]";
    }
}
