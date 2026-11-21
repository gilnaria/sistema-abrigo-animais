package view.atividades;


import repository.EventosRepository;
import controller.EventosController;

import java.util.Scanner;

public class TesteCadastro {
    public static void main(String[] args) {
        EventosRepository repositorio = new EventosRepository();
        EventosController controller = new EventosController(repositorio);

        Scanner scanner = new Scanner(System.in);

        CadastroEvento tela = new CadastroEvento(controller, scanner);

        tela.exibirMenuEventos();

    }
}
