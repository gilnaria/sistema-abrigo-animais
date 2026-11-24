package model.acesso;

import java.util.ArrayList;
import java.util.List;
import model.Animal;

public class Voluntario extends Usuario{
    private List<Animal> meusAnimais = new ArrayList<>();

    public Voluntario(String nome, String cpf, String email, String senha){
        super(nome, cpf, email, senha);
    }

    public void adicionarAnimal(Animal animal){
        meusAnimais.add(animal);
    }

    public List<Animal> listarMeusAnimais(){
        return new ArrayList<Animal>(meusAnimais);
    }

    public void removerAnimalResponsabilidade(Animal animal){
        meusAnimais.remove(animal);
    }
}
