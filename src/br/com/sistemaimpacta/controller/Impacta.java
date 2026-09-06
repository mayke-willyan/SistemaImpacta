package br.com.sistemaimpacta.controller;

import br.com.sistemaimpacta.exceptions.*;
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

        throw new VoluntarioNaoEncontradoException("Voluntario não encontrado");
    }

    public String[] listarVoluntarios(){

        List<Voluntario> voluntariosUnsorted = new ArrayList<>(voluntarios.values());

        for (int i = 0; i < voluntariosUnsorted.size() - 1 ; i++) {
            for (int j = 0; j < voluntariosUnsorted.size() - 1 - i ; j++) {

                Voluntario voluntario1 = voluntariosUnsorted.get(j);
                Voluntario voluntario2 = voluntariosUnsorted.get(j+1);

                boolean precisaTrocar = false;

                //comparação pontuação
                if(voluntario1.getPontuacaoImpacto() < voluntario2.getPontuacaoImpacto()){
                    precisaTrocar = true;
                }

                //desempate por nome
                if(voluntario1.getPontuacaoImpacto() == voluntario2.getPontuacaoImpacto()){
                    if(voluntario1.getNome().compareToIgnoreCase(voluntario2.getNome()) > 0){
                        precisaTrocar = true;
                    }
                }

                if(precisaTrocar){
                    voluntariosUnsorted.set(j,voluntario2);
                    voluntariosUnsorted.set(j+1,voluntario1);
                }
            }

        }

        //lista sorted 
        String[] voluntariosSorted = new String[voluntariosUnsorted.size()];
        for (int i = 0; i < voluntariosUnsorted.size(); i++) {
            Voluntario v = voluntariosUnsorted.get(i);
            voluntariosSorted[i] = String.format("Nome: %s | Email: %s | Ações: %d | Pontos: %d",
                    v.getNome(), v.getEmail(), v.getQuantidadeAcoes(), v.getPontuacaoImpacto());
        }

        return voluntariosSorted;
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
        voluntario.adicionarAcao(acao);

        return true;
    }

    public String exibirDetalhesAcao(int idAcao){

        if(!acoes.containsKey(idAcao)){
            throw new AcaoNaoEncontradaException("Ação com o id informado não foi encontrada");
        }

        Acao acao = acoes.get(idAcao);
        List<Voluntario> inscritos = inscricoesAcao.get(idAcao);

        String mensagem = "Título: " + acao.getTitulo() + "\n"
                + "Descrição: " + acao.getDescricao() + "\n"
                + "Data: " + acao.getData() + "\n"
                + "Máximo Participantes: " + acao.getMaximoParticipantes() + "\n"
                + "Pontuação Calculada: " + acao.calcularPontuacao() + "\n"
                + "Voluntários Inscritos:\n";

        if(inscritos.isEmpty()){

            mensagem += "- Nenhum voluntário inscrito.";

        }
        else{

            for(Voluntario v : inscritos){
                mensagem += " - " + v.getNome() + " (" + v.getEmail() + ")\n";
            }

        }

        return mensagem;
    }

}
