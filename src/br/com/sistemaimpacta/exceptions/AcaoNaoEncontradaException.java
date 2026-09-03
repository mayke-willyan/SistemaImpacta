package br.com.sistemaimpacta.exceptions;

public class AcaoNaoEncontradaException extends RuntimeException {
    public AcaoNaoEncontradaException(String message) {
        super(message);
    }
}
