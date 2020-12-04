package org.generation.desafio.repository;

import org.generation.desafio.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe representativa para o acesso aos dados da tabela Participante. Busque
 * no README.md deste pacote.
 */
@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}
