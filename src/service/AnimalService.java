package service;

import exception.DadoInvalidoException;
import repository.AnimalRepository;
import java.util.List;
import model.Animal;

public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public void cadastrarAnimal(Animal animal) throws DadoInvalidoException {
        if (animal == null) {
            throw new IllegalArgumentException("Animal não pode ser nulo.");
        }
        if(animal.getNome() == null || animal.getNome().isBlank()){
            throw new DadoInvalidoException("Nome invalido!");
        }
        if(animal.getIdade() < 0){
            throw new DadoInvalidoException("Idade invalida!");
        }
        if(animal.getRaca() == null || animal.getRaca().isBlank()){
            throw new DadoInvalidoException("Raça invalida!");
        }
        if(animal.getEspecie() == null || animal.getEspecie().isBlank()){
            throw new DadoInvalidoException("Espécie invalida!");
        }
        if(animal.getPorte() == null){
            throw new DadoInvalidoException("Porte invalido!");
        }
        if(animal.getEstadoSaude() == null || animal.getEstadoSaude().isBlank()){
            throw new DadoInvalidoException("Estado de Saúde invalido!");
        }
        if(animal.getStatusAdocao() == null){
            throw new DadoInvalidoException("Status de adoção invalido!");
        }

        animalRepository.adicionarAnimal(animal);
    }

    public List<Animal> listarAnimais(){
        return animalRepository.listarAnimais();
    }

    public List<Animal> buscarPorNome(String nome) throws DadoInvalidoException{
        if (nome == null || nome.isBlank()) {
            throw new DadoInvalidoException("Nome invalido!");
        }

        return animalRepository.buscarPorNome(nome);
    }

    public List<Animal> buscarPorEspecie(String especie) throws DadoInvalidoException{
        if (especie == null || especie.isBlank()) {
            throw new DadoInvalidoException("Especie invalida!");
        }

        return animalRepository.buscarPorEspecie(especie);
    }

    public List<Animal> buscarPorRaca(String raca) throws DadoInvalidoException{
        if (raca == null || raca.isBlank()) {
            throw new DadoInvalidoException("Raça invalida!");
        }

        return animalRepository.buscarPorRaca(raca);
    }

    public List<Animal> buscarPorIdade(int idade) throws DadoInvalidoException{
        if (idade < 0) {
            throw new DadoInvalidoException("Idade invalida!");
        }

        return animalRepository.buscarPorIdade(idade);
    }

    public List<Animal> buscarPorPorte(Animal.Porte porte) throws DadoInvalidoException{
        if (porte == null) {
            throw new DadoInvalidoException("Porte invalida");
        }

        return animalRepository.buscarPorPorte(porte);
    }

    public List<Animal> buscarPorStatusAdocao(Animal.StatusAdocao statusAdocao) throws DadoInvalidoException{
        if (statusAdocao == null) {
            throw new DadoInvalidoException("Status de Adoção invalido!");
        }

        return animalRepository.buscarPorStatusAdocao(statusAdocao);
    }
    
}
