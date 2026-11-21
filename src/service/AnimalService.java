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
        if(animal.getNome() == null || animal.getNome().isBlank()){
            //throw new DadoInvalidoException();
        }
        if(animal.getIdade() < 0){
            //throw new DadoInvalidoException();
        }
        if(animal.getRaca() == null || animal.getRaca().isBlank()){
            //throw new DadoInvalidoException();
        }
        if(animal.getEspecie() == null || animal.getEspecie().isBlank()){

        }
        if(animal.getPorte() == null){
            //throw new DadoInvalidoException();
        }
        if(animal.getEstadoSaude() == null || animal.getEstadoSaude().isBlank()){
            //throw new DadoInvalidoException();
        }
        if(animal.getStatusAdocao() == null){
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

    public List<Animal> buscarPorPorte(Animal.Porte porte){
        if (porte == null) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorPorte(porte);
    }

    public List<Animal> buscarPorStatusAdocao(Animal.StatusAdocao statusAdocao){
        if (statusAdocao == null) {
            // throw new DadoInvalidoException();
        }

        return animalRepository.buscarPorStatusAdocao(statusAdocao);
    }

    //adicionar o restante das verificações e o tratamendo de oq cada usuario pode utilizar
}
