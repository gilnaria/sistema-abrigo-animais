package model.notificacao;

public class AvisoGeral implements Notificavel {
    private final String mensagem;

    public AvisoGeral(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.AVISO_GERAL;
    }

    @Override
    public String gerarMensagemNotificacao() {
        return mensagem;
    }
}