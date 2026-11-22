package view.atividades;

import model.acesso.Voluntario;
import model.atividades.Evento;
import model.atividades.Inscricao;
import controller.EventosController;
import controller.InscricoesController;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;



public class TelaInscricao {
    private EventosController controllerEvento;
    private InscricoesController controllerInscricao;
    private Voluntario voluntario;
    private Scanner scanner;

    public TelaInscricao(EventosController controllerEvento, InscricoesController controllerInscricao, Voluntario voluntario,
        Scanner scanner){
        this.controllerEvento = controllerEvento;
        this.controllerInscricao = controllerInscricao;
        this.voluntario = voluntario;
        this.scanner = scanner;

    }

    public void exibirEventos(){
        List<Evento> eventos = controllerEvento.listarEventos();
            
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
    }

    public void cadastrarInscricao(){
        List <Evento> eventos = controllerEvento.listarEventos();

        if (eventos.isEmpty()) {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("|                 NÃO HÁ EVENTOS DISPONÍVEIS!                |");
        System.out.println("└────────────────────────────────────────────────────────────┘");
         
         return; 
        } 

        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("|                    EVENTOS DISPONÍVEIS:                    |");
        System.out.println("└────────────────────────────────────────────────────────────┘");

        System.out.println("------------------------------------------------------------");
        System.out.printf("%-4s %-25s %-25s %5s%n","Nº", "NOME", "TIPO", "VAGAS");
        System.out.println("------------------------------------------------------------");

        for(int i = 0; i < eventos.size(); i++){
            
            System.out.printf("%-4d %-25s %-25s %5d%n", i + 1, eventos.get(i).getNomeEvento(), 
            eventos.get(i).getTipo(), eventos.get(i).getVagas());
        
            System.out.println("------------------------------------------------------------");

        }    

            System.out.println("┌─────────────────────────────────┐");
            System.out.println("| 1 - [ VER DETALHES ]            |");
            System.out.println("| 2 - [ INSCREVER ]               |");
            System.out.println("| 3 - [ VOLTAR ]                  |");
            System.out.println("└─────────────────────────────────┘");
             System.out.print("|> ");
             int op = scanner.nextInt();
             scanner.nextLine();

             switch(op){
                case 1:
                    System.out.println("┌───────────────────────────────────────────────┐");
                    System.out.println("| DIGITE O NÚMERO DO EVENTO:                    |");
                    System.out.println("└───────────────────────────────────────────────┘"); 
                    System.out.print("|> "); 
                    int numDetalhes = scanner.nextInt();
                    scanner.nextLine();

                    if(numDetalhes >= 1 && numDetalhes <= eventos.size()){

                        Evento eventoEscolhido = eventos.get(numDetalhes - 1);

                        eventoEscolhido.exibirInfo();

                        System.out.println("┌─────────────────────────────────┐");
                        System.out.println("| 1 - [ INSCREVER-SE ]            |");
                        System.out.println("| 0 - [ VOLTAR ]                  |");
                        System.out.println("└─────────────────────────────────┘");
                        System.out.print("|> "); 
                        int op2 = scanner.nextInt();
                        scanner.nextLine();

                        switch(op2){
                            case 1:
                                boolean verificacao = controllerInscricao.realizarInscricao(voluntario, eventoEscolhido);

                                if(verificacao){
                                System.out.println("┌────────────────────────────────────────────────────────────┐");
                                System.out.println("|               INSCRIÇÃO EFETUADA COM SUCESSO!              |");
                                System.out.println("└────────────────────────────────────────────────────────────┘");
                                }
                                else{
                                    System.out.println("┌────────────────────────────────────────────────────────────┐");
                                    System.out.println("|                      INSCRIÇÃO INVÁLIDA.                   |");
                                    System.out.println("└────────────────────────────────────────────────────────────┘");
                                }

                            break;    

                            case 0:
                                break;

                            default:
                                System.out.println("┌───────────────────────────────────────────┐");
                                System.out.println("|               OPÇÃO INVÁLIDA.             |");
                                System.out.println("└───────────────────────────────────────────┘");    
                        }

                    }
                    else{
                    System.out.println("┌──────────────────────────────────────────────┐");
                    System.out.println("|                EVENTO INVÁLIDO.              |");
                    System.out.println("└──────────────────────────────────────────────┘");
                }

                break;

                case 2:
                    System.out.println("┌───────────────────────────────────────────────┐");
                    System.out.println("| DIGITE O NÚMERO DO EVENTO:                    |");
                    System.out.println("└───────────────────────────────────────────────┘"); 
                    System.out.print("|> "); 
                   int numInscricao = scanner.nextInt();
                   scanner.nextLine();

                   if(numInscricao >= 1 && numInscricao <= eventos.size()){
                    
                    Evento eventoEscolhido = eventos.get(numInscricao - 1);

                    boolean verificacao = controllerInscricao.realizarInscricao(voluntario, eventoEscolhido);

                    if(verificacao){
                    System.out.println("┌────────────────────────────────────────────────────────────┐");
                    System.out.println("|               INSCRIÇÃO EFETUADA COM SUCESSO!              |");
                    System.out.println("└────────────────────────────────────────────────────────────┘");
                    }
                    else{
                        System.out.println("┌────────────────────────────────────────────────────────────┐");
                        System.out.println("|                      INSCRIÇÃO INVÁLIDA.                   |");
                        System.out.println("└────────────────────────────────────────────────────────────┘");
                    }

                    }
                    else{
                    System.out.println("┌──────────────────────────────────────────────┐");
                    System.out.println("|                EVENTO INVÁLIDO.              |");
                    System.out.println("└──────────────────────────────────────────────┘");
                }

                break;

                case 3:
                System.out.println("┌───────────────────────────────────────────────────────┐");
                System.out.println("|               VOLTANDO AO MENU PRINCIPAL...           |");
                System.out.println("└───────────────────────────────────────────────────────┘");

                break;

                default:
                System.out.println("┌───────────────────────────────────────────┐");
                System.out.println("|               OPÇÃO INVÁLIDA.             |");
                System.out.println("└───────────────────────────────────────────┘");

            }
        }
        
        public void exibirHistorico(){
            List <Inscricao> historico = controllerInscricao.listarHistorico(voluntario);

            System.out.println("┌────────────────────────────────────────────────────────────┐");
            System.out.println("|                    HISTÓRICO DE INSCRIÇÕES                 |");
            System.out.println("└────────────────────────────────────────────────────────────┘");

            if(historico.isEmpty()){
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|               NÃO HÁ HISTÓRICO DE INSCRIÇÕES!              |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
           
                return;
            }

            System.out.println("------------------------------------------------------------");
            System.out.printf("%-5s %-25s %-20s %-15s%n","Nº", "EVENTO", "TIPO", "DATA");
            System.out.println("------------------------------------------------------------");

            for(int i = 0; i < historico.size(); i++){
                Inscricao inscricao = historico.get(i);

                System.out.printf("%-5d %-25s %-20s %-15s%n",(i + 1),inscricao.getEvento().getNomeEvento(),
                inscricao.getEvento().getTipo(), inscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

        }


        public void cancelarInscricao(){
            List<Inscricao> historico = controllerInscricao.listarHistorico(voluntario);
            
            System.out.println("┌────────────────────────────────────────────────────────────┐");
            System.out.println("|                    HISTÓRICO DE INSCRIÇÕES                 |");
            System.out.println("└────────────────────────────────────────────────────────────┘");
            
            if(historico.isEmpty()){
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|               NÃO HÁ HISTÓRICO DE INSCRIÇÕES!              |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
           
                return;
            }
            
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-5s %-25s %-20s %-15s%n","Nº", "EVENTO", "TIPO", "DATA");
            System.out.println("------------------------------------------------------------");

            for(int i = 0; i < historico.size(); i++){
                Inscricao inscricao = historico.get(i);

                System.out.printf("%-5d %-25s %-20s %-15s%n",(i + 1),inscricao.getEvento().getNomeEvento(),
                inscricao.getEvento().getTipo(), inscricao.getDataInscricao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            System.out.println("┌───────────────────────────────────────────────┐");
            System.out.println("| DIGITE O NÚMERO DA INSCRIÇÃO:                 |");
            System.out.println("└───────────────────────────────────────────────┘"); 
            System.out.print("|> "); 
            int numInscricao = scanner.nextInt();
            scanner.nextLine();
            
            if(numInscricao >= 1 && numInscricao <= historico.size()){
                
                Inscricao inscricaoCancelada = historico.get(numInscricao - 1);

                controllerInscricao.cancelarInscricao(inscricaoCancelada);
 
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|               INSCRIÇÃO CANCELADA COM SUCESSO!             |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
            
            }
            else{
                System.out.println("┌────────────────────────────────────────────────────────────┐");
                System.out.println("|                NÚMERO DE INSCRIÇÃO INVÁLIDO.               |");
                System.out.println("└────────────────────────────────────────────────────────────┘");
           
            }
        }    
    } 
    