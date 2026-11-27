package model.notificacao;

public enum TipoNotificacao {
    NOVO_ANIMAL("Novo animal"),
    PROXIMO_EVENTO("Evento"),
    VACINA_VENCIMENTO("Vacina"),
    ATUALIZACAO_ADOCAO("Adoção"),
    AVISO_GERAL("Aviso geral");

    private final String descricao;

    TipoNotificacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
