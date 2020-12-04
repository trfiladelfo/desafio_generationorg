package org.generation.desafio.entity;

import org.generation.desafio.entity.Turma;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe para realização dos testes unitários para a entidade Turma
 */
public class TurmaUnitTest {

    /**
     * Método com a finalidade de testar a integridade dos dados armazenados na
     * entidade Turma
     */
    @Test
    public void testarDescricao() {
        String descricao = "Turma 2021 - EAD: Ciclo 1";

        Turma turma = new Turma();
        turma.setDescricao(descricao);

        assertEquals(turma.getDescricao(), descricao);
    }
}
