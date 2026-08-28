package br.com.sistemaimpacta.controller;

import br.com.sistemaimpacta.exceptions.CadastroEmailDuplicadoException;
import br.com.sistemaimpacta.model.Voluntario;

import java.util.HashMap;

public class Impacta {
    private HashMap<String,Voluntario> voluntarios;

    public Impacta() {
        this.voluntarios = new HashMap<>();
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

        return "Voluntario não encontrado";
    }


}
