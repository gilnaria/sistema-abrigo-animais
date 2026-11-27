package controller;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.animal.AnimalNaoEncontradoException;
import exception.animal.RemocaoProibidaException;
import exception.saude.TratamentoCriticoException;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import service.AnimalService;

public class AnimalController {
    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    public void cadastrar(Usuario usuario, Animal animal) throws AcessoNegadoException, DadoInvalidoException {
        service.cadastrarAnimal(usuario, animal);
    }

    public void editar(Usuario usuario, Animal animal, String nome, String especie, String raca, int idade,
            Animal.Porte porte) throws AcessoNegadoException, DadoInvalidoException, AnimalNaoEncontradoException {
        service.editarAnimal(usuario, animal, nome, especie, raca, idade, porte);
    }

    public void remover(Usuario usuario, Animal animal)
            throws AcessoNegadoException, AnimalNaoEncontradoException, RemocaoProibidaException {
        service.removerAnimal(usuario, animal);
    }

    public void disponibilizar(Usuario usuario, Animal animal) throws AcessoNegadoException,
            AnimalNaoEncontradoException, TratamentoCriticoException, DadoInvalidoException {
        service.disponibilizarParaAdocao(usuario, animal);
    }

    public List<Animal> listarTodos() {
        return service.listarAnimais();
    }

    public List<Animal> listarDisponiveis() {
        return service.listarDisponiveis();
    }

    public List<Animal> listarAdotaveis() {
        return service.listarAdotaveis();
    }

    public List<Animal> filtrar(String nome, String especie, String raca, Integer idade, Animal.Porte porte,
            Animal.StatusAdocao status) throws DadoInvalidoException {
        return service.filtrar(nome, especie, raca, idade, porte, status);
    }
}