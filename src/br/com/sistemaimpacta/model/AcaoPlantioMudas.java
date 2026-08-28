package br.com.sistemaimpacta.model;

import java.time.LocalDateTime;
import java.util.List;

public class AcaoPlantioMudas extends Acao{
    private int qtdMudas;

    public AcaoPlantioMudas(String titulo, String descricao, LocalDateTime data, int maximoParticipantes, List<Voluntario> voluntariosInscritos, int qtdMudas) {
        super(titulo, descricao, data, maximoParticipantes, voluntariosInscritos);
        this.qtdMudas = qtdMudas;
    }


    @Override
    public int calcularPontuacao(){
        return 5 + (this.qtdMudas);
    }
}
