package model;

import java.time.LocalDate;
import util.Formato;

public class Animal implements Exibivel {
    public static final int IDADE_MAXIMA = 40;

    private static int proximoCodigo = 1;

    public enum StatusAdocao {
        DISPONIVEL("Disponível"),
        EM_PROCESSO("Em processo de adoção"),
        EM_TRATAMENTO("Em tratamento"),
        ADOTADO("Adotado");

        private final String descricao;

        StatusAdocao(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }

    public enum Porte {
        PEQUENO("Pequeno"),
        MEDIO("Médio"),
        GRANDE("Grande");

        private final String descricao;

        Porte(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }

    private final int codigo;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Porte porte;
    private String estadoSaude;
    private final LocalDate dataResgate;
    private StatusAdocao statusAdocao;

    public Animal(String nome, String especie, String raca, int idade, Porte porte, String estadoSaude,
            LocalDate dataResgate, StatusAdocao statusAdocao) {
        this.codigo = proximoCodigo++;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.estadoSaude = estadoSaude;
        this.dataResgate = dataResgate;
        this.statusAdocao = statusAdocao;
    }

    public static int getTotalCadastrados() {
        return proximoCodigo - 1;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public Porte getPorte() {
        return porte;
    }

    public String getEstadoSaude() {
        return estadoSaude;
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public void setEstadoSaude(String estadoSaude) {
        this.estadoSaude = estadoSaude;
    }

    public void setStatusAdocao(StatusAdocao statusAdocao) {
        this.statusAdocao = statusAdocao;
    }

    public boolean isAdotado() {
        return statusAdocao == StatusAdocao.ADOTADO;
    }

    public boolean aceitaSolicitacoes() {
        return statusAdocao == StatusAdocao.DISPONIVEL || statusAdocao == StatusAdocao.EM_PROCESSO;
    }

    @Override
    public void exibirInfo() {
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Espécie: " + especie + " | Raça: " + raca);
        System.out.println("Idade: " + idade + " ano(s) | Porte: " + porte);
        System.out.println("Estado de saúde: " + estadoSaude);
        System.out.println("Data de resgate: " + Formato.data(dataResgate));
        System.out.println("Status: " + statusAdocao);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Animal)) {
            return false;
        }
        return codigo == ((Animal) obj).codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nome + " - " + especie + " (" + raca + ") - " + statusAdocao;
    }
}
