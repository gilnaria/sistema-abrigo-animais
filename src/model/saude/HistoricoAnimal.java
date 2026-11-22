package model.saude;

import java.util.ArrayList;
import java.util.List;
import model.Animal;

public class HistoricoAnimal {
    private Animal animal;
    
    private ArrayList <RegistroMedico> registros = new ArrayList<>();


    public HistoricoAnimal(Animal animal){
        this.animal = animal;
    }

    public Animal getAnimal(){
        return animal;
    }

    public void adicionarRegistro(RegistroMedico registro){
        registros.add(registro);

    }

    public List<RegistroMedico> getRegistros(){
       return new ArrayList<>(registros);
    }
// uma porta de saida da lista de registros

}
