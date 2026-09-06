package br.com.sistemaimpacta.exceptions;

public class CadastroEmailDuplicadoException extends RuntimeException {
    public CadastroEmailDuplicadoException(String message) {
        super(message);
    }
}
