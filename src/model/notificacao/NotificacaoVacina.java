package model.notificacao;

import model.saude.Vacina;
import util.Formato;

public class NotificacaoVacina implements Notificavel {
    private final Vacina vacina;

    public NotificacaoVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.VACINA_VENCIMENTO;
    }

    @Override
    public String gerarMensagemNotificacao() {
        return "Vacina " + vacina.getNomeVacina() + " de " + vacina.getAnimal().getNome()
                + " vence em " + Formato.data(vacina.getDataProximaDose()) + ".";
    }
}
