package model.saude;

public enum StatusTratamento {
    EM_ANDAMENTO("Em andamento"),
    CRITICO("Crítico"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusTratamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}