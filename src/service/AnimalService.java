package service;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.animal.AnimalNaoEncontradoException;
import exception.animal.RemocaoProibidaException;
import exception.saude.TratamentoCriticoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import repository.AdocaoRepository;
import repository.AnimalRepository;
import util.Permissoes;
import util.Validador;

public class AnimalService {
    private static final String MSG_SO_ADMIN = "[ APENAS ADMINISTRADORES PODEM GERENCIAR ANIMAIS. ]";

    private final AnimalRepository animalRepository;
    private final AdocaoRepository adocaoRepository;
    private final SaudeService saudeService;
    private final NotificacaoService notificacaoService;

    public AnimalService(AnimalRepository animalRepository, AdocaoRepository adocaoRepository,
            SaudeService saudeService, NotificacaoService notificacaoService) {
        this.animalRepository = animalRepository;
        this.adocaoRepository = adocaoRepository;
        this.saudeService = saudeService;
        this.notificacaoService = notificacaoService;
    }

    public void cadastrarAnimal(Usuario usuario, Animal animal) throws AcessoNegadoException, DadoInvalidoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);

        if (animal == null) {
            throw new DadoInvalidoException("[ ANIMAL NÃO PODE SER NULO. ]");
        }
        validarDadosBasicos(animal.getNome(), animal.getEspecie(), animal.getRaca(), animal.getIdade(),
                animal.getPorte());

        if (!Validador.textoValido(animal.getEstadoSaude())) {
            throw new DadoInvalidoException("[ ESTADO DE SAÚDE É OBRIGATÓRIO. ]");
        }
        if (animal.getDataResgate() == null || animal.getDataResgate().isAfter(LocalDate.now())) {
            throw new DadoInvalidoException("[ DATA DE RESGATE INVÁLIDA. ]");
        }
        if (animal.getStatusAdocao() != Animal.StatusAdocao.DISPONIVEL
                && animal.getStatusAdocao() != Animal.StatusAdocao.EM_TRATAMENTO) {
            throw new DadoInvalidoException("[ NOVO ANIMAL DEVE ESTAR DISPONÍVEL OU EM TRATAMENTO. ]");
        }

        animalRepository.adicionarAnimal(animal);
        notificacaoService.notificarNovoAnimal(animal);
    }

    public void editarAnimal(Usuario usuario, Animal animal, String nome, String especie, String raca, int idade,
            Animal.Porte porte) throws AcessoNegadoException, DadoInvalidoException, AnimalNaoEncontradoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        exigirExistente(animal);
        validarDadosBasicos(nome, especie, raca, idade, porte);

        animal.setNome(nome.trim());
        animal.setEspecie(especie.trim());
        animal.setRaca(raca.trim());
        animal.setIdade(idade);
        animal.setPorte(porte);
    }

    public void removerAnimal(Usuario usuario, Animal animal)
            throws AcessoNegadoException, AnimalNaoEncontradoException, RemocaoProibidaException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        exigirExistente(animal);

        if (animal.isAdotado() || adocaoRepository.existeAdocaoConcluida(animal)) {
            throw new RemocaoProibidaException("[ ANIMAL ADOTADO NÃO PODE SER REMOVIDO DO HISTÓRICO. ]");
        }
        if (!adocaoRepository.buscarPendentesPorAnimal(animal).isEmpty()) {
            throw new RemocaoProibidaException("[ ANIMAL POSSUI SOLICITAÇÕES DE ADOÇÃO PENDENTES. ]");
        }

        animalRepository.removerAnimal(animal);
    }

    public void disponibilizarParaAdocao(Usuario usuario, Animal animal) throws AcessoNegadoException,
            AnimalNaoEncontradoException, TratamentoCriticoException, DadoInvalidoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        exigirExistente(animal);

        if (animal.isAdotado()) {
            throw new DadoInvalidoException("[ ANIMAL JÁ ADOTADO. ]");
        }
        if (saudeService.possuiTratamentoCritico(animal)) {
            throw new TratamentoCriticoException(
                    "[ ANIMAL EM TRATAMENTO CRÍTICO NÃO PODE SER DISPONIBILIZADO PARA ADOÇÃO. ]");
        }

        boolean temPendentes = !adocaoRepository.buscarPendentesPorAnimal(animal).isEmpty();
        animal.setStatusAdocao(temPendentes ? Animal.StatusAdocao.EM_PROCESSO : Animal.StatusAdocao.DISPONIVEL);
    }

    public List<Animal> listarAnimais() {
        return animalRepository.listarAnimais();
    }

    public List<Animal> listarDisponiveis() {
        return animalRepository.filtrar(null, null, null, null, null, Animal.StatusAdocao.DISPONIVEL);
    }

    public List<Animal> listarAdotaveis() {
        List<Animal> adotaveis = new ArrayList<>();

        for (Animal animal : animalRepository.listarAnimais()) {
            if (animal.aceitaSolicitacoes()) {
                adotaveis.add(animal);
            }
        }
        return adotaveis;
    }

    public List<Animal> filtrar(String nome, String especie, String raca, Integer idade, Animal.Porte porte,
            Animal.StatusAdocao status) throws DadoInvalidoException {
        if (idade != null && idade < 0) {
            throw new DadoInvalidoException("[ IDADE NÃO PODE SER NEGATIVA. ]");
        }
        return animalRepository.filtrar(nome, especie, raca, idade, porte, status);
    }

    public Animal buscarPorCodigo(int codigo) throws AnimalNaoEncontradoException {
        Animal animal = animalRepository.buscarPorCodigo(codigo);

        if (animal == null) {
            throw new AnimalNaoEncontradoException("[ ANIMAL NÃO ENCONTRADO. ]");
        }
        return animal;
    }

    private void exigirExistente(Animal animal) throws AnimalNaoEncontradoException {
        if (animal == null || animalRepository.buscarPorCodigo(animal.getCodigo()) == null) {
            throw new AnimalNaoEncontradoException("[ ANIMAL NÃO ENCONTRADO. ]");
        }
    }

    private void validarDadosBasicos(String nome, String especie, String raca, int idade, Animal.Porte porte)
            throws DadoInvalidoException {
        if (!Validador.textoValido(nome)) {
            throw new DadoInvalidoException("[ NOME É OBRIGATÓRIO. ]");
        }
        if (!Validador.textoValido(especie)) {
            throw new DadoInvalidoException("[ ESPÉCIE É OBRIGATÓRIA. ]");
        }
        if (!Validador.textoValido(raca)) {
            throw new DadoInvalidoException("[ RAÇA É OBRIGATÓRIA. ]");
        }
        if (idade < 0 || idade > Animal.IDADE_MAXIMA) {
            throw new DadoInvalidoException("[ IDADE DEVE ESTAR ENTRE 0 E " + Animal.IDADE_MAXIMA + ". ]");
        }
        if (porte == null) {
            throw new DadoInvalidoException("[ PORTE É OBRIGATÓRIO. ]");
        }
    }
}