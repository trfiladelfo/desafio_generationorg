package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

public class ArgumentoIlegalException extends DesafioException {

    public ArgumentoIlegalException(final String mensagem) {
        super(HttpStatus.BAD_REQUEST, mensagem, new IllegalArgumentException());
    }

    public ArgumentoIlegalException(final String mensagem, Throwable error) {
        super(HttpStatus.BAD_REQUEST, mensagem, error);
    }

    public ArgumentoIlegalException() {
        super(HttpStatus.BAD_REQUEST, "Você utilizou de um ou mais argumentos não compativel", new IllegalArgumentException());
    }
}
