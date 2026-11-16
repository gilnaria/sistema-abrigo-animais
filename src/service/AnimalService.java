package service;

import repository.AnimalRepository;
import java.util.List;
import model.Animal;

public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public void cadastrarAnimal(Animal animal){
        if (animal == null) {
            throw new IllegalArgumentException("Animal não pode ser nulo.");
        }
        if(animal.getNome().isEmpty()){
            //throw new DadoInvalidoException();
        }
        if(animal.getIdade() < 0){
            //throw new DadoInvalidoException();
        }
        if(animal.getRaca().isEmpty() || animal.getEspecie().isEmpty() || animal.getPorte().isEmpty()){
            //throw new DadoInvalidoException();
        }
        if(animal.getEstadoSaude().isEmpty() || animal.getStatusAdocao().isEmpty()){
            //throw new DadoInvalidoException();
        }

        animalRepository.adicionarAnimal(animal);
    }

    public List<Animal> listarAnimais(){
        return animalRepository.listarAnimais();
    }

    public List<Animal> buscarPorNome(String nome){
        if (nome == null || nome.isBlank()) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorNome(nome);
    }

    public List<Animal> buscarPorEspecie(String especie){
        if (especie == null || especie.isBlank()) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorEspecie(especie);
    }

    public List<Animal> buscarPorRaca(String raca){
        if (raca == null || raca.isBlank()) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorRaca(raca);
    }

    public List<Animal> buscarPorIdade(int idade){
        if (idade < 0) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorIdade(idade);
    }

    public List<Animal> buscarPorPorte(String porte){
        if (porte == null || porte.isBlank()) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorPorte(porte);
    }

    public List<Animal> buscarPorStatusAdocao(String statusAdocao){
        if (statusAdocao == null || statusAdocao.isBlank()) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorStatusAdocao(statusAdocao);
    }

    //adicionar o restante das verificações e o tratamendo de oq cada usuario pode utilizar
}
