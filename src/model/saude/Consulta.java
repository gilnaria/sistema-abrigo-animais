package model.saude;

import java.time.LocalDate;
import model.Animal;

public class Consulta extends RegistroMedico {
    private final String diagnostico;
    private final String observacoes;

    public Consulta(Animal animal, LocalDate dataRegistroMedico, String veterinario, String descricao,
            String diagnostico, String observacoes) {
        super(animal, dataRegistroMedico, veterinario, descricao);
        this.diagnostico = diagnostico;
        this.observacoes = observacoes;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    @Override
    public String getTipoRegistro() {
        return "Consulta";
    }

    @Override
    protected String detalhar() {
        return "\n   Diagnóstico: " + diagnostico + "\n   Observações: " + observacoes;
    }
}
