package model;

import java.time.LocalDate;

public class Animal {
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String porte;
    private String estadoSaude;
    private final LocalDate dataResgate;
    private String statusAdocao;

    public Animal(String nome, String especie, String raca, int idade, String porte, String estadoSaude, String statusAdocao){
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.estadoSaude = estadoSaude;
        this.statusAdocao = statusAdocao;
        this.dataResgate = LocalDate.now();
    }

    public String getNome(){
        return nome;
    }
    public String getEspecie(){
        return especie;
    }
    public String getRaca(){
        return raca;
    }
    public int getIdade(){
        return idade;
    }
    public String getPorte(){
        return porte;
    }
    public String getEstadoSaude(){
        return estadoSaude;
    }
    public LocalDate getDataResgate(){
        return dataResgate;
    }
    public String getStatusAdocao(){
        return statusAdocao;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }
    public void setPorte(String porte){
        this.porte = porte;
    }
    public void setEstadoSaude(String estadoSaude){
        this.estadoSaude = estadoSaude;
    }
    public void setStatusAdocao(String statusAdocao){
        this.statusAdocao = statusAdocao;
    }
}
