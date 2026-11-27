package model.acesso;

public class Administrador extends Usuario {
    public Administrador(String nome, String cpf, String email, String senha){
        super(nome, cpf, email, senha);
    }

    @Override
    public String getPerfil() {
        return "Administrador";
    }
}
