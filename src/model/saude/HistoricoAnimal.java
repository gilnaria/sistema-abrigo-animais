package model.saude;

import java.util.ArrayList;
import java.util.List;
import model.Animal;

public class HistoricoAnimal {
    private final Animal animal;
    private final List<RegistroMedico> registros = new ArrayList<>();

    public HistoricoAnimal(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void adicionarRegistro(RegistroMedico registro) {
        registros.add(registro);
    }

    public List<RegistroMedico> getRegistros() {
        return new ArrayList<>(registros);
    }

    public List<Tratamento> getTratamentos() {
        List<Tratamento> resultado = new ArrayList<>();

        for (RegistroMedico registro : registros) {
            if (registro instanceof Tratamento) {
                resultado.add((Tratamento) registro);
            }
        }
        return resultado;
    }

    public List<Vacina> getVacinas() {
        List<Vacina> resultado = new ArrayList<>();

        for (RegistroMedico registro : registros) {
            if (registro instanceof Vacina) {
                resultado.add((Vacina) registro);
            }
        }
        return resultado;
    }
}
