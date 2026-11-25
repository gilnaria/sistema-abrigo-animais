package controller;

import java.util.List;

import exception.saude.AcessoNegadoException;
import exception.saude.RegistroInvalidoException;
import exception.saude.VacinaDuplicadaException;
import model.Animal;
import model.acesso.Usuario;
import model.saude.HistoricoAnimal;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import model.saude.Vacina;
import service.SaudeService;

public class SaudeController {
    private SaudeService service;

    public SaudeController(SaudeService service) {
        this.service = service;
    }

    public void adicionarRegistro(Usuario usuario, RegistroMedico registro)
            throws AcessoNegadoException, RegistroInvalidoException, VacinaDuplicadaException {
        service.adicionarRegistro(usuario, registro);
    }

    public HistoricoAnimal buscarHistorico(Animal animal) {
        return service.buscarHistorico(animal);
    }

    public List<HistoricoAnimal> listarHistoricos() {
        return service.listarHistoricos();
    }

    public void atualizarStatusTratamento(Usuario usuario, Tratamento tratamento, StatusTratamento novoStatus)
            throws AcessoNegadoException, RegistroInvalidoException {
        service.atualizarStatusTratamento(usuario, tratamento, novoStatus);
    }

    public List<Tratamento> listarTratamentos(Animal animal) {
        return service.listarTratamentos(animal);
    }

    public List<Tratamento> listarTratamentosAtivos(Animal animal) {
        return service.listarTratamentosAtivos(animal);
    }

    public boolean possuiTratamentoCritico(Animal animal) {
        return service.possuiTratamentoCritico(animal);
    }

    public List<Vacina> listarVacinasProximasVencimento() {
        return service.listarVacinasProximasVencimento();
    }
}
