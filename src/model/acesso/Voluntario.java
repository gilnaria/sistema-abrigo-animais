package model.acesso;

import java.util.ArrayList;
import java.util.List;
import model.Animal;

public class Voluntario extends Usuario {
    private final List<Animal> animaisResponsaveis = new ArrayList<>();

    public Voluntario(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
    }

    @Override
    public String getPerfil() {
        return "Voluntário";
    }

    public void adicionarAnimal(Animal animal) {
        if (animal != null && !animaisResponsaveis.contains(animal)) {
            animaisResponsaveis.add(animal);
        }
    }

    public List<Animal> getAnimaisResponsaveis() {
        return new ArrayList<>(animaisResponsaveis);
    }
}

