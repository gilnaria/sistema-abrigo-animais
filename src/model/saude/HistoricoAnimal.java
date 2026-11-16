package model.saude;
import java.util.ArrayList;

public class HistoricoAnimal {
    
private ArrayList <RegistroMedico> registros = new ArrayList<>();

public void adicionarRegistro(RegistroMedico registro){

registros.add(registro);

}

public ArrayList<RegistroMedico> getRegistros(){
    return registros;
}
// uma porta de saida da lista de registros

}
