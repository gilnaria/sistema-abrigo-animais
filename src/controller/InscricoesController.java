package controller;

import model.atividades.Evento;
import model.atividades.Inscricao;
import model.acesso.Voluntario;
import exception.atividades.InscricaoDuplicadaException;
import exception.atividades.VagasEsgotadasException;
import exception.atividades.EventoEncerradoException;
import exception.atividades.InscricaoNaoEncontradaException;
import service.InscricaoService;


import java.util.List;

public class InscricoesController {
    private InscricaoService service;

    public InscricoesController(InscricaoService service){
        this.service = service;
    }

    public int vagasRestantes(Evento evento) {
        return service.vagasRestantes(evento);
    }

    public void realizarInscricao(Voluntario voluntario, Evento evento) throws InscricaoDuplicadaException, 
    EventoEncerradoException, VagasEsgotadasException {
        service.realizarInscricao(voluntario, evento);
    }
    
    public List<Inscricao> listarHistorico(Voluntario voluntario) {
        return service.listarHistorico(voluntario);
    }

    public void cancelarInscricao(Inscricao inscricao) throws InscricaoNaoEncontradaException{
        service.cancelarInscricao(inscricao);
    }
}