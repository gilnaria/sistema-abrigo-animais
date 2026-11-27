package controller;

import exception.acesso.AcessoNegadoException;
import java.util.List;
import model.acesso.Usuario;
import model.atividades.Evento;
import model.notificacao.Notificacao;
import service.EventoService;
import service.NotificacaoService;

public class NotificacaoController {
    private final NotificacaoService service;
    private final EventoService eventoService;

    public NotificacaoController(NotificacaoService service, EventoService eventoService) {
        this.service = service;
        this.eventoService = eventoService;
    }

    public void publicarAvisoGeral(Usuario usuario, String mensagem) throws AcessoNegadoException {
        service.notificarAvisoGeral(usuario, mensagem);
    }

    public int contarNaoLidas(Usuario usuario) {
        return service.contarNaoLidas(usuario, eventoService.listarEventos());
    }

    public List<Notificacao> obterNaoLidas(Usuario usuario) {
        List<Evento> eventos = eventoService.listarEventos();
        service.verificarVacinasProximas();
        service.verificarEventosProximos(eventos);
        return service.listarNaoLidas(usuario);
    }

    public List<Notificacao> listarTodas() {
        return service.listarNotificacoes();
    }

    public void marcarTodasComoLidas(Usuario usuario) {
        service.marcarTodasComoLidas(usuario);
    }
}