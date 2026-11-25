package controller;

import java.util.List;

import exception.AcessoNegadoException;
import exception.NotificacaoInvalidaException;
import model.Adocao;
import model.Animal;
import model.acesso.Usuario;
import model.atividades.Evento;
import model.notificacao.Notificacao;
import service.NotificacaoService;

public class NotificacaoController {
    private NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    public void notificarNovoAnimal(Animal animal) throws NotificacaoInvalidaException {
        service.notificarNovoAnimal(animal);
    }

    public void notificarEvento(Evento evento) throws NotificacaoInvalidaException {
        service.notificarEvento(evento);
    }

    public void notificarAtualizacaoAdocao(Adocao adocao) throws NotificacaoInvalidaException {
        service.notificarAtualizacaoAdocao(adocao);
    }

    public void publicarAvisoGeral(Usuario usuario, String mensagem)
            throws AcessoNegadoException, NotificacaoInvalidaException {
        service.notificarAvisoGeral(usuario, mensagem);
    }

    public void exibirNaoLidas(List<Evento> eventos) {
        service.verificarVacinasProximas();
        service.verificarEventosProximos(eventos);

        List<Notificacao> naoLidas = service.listarNaoLidas();

        if (naoLidas.isEmpty()) {
            System.out.println("Nenhuma notificação nova.");
            return;
        }

        for (Notificacao notificacao : naoLidas) {
            notificacao.exibirInfo();
            System.out.println("------------------------");
        }

        service.marcarTodasComoLidas();
    }
}