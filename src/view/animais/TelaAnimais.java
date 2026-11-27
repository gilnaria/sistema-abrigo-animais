package view.animais;

import controller.AnimalController;
import exception.AbrigoException;
import java.time.LocalDate;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import util.Entrada;
import view.TelaBase;

public class TelaAnimais extends TelaBase {
    private final AnimalController animais;

    public TelaAnimais(AnimalController animais) {
        this.animais = animais;
    }

    public void exibirDisponiveis() {
        List<Animal> disponiveis = animais.listarDisponiveis();
        listar("ANIMAIS DISPONÍVEIS PARA ADOÇÃO", disponiveis);
    }

    public void buscar() {
        titulo("BUSCA E FILTROS");

        String nome = Entrada.lerTexto("Nome (ENTER para ignorar)");
        String especie = Entrada.lerTexto("Espécie (ENTER para ignorar)");
        String raca = Entrada.lerTexto("Raça (ENTER para ignorar)");
        Integer idade = Entrada.lerIntOpcional("Idade");
        Animal.Porte porte = (Animal.Porte) Entrada.lerEscolhaOpcional("Porte:", Animal.Porte.values());
        Animal.StatusAdocao status = (Animal.StatusAdocao) Entrada.lerEscolhaOpcional("Status de adoção:", Animal.StatusAdocao.values());

        try {
            listar("RESULTADO DA BUSCA", animais.filtrar(nome, especie, raca, idade, porte, status));
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    public void exibirMenuAdministrador(Usuario administrador) {
        int opcao;

        do {
            menu("GESTÃO DE ANIMAIS",
                    "1 - Cadastrar animal",
                    "2 - Listar todos os animais",
                    "3 - Buscar / filtrar animais",
                    "4 - Ver detalhes de um animal",
                    "5 - Editar animal",
                    "6 - Remover animal",
                    "7 - Disponibilizar animal para adoção",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    cadastrar(administrador);
                    break;
                case 2:
                    listar("TODOS OS ANIMAIS", animais.listarTodos());
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    detalhar();
                    break;
                case 5:
                    editar(administrador);
                    break;
                case 6:
                    remover(administrador);
                    break;
                case 7:
                    disponibilizar(administrador);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrar(Usuario administrador) {
        titulo("CADASTRO DE ANIMAL");

        String nome = Entrada.lerTexto("Nome");
        String especie = Entrada.lerTexto("Espécie");
        String raca = Entrada.lerTexto("Raça");
        int idade = Entrada.lerInt("Idade (anos)");
        Animal.Porte porte = (Animal.Porte) Entrada.lerEscolha("Porte:", Animal.Porte.values());
        String estadoSaude = Entrada.lerTexto("Estado de saúde");
        LocalDate dataResgate = Entrada.lerData("Data de resgate");
        Animal.StatusAdocao status = (Animal.StatusAdocao) Entrada.lerEscolha("Status inicial:",
                new Animal.StatusAdocao[] { Animal.StatusAdocao.DISPONIVEL, Animal.StatusAdocao.EM_TRATAMENTO });

        Animal animal = new Animal(nome, especie, raca, idade, porte, estadoSaude, dataResgate, status);

        try {
            animais.cadastrar(administrador, animal);
            sucesso("Animal cadastrado com o código " + animal.getCodigo() + ".");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void detalhar() {
        Animal animal = (Animal) escolher("ESCOLHA O ANIMAL", animais.listarTodos());

        if (animal != null) {
            linha();
            animal.exibirInfo();
            linha();
        }
    }

    private void editar(Usuario administrador) {
        Animal animal = (Animal) escolher("ESCOLHA O ANIMAL A EDITAR", animais.listarTodos());

        if (animal == null) {
            return;
        }

        aviso("Deixe em branco para manter o valor atual.");
        String nome = Entrada.lerTexto("Nome [" + animal.getNome() + "]");
        String especie = Entrada.lerTexto("Espécie [" + animal.getEspecie() + "]");
        String raca = Entrada.lerTexto("Raça [" + animal.getRaca() + "]");
        Integer idade = Entrada.lerIntOpcional("Idade [" + animal.getIdade() + "]");
        Animal.Porte porte = (Animal.Porte) Entrada.lerEscolhaOpcional("Porte [" + animal.getPorte() + "]:", Animal.Porte.values());

        try {
            animais.editar(administrador, animal,
                    nome.isBlank() ? animal.getNome() : nome,
                    especie.isBlank() ? animal.getEspecie() : especie,
                    raca.isBlank() ? animal.getRaca() : raca,
                    idade == null ? animal.getIdade() : idade,
                    porte == null ? animal.getPorte() : porte);
            sucesso("Animal atualizado.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void remover(Usuario administrador) {
        Animal animal = (Animal) escolher("ESCOLHA O ANIMAL A REMOVER", animais.listarTodos());

        if (animal == null || !Entrada.confirmar("Confirma a remoção de " + animal.getNome() + "?")) {
            return;
        }

        try {
            animais.remover(administrador, animal);
            sucesso("Animal removido.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void disponibilizar(Usuario administrador) {
        Animal animal = (Animal) escolher("ESCOLHA O ANIMAL A DISPONIBILIZAR", animais.listarTodos());

        if (animal == null) {
            return;
        }

        try {
            animais.disponibilizar(administrador, animal);
            sucesso(animal.getNome() + " agora está: " + animal.getStatusAdocao() + ".");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}
