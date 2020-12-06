package org.generation.desafio.core;


import org.generation.desafio.core.errors.DesafioError;
import org.generation.desafio.controller.exception.DesafioException;
import org.generation.desafio.controller.exception.NaoExisteRegistroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe controlado da geração do retorno de erro de todo o sistema
 */
@RestControllerAdvice
public class DesafioExceptionHandler {

    /**
     * Classe de tratamento dos erros do tipo de quando não existe registro cadastrado
     * @param request objeto de requisição
     * @param ex erro gerado pelo sistema
     * @return objeto de retorno
     */
    @ExceptionHandler(NaoExisteRegistroException.class)
    public ResponseEntity<DesafioError> handleNaoExisteRegistro(HttpServletRequest request, NaoExisteRegistroException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getCodigo());
        return new ResponseEntity<>(DesafioError.of(request.getRequestURI(), ex.getMessage(), ex), status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Forma generica de tratamento dos erros
     * @param request objeto de requisição
     * @param ex erro gerado pelo sistema
     * @return objeto de retorno
     */
    @ExceptionHandler(DesafioException.class)
    public ResponseEntity<DesafioError> handleGenerico(HttpServletRequest request, DesafioException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getCodigo());
        return new ResponseEntity<>(DesafioError.of(request.getRequestURI(), ex.getMessage(), ex), status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
