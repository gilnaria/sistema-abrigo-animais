package service;

import java.util.List;

import model.Animal;
import model.saude.HistoricoAnimal;
import model.saude.RegistroMedico;
import repository.HistoricoAnimalRepository;

public class SaudeService {
     private HistoricoAnimalRepository repositorio;
    
    public SaudeService(HistoricoAnimalRepository repositorio){
        this.repositorio = repositorio;
    }

    // adiciona consulta,vacina...
    public void adicionarRegistro(RegistroMedico registro){
        HistoricoAnimal historico = repositorio.buscarHistorico(registro.getAnimal());

        if(historico != null){
            historico.adicionarRegistro(registro);
        }
        else{
            historico = new HistoricoAnimal(registro.getAnimal());
            historico.adicionarRegistro(registro);
            repositorio.salvarHistorico(historico);
        }
    }

    public HistoricoAnimal buscarHistorico(Animal animal){
        return repositorio.buscarHistorico(animal);
    }

    public List<HistoricoAnimal> listarHistoricos(){
        return repositorio.listarHistoricos();
    }
    
}
