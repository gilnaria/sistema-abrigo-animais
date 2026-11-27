package model.notificacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Exibivel;
import model.acesso.Usuario;
import util.Formato;

public class Notificacao implements Exibivel {
    private final TipoNotificacao tipo;
    private final String mensagem;
    private final LocalDateTime dataHora;
    private final List<String> lidaPor = new ArrayList<>();

    public Notificacao(TipoNotificacao tipo, String mensagem) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public boolean foiLidaPor(Usuario usuario) {
        return lidaPor.contains(usuario.getCpf());
    }

    public void marcarComoLida(Usuario usuario) {
        if (!lidaPor.contains(usuario.getCpf())) {
            lidaPor.add(usuario.getCpf());
        }
    }

    @Override
    public void exibirInfo() {
        System.out.println("[" + Formato.dataHora(dataHora) + "] [" + tipo + "] " + mensagem);
    }
}