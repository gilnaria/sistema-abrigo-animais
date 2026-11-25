package service;
 
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
 
import exception.PermissaoInvalidaException;
import model.Adocao;
import model.Animal;
import model.acesso.Administrador;
import model.acesso.Usuario;
import model.acesso.Voluntario;
import model.atividades.Inscricao;
import model.saude.HistoricoAnimal;
import model.saude.RegistroMedico;
import model.saude.StatusTratamento;
import model.saude.Tratamento;
import repository.InscricaoRepository;
 
public class RelatorioService {
    private final AnimalService animalService;
    private final AdocaoService adocaoService;
    private final UsuarioService usuarioService;
    private final SaudeService saudeService;
    private final InscricaoRepository inscricaoRepository;
 
    public RelatorioService(AnimalService animalService, AdocaoService adocaoService, UsuarioService usuarioService,
            SaudeService saudeService, InscricaoRepository inscricaoRepository) {
        this.animalService = animalService;
        this.adocaoService = adocaoService;
        this.usuarioService = usuarioService;
        this.saudeService = saudeService;
        this.inscricaoRepository = inscricaoRepository;
    }
 
    // Quantidade de animais resgatados (todos os animais cadastrados no abrigo)
    public int quantidadeAnimaisResgatados(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        return animalService.listarAnimais().size();
    }
 
    // Quantidade de adoções realizadas (solicitações aprovadas, inclusive as que depois foram devolvidas)
    public int quantidadeAdocoesRealizadas(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        return adocaoService.historicoAdocao().size();
    }
 
    // Espécies mais resgatadas, da mais frequente para a menos frequente
    public Map<String, Integer> especiesMaisResgatadas(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        Map<String, Integer> contagem = new HashMap<>();
        Map<String, String> nomeOriginal = new HashMap<>();
 
        for (Animal animal : animalService.listarAnimais()) {
            String chave = animal.getEspecie().trim().toLowerCase();
 
            nomeOriginal.putIfAbsent(chave, animal.getEspecie().trim());
            contagem.merge(chave, 1, Integer::sum);
        }
 
        List<String> chaves = new ArrayList<>(contagem.keySet());
 
        chaves.sort((a, b) -> {
            int porQuantidade = Integer.compare(contagem.get(b), contagem.get(a));
 
            if (porQuantidade != 0) {
                return porQuantidade;
            }
 
            return a.compareTo(b);
        });
 
        Map<String, Integer> ranking = new LinkedHashMap<>();
 
        for (String chave : chaves) {
            ranking.put(nomeOriginal.get(chave), contagem.get(chave));
        }
 
        return ranking;
    }
 
    // Taxa de adoção mensal (%) = adoções aprovadas no mês / animais resgatados até o fim do mês * 100
    public Map<YearMonth, Double> taxaAdocaoMensal(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        Map<YearMonth, Double> taxas = new TreeMap<>();
        List<Animal> animais = animalService.listarAnimais();
 
        if (animais.isEmpty()) {
            return taxas;
        }
 
        YearMonth primeiroMes = YearMonth.from(animais.get(0).getDataResgate());
 
        for (Animal animal : animais) {
            YearMonth mesResgate = YearMonth.from(animal.getDataResgate());
 
            if (mesResgate.isBefore(primeiroMes)) {
                primeiroMes = mesResgate;
            }
        }
 
        List<Adocao> adocoes = adocaoService.historicoAdocao();
        YearMonth mesAtual = YearMonth.now();
 
        for (YearMonth mes = primeiroMes; !mes.isAfter(mesAtual); mes = mes.plusMonths(1)) {
            int adocoesNoMes = 0;
            int resgatadosAteMes = 0;
 
            for (Adocao adocao : adocoes) {
                LocalDate data = adocao.getDataAdocao();
 
                if (data != null && YearMonth.from(data).equals(mes)) {
                    adocoesNoMes++;
                }
            }
 
            for (Animal animal : animais) {
                if (!YearMonth.from(animal.getDataResgate()).isAfter(mes)) {
                    resgatadosAteMes++;
                }
            }
 
            double taxa = resgatadosAteMes == 0 ? 0.0 : (adocoesNoMes * 100.0) / resgatadosAteMes;
 
            taxas.put(mes, taxa);
        }
 
        return taxas;
    }
 
    // Voluntário ativo = tem animal sob sua responsabilidade OU está inscrito em algum evento
    public int quantidadeVoluntariosAtivos(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        int ativos = 0;
 
        for (Usuario cadastrado : usuarioService.listarUsuario()) {
            if (cadastrado instanceof Voluntario) {
                Voluntario voluntario = (Voluntario) cadastrado;
 
                if (estaAtivo(voluntario)) {
                    ativos++;
                }
            }
        }
 
        return ativos;
    }
 
    // Animais em tratamento = tratamentos cujo status não é CONCLUIDO
    public List<Tratamento> tratamentosEmAndamento(Usuario usuario) throws PermissaoInvalidaException {
        exigirAdministrador(usuario);
 
        List<Tratamento> emTratamento = new ArrayList<>();
 
        for (HistoricoAnimal historico : saudeService.listarHistoricos()) {
            for (RegistroMedico registro : historico.getRegistros()) {
                if (registro instanceof Tratamento) {
                    Tratamento tratamento = (Tratamento) registro;
 
                    if (tratamento.getStatusTratamento() != StatusTratamento.CONCLUIDO) {
                        emTratamento.add(tratamento);
                    }
                }
            }
        }
 
        return emTratamento;
    }
 
    private boolean estaAtivo(Voluntario voluntario) {
        if (!voluntario.listarMeusAnimais().isEmpty()) {
            return true;
        }
 
        for (Inscricao inscricao : inscricaoRepository.listarInscricoes()) {
            if (inscricao.getVoluntario().equals(voluntario)) {
                return true;
            }
        }
 
        return false;
    }
 
    private void exigirAdministrador(Usuario usuario) throws PermissaoInvalidaException {
        if (!(usuario instanceof Administrador)) {
            throw new PermissaoInvalidaException("Permissão negada! (somente administradores)");
        }
    }
}
 
