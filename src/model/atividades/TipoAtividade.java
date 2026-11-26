package model.atividades;

public enum TipoAtividade {
    MUTIRAO_LIMPEZA("Mutirão de limpeza"),
    CAMPANHA_VACINACAO("Campanha de vacinação"),
    FEIRA_ADOCAO("Feira de adoção"),
    EVENTO_BENEFICENTE("Evento beneficente");

    private final String descricao;

    TipoAtividade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
