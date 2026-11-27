package controller;

import exception.atividades.EventoEncerradoException;
import exception.atividades.InscricaoDuplicadaException;
import exception.atividades.InscricaoNaoEncontradaException;
import exception.atividades.VagasEsgotadasException;
import java.util.List;
import model.acesso.Voluntario;
import model.atividades.Evento;
import model.atividades.Inscricao;
import service.InscricaoService;

public class InscricoesController {
    private final InscricaoService service;

    public InscricoesController(InscricaoService service) {
        this.service = service;
    }

    public void realizarInscricao(Voluntario voluntario, Evento evento)
            throws InscricaoDuplicadaException, EventoEncerradoException, VagasEsgotadasException {
        service.realizarInscricao(voluntario, evento);
    }

    public int vagasRestantes(Evento evento) {
        return service.vagasRestantes(evento);
    }

    public List<Inscricao> listarHistorico(Voluntario voluntario) {
        return service.listarHistorico(voluntario);
    }

    public List<Inscricao> listarInscritos(Evento evento) {
        return service.listarInscritos(evento);
    }

    public void cancelarInscricao(Inscricao inscricao) throws InscricaoNaoEncontradaException, EventoEncerradoException {
        service.cancelarInscricao(inscricao);
    }
}