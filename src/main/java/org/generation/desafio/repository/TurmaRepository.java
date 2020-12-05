package org.generation.desafio.repository;

import org.generation.desafio.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe representativa para o acesso aos dados da tabela Turma. Busque no
 * README.md deste pacote.
 */
@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    /**
     * Método que retorna as turmas com base no nome, a pesquisa é realizada em
     * quaisquer posição da coluna descrição da tabela Turma
     */
    //@Query("SELECT * FROM turma t WHERE t.descricao LIKE %?1%")
    //public List<Turma> getByNameTurma(String name);

}
