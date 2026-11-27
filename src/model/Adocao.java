package model;

import java.time.LocalDate;
import model.acesso.Voluntario;
import util.Formato;

public class Adocao implements Exibivel {
    private final Voluntario voluntario;
    private final Animal animal;
    private final LocalDate dataSolicitacao;
    private StatusSolicitacao statusSolicitacao;
    private LocalDate dataAdocao;
    private LocalDate dataDevolucao;

    public Adocao(Voluntario voluntario, Animal animal) {
        this.voluntario = voluntario;
        this.animal = animal;
        this.dataSolicitacao = LocalDate.now();
        this.statusSolicitacao = StatusSolicitacao.PENDENTE;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public StatusSolicitacao getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isPendente() {
        return statusSolicitacao == StatusSolicitacao.PENDENTE;
    }

    public boolean isConcluida() {
        return dataAdocao != null;
    }

    public void aprovar() {
        this.statusSolicitacao = StatusSolicitacao.APROVADA;
        this.dataAdocao = LocalDate.now();
    }

    public void rejeitar() {
        this.statusSolicitacao = StatusSolicitacao.REJEITADA;
    }

    public void registrarDevolucao() {
        this.statusSolicitacao = StatusSolicitacao.DEVOLVIDA;
        this.dataDevolucao = LocalDate.now();
    }

    @Override
    public void exibirInfo() {
        System.out.println("Animal: " + animal.getNome() + " (código " + animal.getCodigo() + ")");
        System.out.println("Candidato: " + voluntario.getNome() + " | CPF: " + voluntario.getCpf());
        System.out.println("Solicitada em: " + Formato.data(dataSolicitacao) + " | Status: " + statusSolicitacao);
        if (dataAdocao != null) {
            System.out.println("Adoção concluída em: " + Formato.data(dataAdocao));
        }
        if (dataDevolucao != null) {
            System.out.println("Devolvido em: " + Formato.data(dataDevolucao));
        }
    }

    @Override
    public String toString() {
        return animal.getNome() + " <- " + voluntario.getNome() + " [" + statusSolicitacao + "] "
                + Formato.data(dataSolicitacao);
    }
}
