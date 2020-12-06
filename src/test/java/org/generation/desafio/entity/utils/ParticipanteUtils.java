package org.generation.desafio.entity.utils;

import org.generation.desafio.entity.Participante;
import org.generation.desafio.entity.Turma;

/**
 * Classe de apoio para realização dos testes
 */
public class ParticipanteUtils {

    /**
     * Cria o objeto representativo da classe Participante
     * @param id identificacao da participante
     * @param email email do participante
     * @param nome nome para identificar o participante
     * @param observacoes apontamentos para esse participante
     * @param turma turma que ele está relacionado
     * @return objeto entity
     */
    public static Participante createModelObject(Long id, String email, String nome, String observacoes, Turma turma) {
        Participante model = new Participante();
        model.setEmail(email);
        model.setNome(nome);
        model.setObservacoes(observacoes);
        model.setTurma(turma);
        model.setId(id);
        return model;
    }

}