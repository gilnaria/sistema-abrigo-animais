package service;

import exception.DadoInvalidoException;
import exception.acesso.AcessoNegadoException;
import exception.atividades.EventoNaoEncontradoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.acesso.Usuario;
import model.atividades.Evento;
import repository.EventosRepository;
import repository.InscricaoRepository;
import util.Permissoes;
import util.Validador;

public class EventoService {
    private static final String MSG_SO_ADMIN = "[ APENAS ADMINISTRADORES PODEM GERENCIAR ATIVIDADES. ]";

    private final EventosRepository repositorio;
    private final InscricaoRepository inscricaoRepositorio;
    private final NotificacaoService notificacaoService;

    public EventoService(EventosRepository repositorio, InscricaoRepository inscricaoRepositorio,
            NotificacaoService notificacaoService) {
        this.repositorio = repositorio;
        this.inscricaoRepositorio = inscricaoRepositorio;
        this.notificacaoService = notificacaoService;
    }

    public void cadastrarEvento(Usuario usuario, Evento evento) throws AcessoNegadoException, DadoInvalidoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);

        if (evento == null) {
            throw new DadoInvalidoException("[ EVENTO NÃO PODE SER NULO. ]");
        }
        if (!Validador.textoValido(evento.getNomeEvento())) {
            throw new DadoInvalidoException("[ NOME DA ATIVIDADE NÃO PODE SER VAZIO. ]");
        }
        if (evento.getTipo() == null) {
            throw new DadoInvalidoException("[ TIPO DE ATIVIDADE É OBRIGATÓRIO. ]");
        }
        if (!Validador.textoValido(evento.getLocal())) {
            throw new DadoInvalidoException("[ NOME DO LOCAL NÃO PODE SER VAZIO. ]");
        }
        if (evento.getDataEvento() == null || evento.getDataEvento().isBefore(LocalDate.now())) {
            throw new DadoInvalidoException("[ DATA DA ATIVIDADE NÃO PODE ESTAR NO PASSADO. ]");
        }
        if (evento.getHorarioEvento() == null) {
            throw new DadoInvalidoException("[ HORÁRIO DA ATIVIDADE É OBRIGATÓRIO. ]");
        }
        if (evento.getVagas() <= 0) {
            throw new DadoInvalidoException("[ NÚMERO DE VAGAS DEVE SER MAIOR QUE ZERO. ]");
        }

        repositorio.adicionarEventos(evento);
        notificacaoService.notificarEvento(evento);
    }

    public List<Evento> listarEventos() {
        return repositorio.listarEventos();
    }

    public List<Evento> listarEventosDisponiveis() {
        List<Evento> disponiveis = new ArrayList<>();

        for (Evento evento : repositorio.listarEventos()) {
            if (!evento.isEncerrado()) {
                disponiveis.add(evento);
            }
        }
        return disponiveis;
    }

    public void remover(Usuario usuario, Evento evento) throws AcessoNegadoException, EventoNaoEncontradoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);

        if (evento == null || !repositorio.removerEventos(evento)) {
            throw new EventoNaoEncontradoException("[ EVENTO NÃO ENCONTRADO. ]");
        }

        inscricaoRepositorio.removerPorEvento(evento);
    }
}
