package repository;

import java.util.ArrayList;
import java.util.List;
import model.Animal;

public class AnimalRepository {
    private final List<Animal> animais = new ArrayList<>();

    public void adicionarAnimal(Animal animal){
        animais.add(animal);
    }

    public List<Animal> listarAnimais() {
        return new ArrayList<>(animais);
    }

    /*criar um filtro de busca com menu no view
    *1- Buscar por nome
    *2- Buscar por especie
    *3- Buscar por raca
    *4- Buscar por idade
    *5- Buscar por porte
    *6- Buscar por Status de Adoção
    */

    //mostrar pro usuario no view se a lista resultado ta vazia
    
    public List<Animal> buscarPorNome(String nome){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getNome().equalsIgnoreCase(nome)){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public List<Animal> buscarPorEspecie(String especie){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getEspecie().equalsIgnoreCase(especie)){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public List<Animal> buscarPorRaca(String raca){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getRaca().equalsIgnoreCase(raca)){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public List<Animal> buscarPorIdade(int idade){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getIdade() == idade){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public List<Animal> buscarPorPorte(String porte){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getPorte().equalsIgnoreCase(porte)){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public List<Animal> buscarPorStatusAdocao(String statusAdocao){
        List<Animal> resultado = new ArrayList<>();

        for(Animal animal : animais){
            if(animal.getStatusAdocao().equalsIgnoreCase(statusAdocao)){
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public boolean removerAnimal(Animal animal){
        return animais.remove(animal);
    }

    public boolean editarNomeAnimal(Animal animal, String novoNome){
        if(animais.contains(animal)){
            animal.setNome(novoNome);
            return true;
        }
        return false;
    }

    public boolean editarIdadeAnimal(Animal animal, int novaIdade){
        if(animais.contains(animal)){
            animal.setIdade(novaIdade);
            return true;
        }
        return false;
    }

    public boolean editarPorteAnimal(Animal animal, String novoPorte){
        if(animais.contains(animal)){
            animal.setPorte(novoPorte);
            return true;
        }
        return false;
    }

    public boolean editarEstadoSaudeAnimal(Animal animal, String novoEstadoSaude){
        if(animais.contains(animal)){
            animal.setEstadoSaude(novoEstadoSaude);
            return true;
        }
        return false;
    }

    public boolean editarStatusAdocaoAnimal(Animal animal, String novoStatusAdocao){
        if(animais.contains(animal)){
            animal.setStatusAdocao(novoStatusAdocao);
            return true;
        }
        return false;
    }
}
