package br.com.sistemaimpacta.model;

import java.time.LocalDateTime;
import java.util.List;

public class AcaoMultiraoReciclagem extends Acao{

    private int qtdHoras;

    public AcaoMultiraoReciclagem(String titulo, String descricao, LocalDateTime data, int maximoParticipantes, List<Voluntario> voluntariosInscritos, int qtdHoras) {
        super(titulo, descricao, data, maximoParticipantes, voluntariosInscritos);
        this.qtdHoras = qtdHoras;
    }

    @Override
    public int calcularPontuacao(){
        return 4 * (this.qtdHoras);
    }
}
