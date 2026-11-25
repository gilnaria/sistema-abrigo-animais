package model.notificacao;

import java.time.LocalDateTime;

public class Notificacao {
    private final TipoNotificacao tipo;
    private final String mensagem;
    private final LocalDateTime dataHora;
    private boolean lida;

    public Notificacao(TipoNotificacao tipo, String mensagem) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
        this.lida = false;
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

    public boolean isLida() {
        return lida;
    }

    public void marcarComoLida() {
        this.lida = true;
    }

    public void exibirInfo() {
    System.out.println("[!] " + mensagem);
    }
}