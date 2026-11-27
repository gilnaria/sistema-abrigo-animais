package repository;

import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.saude.HistoricoAnimal;

public class HistoricoAnimalRepository {
    private final List<HistoricoAnimal> historicos = new ArrayList<>();

    public void salvarHistorico(HistoricoAnimal historico) {
        historicos.add(historico);
    }

    public List<HistoricoAnimal> listarHistoricos() {
        return new ArrayList<>(historicos);
    }

    public HistoricoAnimal buscarHistorico(Animal animal) {
        for (HistoricoAnimal historico : historicos) {
            if (historico.getAnimal().equals(animal)) {
                return historico;
            }
        }
        return null;
    }
}

