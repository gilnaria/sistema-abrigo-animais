package view.principal;

import java.util.Scanner;

import java.util.List;
import service.UsuarioService;

public class MenuPrincipal {
    private UsuarioService usuarioService;
    private Scanner scanner;

    public MenuPrinicpal(UsuarioService usuarioService, Scanner scanner){
        this.usuarioService = usuarioService;
        this.scanner = scanner;
    }

    public void exibirMenuPrincipal(){
        int opcao;
        
        do{
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("|           MENU PRINCIPAL           |");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("| 1 - Fazer Login                    |");
        System.out.println("| 2 - Realizar Cadastro              |");
        System.out.println("| 3 - Visualizar Animais Disponiveis |");
        System.out.println("| 4 - Sobre o Sistema                |");
        System.out.println("| 0 - Encerrar o programa            |"); 
        System.out.println("└────────────────────────────────────┘");
        System.out.println("│ Escolha uma opção:                 | ");
        System.out.print("│ > ");
        opcao = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└────────────────────────────────────┘");
        System.out.println("\n");

        switch(opcao){
            case 1:
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|            SISTEMA DE GERENCIAMENTO DE ABRIGOS             |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
        }

        }while (opcao != 0);
    }
}
