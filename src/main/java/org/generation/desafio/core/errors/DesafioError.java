package org.generation.desafio.core.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.generation.desafio.controller.exception.DesafioException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Classe de respostas dos erros ocorridos no sistema
 */
public final class DesafioError {
    @JsonIgnore
    private final UUID uuid;
    private final String dominio;
    private final String mensagem;
    @JsonIgnore
    private final DesafioException causa;

    /**
     * Classe de respostas dos erros ocorridos no sistema
     * @param dominio endereço do recurso que ocorreu o erro
     * @param mensagem mensagem a ser apresentada
     * @param causa erro do sistema
     */
    private DesafioError(final String dominio, final String mensagem,
                        final DesafioException causa) {
        this.uuid = UUID.randomUUID();
        this.mensagem = mensagem;
        this.dominio = dominio;
        this.causa = causa;
    }

    /**
     * Classe de respostas dos erros ocorridos no sistema
     * @param mensagem mensagem a ser apresentada
     * @param causa erro do sistema
     */
    public static DesafioError of(String mensagem, DesafioException causa) {
        return DesafioError.of("dominio nao declarado", mensagem, causa);
    }

    /**
     * Método para geração de uma instancia da classe
     * @param dominio endereço do recurso que ocorreu o erro
     * @param mensagem mensagem a ser apresentada
     * @param causa erro do sistema
     * @return instancia da classe
     */
    public static DesafioError of(String dominio, String mensagem, DesafioException causa) {
        return new DesafioError(dominio, mensagem, causa);
    }

    /**
     * Retorna um código único identificador do erro
     * @return codigo identificado do erro
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Retorna o endereço do recurso que ocorreu o erro
     * @return endereço relativo do recurso
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Retorna o código representativo do erro gerado
     * @return codigo html
     */
    public int getCodigo() {
        return causa != null ? causa.getCodigo(): HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    /**
     * Retorna um motivo deste erro
     * @return motivo do erro
     */
    public String getMotivo() {
        return causa != null ? causa.getMotivo(): "Motivo não declarado";
    }

    /**
     * Retorna a descrição detalhada do que foi o erro gerado
     * @return descrição do erro
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Retorna o objeto erro gerado no sistema
     * @return erro do sistema
     */
    public DesafioException getCausa() {
        return causa;
    }
}
