package br.com.sistemaimpacta.controller;

import br.com.sistemaimpacta.exceptions.AcaoLotadaException;
import br.com.sistemaimpacta.exceptions.CadastroEmailDuplicadoException;
import br.com.sistemaimpacta.exceptions.DadosNaoEncontradosCadastroException;
import br.com.sistemaimpacta.model.*;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Impacta {
    private HashMap<String,Voluntario> voluntarios;
    private HashMap<Integer, Acao> acoes;
    private int idAcao;


    public Impacta() {
        this.voluntarios = new HashMap<>();
        this.acoes = new HashMap<>();
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

        this.idAcao ++;

        return idGeradoAcao;

    }

    public int cadastrarMultirao(String titulo, String descricao, String data, int maximoParticipantes, int duracaoHoras){

        int idGeradoAcao = this.idAcao;
        LocalDateTime dataFormatada = LocalDateTime.parse(data);

        AcaoMultiraoReciclagem novaAcaoMultirao = new AcaoMultiraoReciclagem(titulo, descricao,dataFormatada,maximoParticipantes,duracaoHoras);

        acoes.put(idGeradoAcao,novaAcaoMultirao);

        this.idAcao ++;

        return idGeradoAcao;
    }

    public int cadastrarOficina(String titulo, String descricao, String data, int maximoParticipantes, int duracaoHoras, boolean kitMaterial){

        int idGeradoAcao = this.idAcao;
        LocalDateTime dataFormatada = LocalDateTime.parse(data);

        AcaoOficinaEcologica novaAcaoOficina = new AcaoOficinaEcologica(titulo, descricao,dataFormatada,maximoParticipantes,duracaoHoras, kitMaterial);

        acoes.put(idGeradoAcao,novaAcaoOficina);

        this.idAcao ++;

        return idGeradoAcao;
    }

    public boolean inscreverVoluntario(String emailVoluntario, int idAcao){
        Acao acao = acoes.get(idAcao);

        if(!voluntarios.containsKey(emailVoluntario) || !acoes.containsKey(idAcao)){
            throw new DadosNaoEncontradosCadastroException("Os dados informados para cadastro não foram encontrados");
        }

        if(acao.acaoLotada()){
            throw new AcaoLotadaException("Ação esta lotada e não aceita mais inscrições de participantes");
        }
        else{
            return true;
        }



    }

}
