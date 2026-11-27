package service;

import exception.acesso.AcessoNegadoException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import model.Adocao;
import model.Animal;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import model.atividades.Inscricao;
import repository.AdocaoRepository;
import repository.AnimalRepository;
import repository.InscricaoRepository;
import util.Permissoes;

public class RelatorioService {
    private static final String MSG_SO_ADMIN = "[ APENAS ADMINISTRADORES PODEM ACESSAR OS RELATÓRIOS. ]";

    private final AnimalRepository animalRepository;
    private final AdocaoRepository adocaoRepository;
    private final InscricaoRepository inscricaoRepository;
    private final SaudeService saudeService;

    public RelatorioService(AnimalRepository animalRepository, AdocaoRepository adocaoRepository,
            InscricaoRepository inscricaoRepository, SaudeService saudeService) {
        this.animalRepository = animalRepository;
        this.adocaoRepository = adocaoRepository;
        this.inscricaoRepository = inscricaoRepository;
        this.saudeService = saudeService;
    }

    public int totalAnimaisResgatados(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        return animalRepository.listarAnimais().size();
    }

    public int totalAdocoesRealizadas(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        return adocoesConcluidas().size();
    }

    public List<String> especiesMaisResgatadas(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        List<String> especies = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Animal animal : animalRepository.listarAnimais()) {
            String especie = animal.getEspecie().trim().toLowerCase();
            int posicao = especies.indexOf(especie);

            if (posicao == -1) {
                especies.add(especie);
                quantidades.add(1);
            } else {
                quantidades.set(posicao, quantidades.get(posicao) + 1);
            }
        }

        // ordena da espécie mais resgatada para a menos resgatada
        for (int i = 0; i < especies.size(); i++) {
            for (int j = 0; j < especies.size() - 1 - i; j++) {
                if (quantidades.get(j) < quantidades.get(j + 1)) {
                    int quantidadeTemp = quantidades.get(j);
                    quantidades.set(j, quantidades.get(j + 1));
                    quantidades.set(j + 1, quantidadeTemp);

                    String especieTemp = especies.get(j);
                    especies.set(j, especies.get(j + 1));
                    especies.set(j + 1, especieTemp);
                }
            }
        }

        List<String> resultado = new ArrayList<>();
        for (int i = 0; i < especies.size(); i++) {
            resultado.add(especies.get(i) + ": " + quantidades.get(i));
        }
        return resultado;
    }

    public double taxaAdocaoMensal(Usuario usuario, YearMonth mes) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        LocalDate fimDoMes = mes.atEndOfMonth();

        int adocoesNoMes = 0;
        for (Adocao adocao : adocoesConcluidas()) {
            if (YearMonth.from(adocao.getDataAdocao()).equals(mes)) {
                adocoesNoMes++;
            }
        }

        int resgatadosAteOMes = 0;
        for (Animal animal : animalRepository.listarAnimais()) {
            if (!animal.getDataResgate().isAfter(fimDoMes)) {
                resgatadosAteOMes++;
            }
        }

        return resgatadosAteOMes == 0 ? 0.0 : (adocoesNoMes * 100.0) / resgatadosAteOMes;
    }

    public int voluntariosAtivos(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministrador(usuario, MSG_SO_ADMIN);
        List<Voluntario> ativos = new ArrayList<>();

        for (Inscricao inscricao : inscricaoRepository.listarInscricoes()) {
            if (!ativos.contains(inscricao.getVoluntario())) {
                ativos.add(inscricao.getVoluntario());
            }
        }
        return ativos.size();
    }

    public List<Animal> animaisEmTratamento(Usuario usuario) throws AcessoNegadoException {
        Permissoes.exigirAdministradorOuVeterinario(usuario,
                "[ APENAS ADMINISTRADORES E VETERINÁRIOS PODEM VER ANIMAIS EM TRATAMENTO. ]");
        return saudeService.listarAnimaisEmTratamento();
    }

    private List<Adocao> adocoesConcluidas() {
        List<Adocao> concluidas = new ArrayList<>();

        for (Adocao adocao : adocaoRepository.listarSolicitacoes()) {
            if (adocao.isConcluida()) {
                concluidas.add(adocao);
            }
        }
        return concluidas;
    }
}
 
