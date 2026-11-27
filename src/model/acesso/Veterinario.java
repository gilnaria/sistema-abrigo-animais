package model.acesso;

public class Veterinario extends Usuario{
    public Veterinario(String nome, String cpf, String email, String senha){
        super(nome, cpf, email, senha);
    }
    
    @Override
    public String getPerfil() {
        return "Veterinário";
    }
}
