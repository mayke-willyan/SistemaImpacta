package br.com.sistemaimpacta.model;

import br.com.sistemaimpacta.exceptions.CalculoPontuacaoInvalidoException;
import br.com.sistemaimpacta.exceptions.QuantidadeHorasInvalidasException;

import java.time.LocalDateTime;
import java.util.List;

public class AcaoMultiraoReciclagem extends Acao{

    private int qtdHoras;

    public AcaoMultiraoReciclagem(String titulo, String descricao, LocalDateTime data, int maximoParticipantes,int qtdHoras)
    {
        super(titulo, descricao, data, maximoParticipantes);
        if(qtdHoras <=0){
            throw new QuantidadeHorasInvalidasException("Quantidade de horas não pode ser <=0");
        }

        else{
            this.qtdHoras = qtdHoras;
        }
    }

    @Override
    public int calcularPontuacao(){
        if(this.qtdHoras <= 0){
            throw new CalculoPontuacaoInvalidoException("Quantidade de Horas não pode ser menor ou igual a 0");
        }
        else{
            return 4 * (this.qtdHoras);
        }

    }
}
