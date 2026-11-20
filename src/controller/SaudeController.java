package controller;

import model.Animal;
import model.saude.RegistroMedico;
import model.saude.HistoricoAnimal;
import repository.HistoricoAnimalRepository;

import java.util.List;

public class SaudeController {
    private HistoricoAnimalRepository repositorio;
    
    public SaudeController(HistoricoAnimalRepository repositorio){
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
