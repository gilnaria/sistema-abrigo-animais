package controller;

import model.Animal;
import model.saude.RegistroMedico;
import model.saude.HistoricoAnimal;
import service.SaudeService;

import java.util.List;

public class SaudeController {
    private SaudeService service;
    
    public SaudeController(SaudeService service){
        this.service = service;
    }

    // adiciona consulta,vacina...
    public void adicionarRegistro(RegistroMedico registro){
        service.adicionarRegistro(registro);
    }

    public HistoricoAnimal buscarHistorico(Animal animal){
        return service.buscarHistorico(animal);
    }

    public List<HistoricoAnimal> listarHistoricos(){
        return service.listarHistoricos();
    }

}
