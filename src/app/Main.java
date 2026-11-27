package app;
import java.util.NoSuchElementException;

import controller.AdocaoController;
import controller.AnimalController;
import controller.EventosController;
import controller.InscricoesController;
import controller.NotificacaoController;
import controller.RelatorioController;
import controller.SaudeController;
import controller.UsuarioController;
import repository.AdocaoRepository;
import repository.AnimalRepository;
import repository.CandidaturaRepository;
import repository.EventosRepository;
import repository.HistoricoAnimalRepository;
import repository.InscricaoRepository;
import repository.UsuarioRepository;
import service.AdocaoService;
import service.AnimalService;
import service.CandidaturaService;
import service.EventoService;
import service.InscricaoService;
import service.NotificacaoService;
import service.RelatorioService;
import service.SaudeService;
import service.UsuarioService;
import view.adocao.TelaAdocao;
import view.animais.TelaAnimais;
import view.atividades.TelaCadastroEvento;
import view.atividades.TelaInscricao;
import view.notificacao.TelaNotificacoes;
import view.principal.MenuAdministrador;
import view.principal.MenuPrincipal;
import view.principal.MenuVeterinario;
import view.principal.MenuVoluntario;
import view.relatorios.TelaRelatorios;
import view.saude.TelaCadastroRegistro;
import view.usuarios.TelaUsuarios;

public class Main {
    public static void main(String[] args){
         try {

            UsuarioRepository usuarioRepository = new UsuarioRepository();
            AnimalRepository animalRepository = new AnimalRepository();
            AdocaoRepository adocaoRepository = new AdocaoRepository();
            CandidaturaRepository candidaturaRepository = new CandidaturaRepository();
            EventosRepository eventosRepository = new EventosRepository();
            InscricaoRepository inscricaoRepository = new InscricaoRepository();
            HistoricoAnimalRepository historicoRepository = new HistoricoAnimalRepository();

          
            SaudeService saudeService = new SaudeService(historicoRepository);
            NotificacaoService notificacaoService = new NotificacaoService(saudeService);
            UsuarioService usuarioService = new UsuarioService(usuarioRepository);
            AnimalService animalService = new AnimalService(animalRepository, adocaoRepository,
                    saudeService, notificacaoService);
            AdocaoService adocaoService = new AdocaoService(adocaoRepository, saudeService, notificacaoService);
            CandidaturaService candidaturaService = new CandidaturaService(candidaturaRepository);
            EventoService eventoService = new EventoService(eventosRepository, inscricaoRepository,
                    notificacaoService);
            InscricaoService inscricaoService = new InscricaoService(inscricaoRepository, eventosRepository);
            RelatorioService relatorioService = new RelatorioService(animalRepository, adocaoRepository,
                    inscricaoRepository, saudeService);


            UsuarioController usuarios = new UsuarioController(usuarioService);
            AnimalController animais = new AnimalController(animalService);
            AdocaoController adocoes = new AdocaoController(adocaoService, candidaturaService);
            EventosController eventos = new EventosController(eventoService);
            InscricoesController inscricoes = new InscricoesController(inscricaoService);
            SaudeController saude = new SaudeController(saudeService);
            NotificacaoController notificacoes = new NotificacaoController(notificacaoService, eventoService);
            RelatorioController relatorios = new RelatorioController(relatorioService);
    
            TelaAnimais telaAnimais = new TelaAnimais(animais);
            TelaAdocao telaAdocao = new TelaAdocao(adocoes, animais);
            TelaCadastroEvento telaEventos = new TelaCadastroEvento(eventos, inscricoes);
            TelaInscricao telaInscricao = new TelaInscricao(inscricoes, eventos);
            TelaNotificacoes telaNotificacoes = new TelaNotificacoes(notificacoes);
            TelaRelatorios telaRelatorios = new TelaRelatorios(relatorios);
            TelaUsuarios telaUsuarios = new TelaUsuarios(usuarios);
            TelaCadastroRegistro telaSaude = new TelaCadastroRegistro(animais, saude, relatorios);

            MenuAdministrador menuAdministrador = new MenuAdministrador(telaAnimais, telaAdocao, telaEventos,
                    telaNotificacoes, telaRelatorios, telaUsuarios, telaSaude);
            MenuVeterinario menuVeterinario = new MenuVeterinario(telaAnimais, telaSaude, telaNotificacoes);
            MenuVoluntario menuVoluntario = new MenuVoluntario(telaAnimais, telaAdocao, telaInscricao,
                    telaNotificacoes);

            new MenuPrincipal(usuarios, telaAnimais, menuVoluntario, menuVeterinario, menuAdministrador)
                    .exibirMenuPrincipal();
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Entrada encerrada. Programa finalizado.");
        }
    }
}
