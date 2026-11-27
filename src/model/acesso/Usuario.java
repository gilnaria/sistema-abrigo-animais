package model.acesso;

import java.time.LocalDate;
import util.Validador;

public abstract class Usuario {
    private String nome;
    private final String cpf;
    private String email;
    private String senha;
    private final LocalDate dataCadastro;
    
    public Usuario(String nome, String cpf, String email, String senha){
        this.nome = nome;
        this.cpf = Validador.somenteDigitos(cpf);
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDate.now();
    }

    public abstract String getPerfil();

    public boolean verificarSenha(String senha){
        return this.senha != null && this.senha.equals(senha);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        return cpf.equals(((Usuario) obj).cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }

    @Override
    public String toString() {
        return nome + " (" + getPerfil() + ") - " + email + " - CPF " + cpf;
    }
}
