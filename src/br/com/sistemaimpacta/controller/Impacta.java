package br.com.sistemaimpacta.controller;

import br.com.sistemaimpacta.exceptions.AcaoLotadaException;
import br.com.sistemaimpacta.exceptions.CadastroEmailDuplicadoException;
import br.com.sistemaimpacta.exceptions.DadosNaoEncontradosCadastroException;
import br.com.sistemaimpacta.exceptions.VoluntarioJaInscritoException;
import br.com.sistemaimpacta.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Impacta {

    private int idAcao;
    private HashMap<String,Voluntario> voluntarios;
    private HashMap<Integer, Acao> acoes;
    private HashMap<Integer, List<Voluntario>> inscricoesAcao;

    public Impacta() {
        this.voluntarios = new HashMap<>();
        this.acoes = new HashMap<>();
        this.inscricoesAcao = new HashMap<>();
        this.idAcao = 1;
    }

    //Metodos Voluntarios
    public boolean cadastrarVoluntario(String nome, String email, String matricula){

        if(!voluntarios.containsKey(email)) {

            Voluntario voluntarioCriado = new Voluntario(nome, email, matricula);
            voluntarios.put(email, voluntarioCriado);
            return true;
        }

        else{
            throw new CadastroEmailDuplicadoException("Email ja esta sendo utilizado");
        }
    }

    public String exibirVoluntario(String email){

        Voluntario voluntario = voluntarios.get(email);
        if(voluntarios.containsKey(email)){
            return String.format("Nome: %s | Ações: %d | Pontuação: %d", voluntario.getNome(), voluntario.getQuantidadeAcoes(), voluntario.getPontuacaoImpacto());
        }

        return "Voluntario não encontrado";
    }

    //Metodos Ações

    //Plantio

    public int cadastrarPlantio(String titulo, String descricao, String data, int maximoParticipantes,int quantidadeMudas){

        int idGeradoAcao = this.idAcao;
        LocalDateTime dataFormatada = LocalDateTime.parse(data);

        AcaoPlantioMudas novaAcaoPlantio = new AcaoPlantioMudas(titulo,descricao,dataFormatada, maximoParticipantes, quantidadeMudas);

        acoes.put(idGeradoAcao,novaAcaoPlantio);
        inscricoesAcao.put(idGeradoAcao, new ArrayList<>());

        this.idAcao ++;

        return idGeradoAcao;

    }

    public int cadastrarMultirao(String titulo, String descricao, String data, int maximoParticipantes, int duracaoHoras){

        int idGeradoAcao = this.idAcao;
        LocalDateTime dataFormatada = LocalDateTime.parse(data);

        AcaoMultiraoReciclagem novaAcaoMultirao = new AcaoMultiraoReciclagem(titulo, descricao,dataFormatada,maximoParticipantes,duracaoHoras);

        acoes.put(idGeradoAcao,novaAcaoMultirao);
        inscricoesAcao.put(idGeradoAcao, new ArrayList<>());

        this.idAcao ++;

        return idGeradoAcao;
    }

    public int cadastrarOficina(String titulo, String descricao, String data, int maximoParticipantes, int duracaoHoras, boolean kitMaterial){

        int idGeradoAcao = this.idAcao;
        LocalDateTime dataFormatada = LocalDateTime.parse(data);

        AcaoOficinaEcologica novaAcaoOficina = new AcaoOficinaEcologica(titulo, descricao,dataFormatada,maximoParticipantes,duracaoHoras, kitMaterial);

        acoes.put(idGeradoAcao,novaAcaoOficina);
        inscricoesAcao.put(idGeradoAcao, new ArrayList<>());

        this.idAcao ++;

        return idGeradoAcao;
    }

    public boolean inscreverVoluntario(String emailVoluntario, int idAcao){

        if(!voluntarios.containsKey(emailVoluntario) || !acoes.containsKey(idAcao)){
            throw new DadosNaoEncontradosCadastroException("Os dados informados para cadastro não foram encontrados");
        }

        Acao acao = acoes.get(idAcao);
        Voluntario voluntario = voluntarios.get(emailVoluntario);

        List<Voluntario> listaInscritos = inscricoesAcao.get(idAcao);

        if(listaInscritos.contains(voluntario)){
            throw new VoluntarioJaInscritoException("Voluntario ja inscrito");
        }

        if(listaInscritos.size() >= acao.getMaximoParticipantes()){
            throw new AcaoLotadaException("Ação ja lotada");
        }

        listaInscritos.add(voluntario);

        return true;
    }

}
