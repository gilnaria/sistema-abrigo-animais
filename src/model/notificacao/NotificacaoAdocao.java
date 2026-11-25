package model.notificacao;

import model.Adocao;

public class NotificacaoAdocao implements Notificavel {
    private final Adocao adocao;

    public NotificacaoAdocao(Adocao adocao) {
        this.adocao = adocao;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.ATUALIZACAO_ADOCAO;
    }

    @Override
    public String gerarMensagemNotificacao() {
        return "Sua solicitação de adoção de " + adocao.getAnimal().getNome() + " foi "
        + adocao.getStatusSolicitacao().toString().toLowerCase() + ".";
    }
}