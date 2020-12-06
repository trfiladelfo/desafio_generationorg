package org.generation.desafio.core.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.generation.desafio.controller.exception.DesafioException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 *
 */
public final class DesafioError {
    @JsonIgnore
    private final UUID uuid;
    private final String dominio;
    private final String mensagem;
    @JsonIgnore
    private final DesafioException causa;

    /**
     * @param dominio
     * @param mensagem
     * @param causa
     */
    private DesafioError(final String dominio, final String mensagem,
                        final DesafioException causa) {
        this.uuid = UUID.randomUUID();
        this.mensagem = mensagem;
        this.dominio = dominio;
        this.causa = causa;
    }

    /**
     * @param mensagem
     * @param causa
     */
    public static DesafioError of(String mensagem, DesafioException causa) {
        return DesafioError.of("dominio nao declarado", mensagem, causa);
    }

    public static DesafioError of(String dominio, String mensagem, DesafioException causa) {
        return new DesafioError(dominio, mensagem, causa);
    }

    /**
     * @return
     */
    public UUID getUuid() {
        return uuid;
    }

    public String getDominio() {
        return dominio;
    }

    /**
     * @return
     */
    public int getCodigo() {
        return causa != null ? causa.getCodigo(): HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public String getMotivo() {
        return causa != null ? causa.getMotivo(): "Motivo não declarado";
    }

    /**
     * @return
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @return
     */
    public DesafioException getCausa() {
        return causa;
    }
}
