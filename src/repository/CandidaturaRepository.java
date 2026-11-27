package repository;

import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.Candidatura;
import model.acesso.Voluntario;

public class CandidaturaRepository {
    private final List<Candidatura> candidaturas = new ArrayList<>();

    public void adicionarCandidatura(Candidatura candidatura) {
        candidaturas.add(candidatura);
    }

    public List<Candidatura> listarCandidaturas() {
        return new ArrayList<>(candidaturas);
    }

    public List<Candidatura> listarPendentes() {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura candidatura : candidaturas) {
            if (candidatura.isPendente()) {
                resultado.add(candidatura);
            }
        }
        return resultado;
    }

    public List<Candidatura> buscarPorAnimal(Animal animal) {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura candidatura : candidaturas) {
            if (candidatura.getAnimal().equals(animal)) {
                resultado.add(candidatura);
            }
        }
        return resultado;
    }

    public List<Candidatura> buscarPorVoluntario(Voluntario voluntario) {
        List<Candidatura> resultado = new ArrayList<>();

        for (Candidatura candidatura : candidaturas) {
            if (candidatura.getVoluntario().equals(voluntario)) {
                resultado.add(candidatura);
            }
        }
        return resultado;
    }
}
