package org.generation.desafio.repository;

import org.generation.desafio.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe representativa para o acesso aos dados da tabela Turma. Busque no
 * README.md deste pacote.
 */
@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
