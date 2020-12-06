package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

public class NaoExisteRegistroException extends DesafioException {

    public NaoExisteRegistroException() {
        super(HttpStatus.NO_CONTENT, "Não existe registro cadastrado para essa pesquisa");
    }

    public NaoExisteRegistroException(final String mensagem) {
        super(HttpStatus.NO_CONTENT, mensagem, null);
    }

    public NaoExisteRegistroException(final String mensagem, Throwable error) {
        super(HttpStatus.NO_CONTENT, mensagem, error);
    }

    public NaoExisteRegistroException(Throwable error) {
        super(HttpStatus.NO_CONTENT, "Não existe registro cadastrado para essa pesquisa", error);
    }
}
