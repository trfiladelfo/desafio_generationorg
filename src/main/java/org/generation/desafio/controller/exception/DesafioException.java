package org.generation.desafio.controller.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class DesafioException extends RuntimeException {

    private Date dataHora = new Date();

    private int codigo;
    private String motivo;

    public DesafioException() {
        this(HttpStatus.BAD_REQUEST, null, null);
    }

    public DesafioException(String mensagem) {
        this(HttpStatus.BAD_REQUEST, mensagem, null);
    }

    public DesafioException(HttpStatus httpStatus, String mensagem) {
        this(httpStatus, mensagem, null);
    }

    public DesafioException(String mensagem, Throwable causa) {
        this(HttpStatus.BAD_REQUEST, mensagem, causa);
    }

    public DesafioException(HttpStatus httpStatus, String mensagem, Throwable causa) {
        super(mensagem, causa);

        this.codigo = httpStatus.value();
        this.motivo = httpStatus.getReasonPhrase();
    }

    public Date getDataHora() {
        return dataHora;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMotivo() {
        return motivo;
    }
}
