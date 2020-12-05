package org.generation.desafio.entity.utils;

import org.generation.desafio.entity.Turma;

public class TurmaUtils {

    public static Turma createModelObject(Long id, String descricao, String tipo) {
        Turma model = new Turma();
        model.setDescricao(descricao);
        model.setTipo(tipo);
        model.setId(id);
        return model;
    }

}
