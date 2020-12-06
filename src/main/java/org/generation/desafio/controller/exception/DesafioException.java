package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Classe generica usada para designar erros
 */
public class DesafioException extends RuntimeException {

    private Date dataHora = new Date();
    private int codigo;
    private String motivo;

    /**
     * Classe generica usada para designar erros
     */
    public DesafioException() {
        this(HttpStatus.BAD_REQUEST, null, null);
    }

    /**
     * Classe generica usada para designar erros
     * @param mensagem mensagem para ser exibida
     */
    public DesafioException(String mensagem) {
        this(HttpStatus.BAD_REQUEST, mensagem, null);
    }

    /**
     * Classe generica usada para designar erros
     * @param httpStatus codigo do status html correspondente ao retorno
     * @param mensagem mensagem para ser exibida
     */
    public DesafioException(HttpStatus httpStatus, String mensagem) {
        this(httpStatus, mensagem, null);
    }

    public DesafioException(String mensagem, Throwable error) {
        this(HttpStatus.BAD_REQUEST, mensagem, error);
    }

    /**
     * Classe generica usada para designar erros
     * @param httpStatus codigo do status html correspondente ao retorno
     * @param mensagem mensagem para ser exibida
     * @param error erro do sistema gerado
     */
    public DesafioException(HttpStatus httpStatus, String mensagem, Throwable error) {
        super(mensagem, error);

        this.codigo = httpStatus.value();
        this.motivo = httpStatus.getReasonPhrase();
    }

    /**
     * Retorna a data de quando foi gerado o erro
     * @return data erro
     */
    public Date getDataHora() {
        return dataHora;
    }

    /**
     * Retorna codigo html correspondente ao retorno
     * @return codigo do erro
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna a causa do erro ocorrido
     * @return motivo do erro
     */
    public String getMotivo() {
        return motivo;
    }
}
