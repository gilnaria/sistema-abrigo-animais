package service;

import exception.atividades.EventoEncerradoException;
import exception.atividades.InscricaoDuplicadaException;
import exception.atividades.InscricaoNaoEncontradaException;
import exception.atividades.VagasEsgotadasException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.acesso.Voluntario;
import model.atividades.Evento;
import model.atividades.Inscricao;
import repository.EventosRepository;
import repository.InscricaoRepository;

public class InscricaoService {
    private final InscricaoRepository repositorio;
    private final EventosRepository repositorioEvento;

    public InscricaoService(InscricaoRepository repositorio, EventosRepository repositorioEvento) {
        this.repositorio = repositorio;
        this.repositorioEvento = repositorioEvento;
    }

    public int vagasRestantes(Evento evento) {
        return evento.getVagas() - repositorio.contarInscricoes(evento);
    }

    public void realizarInscricao(Voluntario voluntario, Evento evento)
            throws InscricaoDuplicadaException, EventoEncerradoException, VagasEsgotadasException {
        if (evento.isEncerrado()) {
            throw new EventoEncerradoException("[ ESTE EVENTO JÁ ACONTECEU. ]");
        }
        if (repositorio.existeInscricao(voluntario, evento)) {
            throw new InscricaoDuplicadaException("[ VOCÊ JÁ ESTÁ INSCRITO NESTE EVENTO. ]");
        }
        if (vagasRestantes(evento) <= 0) {
            throw new VagasEsgotadasException("[ NÃO HÁ MAIS VAGAS DISPONÍVEIS. ]");
        }

        repositorio.adicionarInscricao(new Inscricao(voluntario, evento, LocalDate.now()));
    }

    public List<Inscricao> listarHistorico(Voluntario voluntario) {
        List<Inscricao> historico = new ArrayList<>();
        List<Evento> eventos = repositorioEvento.listarEventos();

        for (Inscricao inscricao : repositorio.listarInscricoes()) {
            if (inscricao.getVoluntario().equals(voluntario) && eventos.contains(inscricao.getEvento())) {
                historico.add(inscricao);
            }
        }
        return historico;
    }

    public List<Inscricao> listarInscritos(Evento evento) {
        return repositorio.buscarPorEvento(evento);
    }

    public void cancelarInscricao(Inscricao inscricao) throws InscricaoNaoEncontradaException, EventoEncerradoException {
        if (inscricao == null || !repositorio.listarInscricoes().contains(inscricao)) {
            throw new InscricaoNaoEncontradaException("[ INSCRIÇÃO NÃO ENCONTRADA. ]");
        }
        if (inscricao.getEvento().isEncerrado()) {
            throw new EventoEncerradoException("[ NÃO É POSSÍVEL CANCELAR A INSCRIÇÃO DE UM EVENTO JÁ REALIZADO. ]");
        }

        repositorio.remover(inscricao);
    }
}
