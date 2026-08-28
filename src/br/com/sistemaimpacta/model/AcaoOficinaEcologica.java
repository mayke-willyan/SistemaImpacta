package br.com.sistemaimpacta.model;

import java.time.LocalDateTime;
import java.util.List;

public class AcaoOficinaEcologica extends Acao{
    private int qtdHoras;
    private boolean kitMaterialEducativo;

    public AcaoOficinaEcologica(String titulo, String descricao, LocalDateTime data, int maximoParticipantes, List<Voluntario> voluntariosInscritos, int qtdHoras, boolean kitMaterialEducativo) {
        super(titulo, descricao, data, maximoParticipantes, voluntariosInscritos);
        this.qtdHoras = qtdHoras;
        this.kitMaterialEducativo = kitMaterialEducativo;
    }

    @Override
    public int calcularPontuacao(){
        int pontos = this.qtdHoras * 3;

        if(this.kitMaterialEducativo){
            pontos += 10;
        }

        return pontos;
    }
}
