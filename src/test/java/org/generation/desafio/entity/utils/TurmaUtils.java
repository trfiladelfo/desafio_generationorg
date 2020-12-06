package org.generation.desafio.entity.utils;

import org.generation.desafio.entity.Turma;

/**
 * Classe de apoio para realização dos testes
 */
public class TurmaUtils {

    /**
     * Cria o objeto representativo da classe Turma
     * @param id identificacao da turma
     * @param descricao descricao de referencia para uma turma
     * @param tipo modalidade da turma
     * @return objeto entity
     */
    public static Turma createModelObject(Long id, String descricao, String tipo) {
        Turma model = new Turma();
        model.setDescricao(descricao);
        model.setTipo(tipo);
        model.setId(id);
        return model;
    }

}
