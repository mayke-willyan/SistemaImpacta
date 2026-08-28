package br.com.sistemaimpacta.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Acao {
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private int maximoParticipantes;

    private List<Voluntario> voluntariosInscritos;

    public Acao(String titulo, String descricao, LocalDateTime data, int maximoParticipantes, List<Voluntario> voluntariosInscritos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maximoParticipantes = maximoParticipantes;
        this.voluntariosInscritos = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public int getMaximoParticipantes() {
        return maximoParticipantes;
    }

    public List<Voluntario> getVoluntariosInscritos() {
        return voluntariosInscritos;
    }
}
