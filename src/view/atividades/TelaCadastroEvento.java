package view.atividades;

import model.atividades.Evento;
import model.atividades.TipoAtividade;
import controller.EventosController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;

import exception.atividades.DadoInvalidoException;
import exception.atividades.EventoNaoEncontradoException;


public class TelaCadastroEvento {
    private EventosController controller;
    private Scanner scanner;

    public TelaCadastroEvento(EventosController controller, Scanner scanner){
        this.controller = controller;
        this.scanner = scanner;
    }

    public void exibirMenuEventos(){
        int opcao;
        
        do{
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("|          MENU DE ATIVIDADES        |");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("| 1 - Cadastrar evento               |");
        System.out.println("| 2 - Exibir eventos disponíveis     |");
        System.out.println("| 3 - Consultar evento               |");
        System.out.println("| 4 - Cancelar evento                |");
        System.out.println("| 0 - Voltar                         |"); 
        System.out.println("└────────────────────────────────────┘");
        System.out.println("│ Escolha uma opção:                 | ");
        System.out.print("│ > ");
        opcao = scanner.nextInt();
        scanner.nextLine();
        System.out.println("└────────────────────────────────────┘");
        System.out.println("\n");
        

        switch (opcao){
            case 1:
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|                     CADASTRO DE ATIVIDADE                  |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
                System.out.print("[NOME]: ");
                String nome = scanner.nextLine();
                System.out.println("-------------------------------------------------------------");
              
                System.out.println("┌───────────────────────────────┐");
                System.out.println("│       TIPOS DE ATIVIDADE      │");
                System.out.println("├───────────────────────────────┤");

                TipoAtividade[] tipos = TipoAtividade.values();

                for (int i = 0; i < tipos.length; i++) {
                System.out.printf("│ %d - %-25s │%n", (i + 1), tipos[i]);
                }

                System.out.println("└───────────────────────────────┘");

                System.out.print("\nEscolha o tipo de atividade: ");
                int escolhaTipo = scanner.nextInt();
                scanner.nextLine();

                if (escolhaTipo < 1 || escolhaTipo > tipos.length) {
                System.out.println("┌─────────────────────────────────────────────┐");
                System.out.println("|                 TIPO INVÁLIDO.              |");
                System.out.println("└─────────────────────────────────────────────┘");
                break;
                }

                TipoAtividade tipo = tipos[escolhaTipo - 1];
                
                System.out.println("-------------------------------------------------------------");
                System.out.print("[LOCAL]: ");
                String local = scanner.nextLine();
                

                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.println("-------------------------------------------------------------");

                System.out.print("[DATA (DD/MM/AAAA)]: ");
                LocalDate data = LocalDate.parse(scanner.nextLine(), formato);
                System.out.println("-------------------------------------------------------------");

                System.out.print("[HORÁRIO (HH:MM)]: ");
                LocalTime horario = LocalTime.parse(scanner.nextLine());
                System.out.println("-------------------------------------------------------------");

                System.out.print("[VAGAS DISPONÍVEIS]: ");
                int vagas = scanner.nextInt();
                System.out.println("-------------------------------------------------------------");

                boolean confirmar = false;

                while(!confirmar){
                    System.out.println("┌────────────────────────────────────────────────────────────┐");
                    System.out.println("|                     CADASTRO DE ATIVIDADE                  |");
                    System.out.println("├────────────────────────────────────────────────────────────┤");
                    
                    System.out.printf("│ [NOME]:     %-46s │%n", nome);
                    System.out.printf("│ [TIPO]:     %-46s │%n", tipo);
                    System.out.printf("│ [LOCAL]:    %-46s │%n", local);
                    System.out.printf("│ [DATA]:     %-46s │%n", data.format(formato));
                    System.out.printf("│ [HORÁRIO]:  %-46s │%n", horario);
                    System.out.printf("│ [VAGAS]:    %-46d │%n", vagas);
                    
                    System.out.println("└────────────────────────────────────────────────────────────┘");
                   
                    System.out.println("┌─────────────────────────────────┐");
                    System.out.println("| 1 - [ CONFIRMAR ]               |");
                    System.out.println("| 2 - [ EDITAR ]                  |");
                    System.out.println("| 0 - [ CANCELAR CADASTRO ]       |");
                    System.out.println("└─────────────────────────────────┘");
                    System.out.print("|> ");
                    int opcaoConfirmacao = scanner.nextInt();
                    scanner.nextLine();

                    switch(opcaoConfirmacao){
                        case 1:
                            try{    
                                Evento novoEvento = new Evento(nome, tipo, local, data, horario, vagas);

                                controller.cadastrarEvento(novoEvento);

                                System.out.println("┌────────────────────────────────────────────────────────────┐");
                                System.out.println("|                 EVENTO CADASTRADO COM SUCESSO!             |");
                                System.out.println("└────────────────────────────────────────────────────────────┘");
                                confirmar = true;
                            
                            }catch(DadoInvalidoException erro){
                                System.out.println(erro.getMessage());
                            }
                            
                                break;

                        case 2:

                            System.out.println("┌──────────────────────────────────────┐");
                            System.out.println("|          CADASTRO DE ATIVIDADE       |");
                            System.out.println("├──────────────────────────────────────┤");
                            System.out.println("| 1 - [NOME]                           |");
                            System.out.println("| 2 - [TIPO DE ATIVIDADE]              |");
                            System.out.println("| 3 - [LOCAL]                          |");
                            System.out.println("| 4 - [DATA]                           |");
                            System.out.println("| 5 - [HORÁRIO]                        |");
                            System.out.println("| 6 - [VAGAS]                          |");  
                            System.out.println("└──────────────────────────────────────┘");
                            System.out.println("│ O que deseja editar?                 | ");
                            System.out.print("|>");
                            int campo = scanner.nextInt();
                            scanner.nextLine();

                            switch(campo){
                                case 1:
                                    System.out.print("[NOME]: ");
                                    nome = scanner.nextLine();
                                    break;

                                case 2:
                                    for (int i = 0; i < tipos.length; i++) {
                                    System.out.println((i + 1) + " - " + tipos[i]);
                                    }

                                    System.out.print("[TIPO]: ");
                                    int novoTipo = scanner.nextInt();
                                    scanner.nextLine();

                                    if (novoTipo >= 1 && novoTipo <= tipos.length) {
                                    tipo = tipos[novoTipo - 1];
                                    } 
                                    else {
                                    System.out.println("┌───────────────────────────────────────────┐");
                                    System.out.println("|               TIPO INVÁLIDO.              |");
                                    System.out.println("└───────────────────────────────────────────┘");
                                    }
                                    break;

                                case 3:
                                    System.out.print("[LOCAL]: ");
                                    local = scanner.nextLine();
                                    break;

                                case 4:
                                    System.out.print("[DATA (DD/MM/AAAA)]: ");
                                    data = LocalDate.parse(scanner.nextLine(), formato);
                                    break;

                                case 5:
                                    System.out.print("[HORÁRIO (HH:MM)]: ");
                                    horario = LocalTime.parse(scanner.nextLine());
                                    break;

                                case 6:
                                    System.out.print("[VAGAS DISPONÍVEIS]: ");
                                    vagas = scanner.nextInt();
                                    scanner.nextLine();
                                    break;

                                default:
                                    System.out.println("┌───────────────────────────────────────────┐");
                                    System.out.println("|               OPÇÃO INVÁLIDA.             |");
                                    System.out.println("└───────────────────────────────────────────┘");
                                }
                                break;

                                case 0:
                                    System.out.println("┌───────────────────────────────────────────┐");
                                    System.out.println("|             CADASTRO CANCELADO.           |");
                                    System.out.println("└───────────────────────────────────────────┘");
                                    confirmar = true;
                                    break;

                                default:
                                System.out.println("┌───────────────────────────────────────────┐");
                                System.out.println("|               OPÇÃO INVÁLIDA.             |");
                                System.out.println("└───────────────────────────────────────────┘");
                                 }
                                }
                                break;
           
            case 2:               
             List<Evento> eventos = controller.listarEventos();
            
             System.out.println("┌────────────────────────────────────────────────────────────┐");
             System.out.println("|                    EVENTOS DISPONÍVEIS:                    |");
             System.out.println("└────────────────────────────────────────────────────────────┘");

             if (eventos.isEmpty()) {
             System.out.println("┌────────────────────────────────────────────────────────────┐");
             System.out.println("|                 NÃO HÁ EVENTOS CADASTRADOS!                |");
             System.out.println("└────────────────────────────────────────────────────────────┘");
             } 
             else {
                for (Evento evento : eventos) {
             evento.exibirInfo();
              }
             }
            break;

            case 3:
                eventos = controller.listarEventos();

                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|                       CONSULTAR EVENTOS:                   |");
                System.out.println("└────────────────────────────────────────────────────────────┘");

                for(int i = 0; i < eventos.size(); i++){
                    System.out.println("------------------------------------------------------------");
                    System.out.println((i + 1) + " - " + eventos.get(i).getNomeEvento());
                    System.out.println("------------------------------------------------------------");

                }
                System.out.println("┌──────────────────────────────────┐");
                System.out.println("|           VER DETALHES:          |");
                System.out.println("└──────────────────────────────────┘");
                System.out.print("|> ");
                int escolha = scanner.nextInt();
                scanner.nextLine();
                
                if(escolha >= 1 && escolha <= eventos.size()){
                    eventos.get(escolha - 1).exibirInfo();
                }
                else{
                    System.out.println("┌──────────────────────────────────────────────┐");
                    System.out.println("|                EVENTO INVÁLIDO.              |");
                    System.out.println("└──────────────────────────────────────────────┘");
                }
                break;

            case 4: 
            eventos = controller.listarEventos();
            
            System.out.println("┌────────────────────────────────────────────────────────────┐");
            System.out.println("|                      EVENTOS DISPONÍVEIS:                  |");
            System.out.println("└────────────────────────────────────────────────────────────┘");
            if(eventos.isEmpty()){
                System.out.println("┌──────────────────────────────────────────────────┐");
                System.out.println("|           NÃO HÁ EVENTOS CADASTRADOS!            |");
                System.out.println("└──────────────────────────────────────────────────┘");
                break;
            }

            for(int i = 0; i < eventos.size(); i++){
                System.out.println("------------------------------------------------------------");
                System.out.println((i + 1) + " - " + eventos.get(i).getNomeEvento());
                System.out.println("------------------------------------------------------------");
            }

            System.out.println("┌───────────────────────────────────────────────┐");
            System.out.println("| ESCOLHA O EVENTO QUE DESEJA REMOVER:          |");
            System.out.println("└───────────────────────────────────────────────┘");
            System.out.print("|> ");
            int numero = scanner.nextInt();
            scanner.nextLine();

            if(numero >= 1 && numero <= eventos.size()){
                try{
                    controller.remover(eventos.get(numero-1));
                    System.out.println("┌────────────────────────────────────────────────────────┐");
                    System.out.println("|               EVENTO REMOVIDO COM SUCESSO!             |");
                    System.out.println("└────────────────────────────────────────────────────────┘");
                
                }catch(EventoNaoEncontradoException erro){
                    System.out.println(erro.getMessage());
                }    
            }
            else{
                System.out.println("┌───────────────────────────────────────────┐");
                System.out.println("|               OPÇÃO INVÁLIDA.             |");
                System.out.println("└───────────────────────────────────────────┘");
            }
            break;

            case 0:
                System.out.println("┌───────────────────────────────────────────────────────┐");
                System.out.println("|               VOLTANDO AO MENU PRINCIPAL...           |");
                System.out.println("└───────────────────────────────────────────────────────┘");
                break;
            
            default:
                System.out.println("┌───────────────────────────────────────────┐");
                System.out.println("|               OPÇÃO INVÁLIDA.             |");
                System.out.println("└───────────────────────────────────────────┘");
   
        }

    }while (opcao != 0);
    
  }

}   
