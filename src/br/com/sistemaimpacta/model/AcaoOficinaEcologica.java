package br.com.sistemaimpacta.model;

import br.com.sistemaimpacta.exceptions.CalculoPontuacaoInvalidoException;
import br.com.sistemaimpacta.exceptions.QuantidadeHorasInvalidasException;

import java.time.LocalDateTime;
import java.util.List;

public class AcaoOficinaEcologica extends Acao{
    private int qtdHoras;
    private boolean kitMaterialEducativo;

    public AcaoOficinaEcologica(String titulo, String descricao, LocalDateTime data, int maximoParticipantes,int qtdHoras, boolean kitMaterialEducativo) {

        super(titulo, descricao, data, maximoParticipantes);

        if(qtdHoras <=0){
            throw new QuantidadeHorasInvalidasException("Quantidade de horas não pode ser <=0");
        }
        else{
            this.qtdHoras = qtdHoras;
        }

        this.kitMaterialEducativo = kitMaterialEducativo;
    }

    @Override
    public int calcularPontuacao(){

        if(this.qtdHoras <= 0){
            throw new CalculoPontuacaoInvalidoException("Quantidade de Horas não pode ser menor ou igual a 0");
        }
        else{
            int pontos = this.qtdHoras * 3;

            if(this.kitMaterialEducativo){
                pontos += 10;
            }

            return pontos;
        }

    }
}
