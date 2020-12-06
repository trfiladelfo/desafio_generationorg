package org.generation.desafio.repository;

import org.generation.desafio.entity.Participante;
import org.generation.desafio.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe representativa para o acesso aos dados da tabela Participante. Busque
 * no README.md deste pacote.
 */
@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    /**
     * Método que retorna as turmas com base no nome, a pesquisa é realizada em
     * quaisquer posição da coluna descrição da tabela Turma
     */
    @Query(value = "SELECT * FROM participante as p WHERE p.nome LIKE %:name%", nativeQuery = true) //select nativo
    List<Participante> getByNameParticipante(@Param("name") String name);

}
