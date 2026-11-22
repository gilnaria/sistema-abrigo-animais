package controller;

import model.atividades.Evento;
import model.atividades.Inscricao;
import model.acesso.Voluntario;
import repository.InscricaoRepository;
import repository.EventosRepository;
import exception.atividades.InscricaoDuplicadaException;
import exception.atividades.VagasEsgotadasException;
import exception.atividades.EventoEncerradoException;
import exception.atividades.InscricaoNaoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InscricoesController {
    private InscricaoRepository repositorio;
    private EventosRepository repositorioEvento;

    public InscricoesController(InscricaoRepository repositorio, EventosRepository repositorioEvento){
        this.repositorio = repositorio;
        this.repositorioEvento = repositorioEvento;
    }

    public int vagasRestantes(Evento evento) {
    return evento.getVagas() - repositorio.contarInscricoes(evento);
    }

    public void realizarInscricao(Voluntario voluntario, Evento evento)
        throws InscricaoDuplicadaException, EventoEncerradoException, VagasEsgotadasException {

    if(evento.getDataEvento().isBefore(LocalDate.now())){
        throw new EventoEncerradoException("[ ESTE EVENTO JÁ ACONTECEU. ]");
    }

    if(repositorio.existeInscricao(voluntario, evento)){
        throw new InscricaoDuplicadaException("[ VOCÊ JÁ ESTÁ INSCRITO NESTE EVENTO. ]");
    }

    if(repositorio.contarInscricoes(evento) >= evento.getVagas()){
        throw new VagasEsgotadasException("[ NÃO HÁ MAIS VAGAS DISPONÍVEIS. ]");
    }

    repositorio.adicionarInscricao(new Inscricao(voluntario, evento, LocalDate.now()));
}
    
    public List<Inscricao> listarHistorico(Voluntario voluntario) {
    List<Inscricao> historico = new ArrayList<>();

    for (Inscricao inscricao : repositorio.listarInscricoes()) {
        if (inscricao.getVoluntario().equals(voluntario)
        && repositorioEvento.listarEventos().contains(inscricao.getEvento())) {
    historico.add(inscricao);
}
    }

    return historico;
    }

    public void cancelarInscricao(Inscricao inscricao) throws InscricaoNaoEncontradaException{
        if(inscricao == null || !repositorio.listarInscricoes().contains(inscricao)){
            throw new InscricaoNaoEncontradaException("[ INSCRIÇÃO NÃO ENCONTRADA. ]");
        }
        repositorio.remover(inscricao);
    }

    }