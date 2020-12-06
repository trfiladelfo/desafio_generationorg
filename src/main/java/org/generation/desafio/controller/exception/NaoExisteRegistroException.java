package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe usada para designar erros do tipo registro não localizado
 */
public class NaoExisteRegistroException extends DesafioException {

    /**
     * Classe usada para designar erros do tipo registro não localizado
     */
    public NaoExisteRegistroException() {
        super(HttpStatus.NO_CONTENT, "Não existe registro cadastrado para essa pesquisa");
    }

    /**
     * Classe usada para designar erros do tipo registro não localizado
     * @param mensagem mensagem para ser exibida
     */
    public NaoExisteRegistroException(final String mensagem) {
        super(HttpStatus.NO_CONTENT, mensagem, null);
    }

    /**
     * Classe usada para designar erros do tipo registro não localizado
     * @param mensagem mensagem para ser exibida
     * @param error erro do sistema gerado
     */
    public NaoExisteRegistroException(final String mensagem, Throwable error) {
        super(HttpStatus.NO_CONTENT, mensagem, error);
    }

    /**
     * Classe usada para designar erros do tipo registro não localizado
     * @param error erro do sistema gerado
     */
    public NaoExisteRegistroException(Throwable error) {
        super(HttpStatus.NO_CONTENT, "Não existe registro cadastrado para essa pesquisa", error);
    }
}
