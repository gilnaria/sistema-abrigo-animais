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
        String animal = adocao.getAnimal().getNome();
        String candidato = adocao.getVoluntario().getNome();

        switch (adocao.getStatusSolicitacao()) {
            case APROVADA:
                return "A adoção de " + animal + " por " + candidato + " foi aprovada.";
            case REJEITADA:
                return "A solicitação de adoção de " + animal + " por " + candidato + " foi rejeitada.";
            case DEVOLVIDA:
                return "O animal " + animal + " foi devolvido por " + candidato + " e voltou ao abrigo.";
            default:
                return "Nova solicitação de adoção de " + animal + " por " + candidato + ".";
        }
    }
}