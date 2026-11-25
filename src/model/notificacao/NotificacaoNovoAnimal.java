package model.notificacao;

import model.Animal;

public class NotificacaoNovoAnimal implements Notificavel {
    private final Animal animal;

    public NotificacaoNovoAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.NOVO_ANIMAL;
    }

    @Override
    public String gerarMensagemNotificacao() {
        return "Novo animal disponível para adoção: " + animal.getNome() + ".";
    }
}