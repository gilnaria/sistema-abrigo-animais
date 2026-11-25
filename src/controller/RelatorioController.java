package controller;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import exception.PermissaoInvalidaException;
import model.acesso.Usuario;
import model.saude.Tratamento;
import service.RelatorioService;

public class RelatorioController {
    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    public int quantidadeAnimaisResgatados(Usuario usuario) throws PermissaoInvalidaException {
        return service.quantidadeAnimaisResgatados(usuario);
    }

    public int quantidadeAdocoesRealizadas(Usuario usuario) throws PermissaoInvalidaException {
        return service.quantidadeAdocoesRealizadas(usuario);
    }

    public Map<String, Integer> especiesMaisResgatadas(Usuario usuario) throws PermissaoInvalidaException {
        return service.especiesMaisResgatadas(usuario);
    }

    public Map<YearMonth, Double> taxaAdocaoMensal(Usuario usuario) throws PermissaoInvalidaException {
        return service.taxaAdocaoMensal(usuario);
    }

    public int quantidadeVoluntariosAtivos(Usuario usuario) throws PermissaoInvalidaException {
        return service.quantidadeVoluntariosAtivos(usuario);
    }

    public List<Tratamento> tratamentosEmAndamento(Usuario usuario) throws PermissaoInvalidaException {
        return service.tratamentosEmAndamento(usuario);
    }
}
