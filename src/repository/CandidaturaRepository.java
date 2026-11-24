package repository;

import model.Animal;
import model.Candidatura;
import model.acesso.Voluntario;
import java.util.ArrayList;
import java.util.List;

public class CandidaturaRepository {
    private final List<Candidatura> candidaturas = new ArrayList<>();

    public void adicionarCandidatura(Candidatura candidatura){
        candidaturas.add(candidatura);
    }
    public List<Candidatura> listarCandidaturas(){
        return new ArrayList<>(candidaturas);
    }
    public List<Candidatura> buscarPorAnimal(Animal animal){
        List<Candidatura> candidaturaPorAnimal = new ArrayList<>();
        
        for(Candidatura candidatura : candidaturas){
            if(candidatura.getAnimal().equals(animal)){
                candidaturaPorAnimal.add(candidatura);
            }
        }
        return candidaturaPorAnimal;
    }
    public List<Candidatura> buscarPorVoluntario(Voluntario voluntario){
        List<Candidatura> candidaturaPorVoluntario = new ArrayList<>();
        
        for(Candidatura candidatura : candidaturas){
            if(candidatura.getVoluntario().equals(voluntario)){
                candidaturaPorVoluntario.add(candidatura);
            }
        }
        return candidaturaPorVoluntario;
    }
}
