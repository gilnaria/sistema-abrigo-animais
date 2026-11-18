package view.atividades;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.atividades.Evento;
import model.atividades.TipoAtividade;
import controller.EventosController;

public class TelaCadastroEvento {
    private EventosController controller;

    public TelaCadastroEvento(EventosController controller){
        this.controller = controller;
    }

    public void exibirCadastroEvento(){
        String nome = JOptionPane.showInputDialog("Digite o nome do evento: ");

        TipoAtividade[] tipos = TipoAtividade.values();

        TipoAtividade tipo = (TipoAtividade) JOptionPane.showInputDialog(null, 
            "Selecione o tipo de atividade: ",
            "Tipo de atividade",
            JOptionPane.QUESTION_MESSAGE,
            null,
            tipos,
            tipos[0]);
    
        String local = JOptionPane.showInputDialog("Digite o local: ");

        String data = JOptionPane.showInputDialog("Digite a data: ");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataEvento = LocalDate.parse(data, formatoData);

        String horario = JOptionPane.showInputDialog("Digite o horário: ");
        LocalTime horarioEvento = LocalTime.parse(horario);


        String vagasTexto = JOptionPane.showInputDialog("Digite a quantidade de vagas:");
        int vagas = Integer.parseInt(vagasTexto);

        Evento evento = new Evento(nome, tipo, local, dataEvento, horarioEvento, vagas);

    controller.cadastrarEvento(evento);

    JOptionPane.showMessageDialog(null, "Evento cadastrado com sucesso!");

    }
    
}
