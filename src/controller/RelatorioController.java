package controller;

import exception.acesso.AcessoNegadoException;
import java.time.YearMonth;
import java.util.List;
import model.Animal;
import model.acesso.Usuario;
import service.RelatorioService;

public class RelatorioController {
    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    public int totalAnimaisResgatados(Usuario usuario) throws AcessoNegadoException {
        return service.totalAnimaisResgatados(usuario);
    }

    public int totalAdocoesRealizadas(Usuario usuario) throws AcessoNegadoException {
        return service.totalAdocoesRealizadas(usuario);
    }

    public List<String> especiesMaisResgatadas(Usuario usuario) throws AcessoNegadoException {
        return service.especiesMaisResgatadas(usuario);
    }

    public double taxaAdocaoMensal(Usuario usuario, YearMonth mes) throws AcessoNegadoException {
        return service.taxaAdocaoMensal(usuario, mes);
    }

    public int voluntariosAtivos(Usuario usuario) throws AcessoNegadoException {
        return service.voluntariosAtivos(usuario);
    }

    public List<Animal> animaisEmTratamento(Usuario usuario) throws AcessoNegadoException {
        return service.animaisEmTratamento(usuario);
    }
}
