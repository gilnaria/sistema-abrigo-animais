package view.adocao;

import controller.AdocaoController;
import controller.AnimalController;
import exception.AbrigoException;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.Candidatura;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import util.Entrada;
import view.TelaBase;

public class TelaAdocao extends TelaBase {
    private final AdocaoController adocoes;
    private final AnimalController animais;

    public TelaAdocao(AdocaoController adocoes, AnimalController animais) {
        this.adocoes = adocoes;
        this.animais = animais;
    }

    public void exibirMenuVoluntario(Voluntario voluntario) {
        int opcao;

        do {
            menu("ADOÇÃO E RESPONSABILIDADE",
                    "1 - Solicitar adoção de um animal",
                    "2 - Minhas solicitações de adoção",
                    "3 - Candidatar-me a cuidar de um animal",
                    "4 - Minhas candidaturas de cuidado",
                    "5 - Animais sob minha responsabilidade",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    solicitarAdocao(voluntario);
                    break;
                case 2:
                    listar("MINHAS SOLICITAÇÕES", adocoes.listarSolicitacoesDoVoluntario(voluntario));
                    break;
                case 3:
                    candidatar(voluntario);
                    break;
                case 4:
                    listar("MINHAS CANDIDATURAS", adocoes.listarCandidaturasDoVoluntario(voluntario));
                    break;
                case 5:
                    listar("ANIMAIS SOB MINHA RESPONSABILIDADE", voluntario.getAnimaisResponsaveis());
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void exibirMenuAdministrador(Usuario administrador) {
        int opcao;

        do {
            menu("CONTROLE DE ADOÇÕES",
                    "1 - Solicitações pendentes (aprovar / rejeitar)",
                    "2 - Histórico completo de adoções",
                    "3 - Registrar devolução de animal",
                    "4 - Candidaturas de cuidado pendentes",
                    "0 - Voltar");
            opcao = Entrada.lerInt("Opção");

            switch (opcao) {
                case 1:
                    analisarSolicitacao(administrador);
                    break;
                case 2:
                    exibirHistorico();
                    break;
                case 3:
                    registrarDevolucao(administrador);
                    break;
                case 4:
                    analisarCandidatura(administrador);
                    break;
                case 0:
                    break;
                default:
                    erro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void solicitarAdocao(Voluntario voluntario) {
        Animal animal = (Animal) escolher("ANIMAIS QUE ACEITAM SOLICITAÇÃO", animais.listarAdotaveis());

        if (animal == null) {
            return;
        }

        try {
            adocoes.solicitarAdocao(voluntario, animal);
            sucesso("Solicitação enviada. Aguarde a análise de um administrador.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void candidatar(Voluntario voluntario) {
        Animal animal = (Animal) escolher("ESCOLHA O ANIMAL", animais.listarTodos());

        if (animal == null) {
            return;
        }

        try {
            adocoes.candidatar(voluntario, animal);
            sucesso("Candidatura registrada.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void analisarSolicitacao(Usuario administrador) {
        try {
            List<Adocao> pendentes = adocoes.listarPendentes(administrador);
            Adocao adocao = (Adocao) escolher("SOLICITAÇÕES PENDENTES", pendentes);

            if (adocao == null) {
                return;
            }

            linha();
            adocao.exibirInfo();
            linha();
            int decisao = Entrada.lerInt("1 - Aprovar | 2 - Rejeitar | 0 - Cancelar");

            if (decisao == 1) {
                adocoes.aprovarAdocao(administrador, adocao);
                sucesso("Adoção concluída. Demais solicitações do animal foram rejeitadas.");
            } else if (decisao == 2) {
                adocoes.rejeitarAdocao(administrador, adocao);
                sucesso("Solicitação rejeitada.");
            }
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void exibirHistorico() {
        List<Adocao> historico = adocoes.historicoAdocao();
        titulo("HISTÓRICO DE ADOÇÕES");

        if (historico.isEmpty()) {
            aviso("Nenhuma adoção realizada.");
            return;
        }

        for (Adocao adocao : historico) {
            linha();
            adocao.exibirInfo();
        }
        linha();
    }

    private void registrarDevolucao(Usuario administrador) {
        try {
            Adocao adocao = (Adocao) escolher("ADOÇÕES ATIVAS", adocoes.listarAdocoesAtivas(administrador));

            if (adocao == null) {
                return;
            }

            adocoes.registrarDevolucao(administrador, adocao.getAnimal());
            sucesso("Devolução registrada. O registro da adoção foi mantido no histórico.");
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }

    private void analisarCandidatura(Usuario administrador) {
        try {
            Candidatura candidatura = (Candidatura) escolher("CANDIDATURAS PENDENTES",
                    adocoes.listarCandidaturasPendentes(administrador));

            if (candidatura == null) {
                return;
            }

            int decisao = Entrada.lerInt("1 - Aprovar | 2 - Rejeitar | 0 - Cancelar");

            if (decisao == 1) {
                adocoes.aprovarCandidatura(administrador, candidatura);
                sucesso("Candidatura aprovada. O animal ficou sob responsabilidade do voluntário.");
            } else if (decisao == 2) {
                adocoes.rejeitarCandidatura(administrador, candidatura);
                sucesso("Candidatura rejeitada.");
            }
        } catch (AbrigoException e) {
            tratarErro(e);
        }
    }
}
