package controller;

import exception.acesso.AcessoNegadoException;
import exception.saude.RegistroInvalidoException;
import exception.saude.VacinaDuplicadaException;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import service.SaudeService;

public class SaudeController {
    private final SaudeService service;

    public SaudeController(SaudeService service) {
        this.service = service;
    }

    public void adicionarRegistro(Usuario usuario, RegistroMedico registro)
            throws AcessoNegadoException, RegistroInvalidoException, VacinaDuplicadaException {
        service.adicionarRegistro(usuario, registro);
    }

    public void atualizarStatusTratamento(Usuario usuario, Tratamento tratamento, StatusTratamento status)
            throws AcessoNegadoException, RegistroInvalidoException {
        service.atualizarStatusTratamento(usuario, tratamento, status);
    }

    public void atualizarEstadoSaude(Usuario usuario, Animal animal, String estado)
            throws AcessoNegadoException, RegistroInvalidoException {
        service.atualizarEstadoSaude(usuario, animal, estado);
    }

    public List<RegistroMedico> listarRegistros(Animal animal) {
        return service.listarRegistros(animal);
    }

    public List<Tratamento> listarTratamentosAtivos(Animal animal) {
        return service.listarTratamentosAtivos(animal);
    }

    public String emitirRelatorioSaude(Usuario usuario, Animal animal)
            throws AcessoNegadoException, RegistroInvalidoException {
        return service.emitirRelatorioSaude(usuario, animal);
    }
}
