package model;

import java.time.LocalDate;

public abstract class Usuario {
    private String nome;
    private final String cpf;
    private String email;
    private String senha;
    private final LocalDate dataCadastro;

    //fazer verificação com exception depois
    public Usuario(String nome, String cpf, String email, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDate.now();
    }

    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getEmail(){
        return email;
    }
    public LocalDate getDataCadastro(){
        return dataCadastro;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email= email;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
}
