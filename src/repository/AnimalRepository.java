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

    public Animal buscarPorCodigo(int codigo) {
        for (Animal animal : animais) {
            if (animal.getCodigo() == codigo) {
                return animal;
            }
        }
        return null;
    }

    public List<Animal> filtrar(String nome, String especie, String raca, Integer idade, Animal.Porte porte, Animal.StatusAdocao status) {
        List<Animal> resultado = new ArrayList<>();

        for (Animal animal : animais) {
            boolean atende = contem(animal.getNome(), nome)
                                    && contem(animal.getEspecie(), especie)
                                    && contem(animal.getRaca(), raca)
                                    && (idade == null || animal.getIdade() == idade)
                                    && (porte == null || animal.getPorte() == porte)
                                    && (status == null || animal.getStatusAdocao() == status);

            if (atende) {
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public boolean removerAnimal(Animal animal) {
        return animais.remove(animal);
    }

    private boolean contem(String campo, String filtro) {
        return filtro == null || filtro.isBlank() || campo.toLowerCase().contains(filtro.trim().toLowerCase());
    }
}
