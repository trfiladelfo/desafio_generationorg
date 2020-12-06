package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe usada para designar erros do tipo parametros invalidos
 */
public class ArgumentoIlegalException extends DesafioException {

    /**
     * Classe usada para designar erros do tipo parametros invalidos
     * @param mensagem mensagem para ser exibida
     */
    public ArgumentoIlegalException(final String mensagem) {
        super(HttpStatus.BAD_REQUEST, mensagem, new IllegalArgumentException());
    }

    /**
     * Classe usada para designar erros do tipo parametros invalidos
     * @param mensagem mensagem para ser exibida
     * @param error erro do sistema gerado
     */
    public ArgumentoIlegalException(final String mensagem, Throwable error) {
        super(HttpStatus.BAD_REQUEST, mensagem, error);
    }

    /**
     * Classe usada para designar erros do tipo parametros invalidos
     */
    public ArgumentoIlegalException() {
        super(HttpStatus.BAD_REQUEST, "Você utilizou de um ou mais argumentos não compativel", new IllegalArgumentException());
    }
}
