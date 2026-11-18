package repository;

import java.util.ArrayList;
import java.util.List;
import model.Voluntario;


public class VoluntarioRepository {
    private final List<Voluntario> voluntarios = new ArrayList<>();

    public void adicionar(Voluntario voluntario) {
        voluntarios.add(voluntario);
    }
}
