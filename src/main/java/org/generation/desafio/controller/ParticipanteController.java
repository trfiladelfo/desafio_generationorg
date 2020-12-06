package org.generation.desafio.controller;

import org.generation.desafio.entity.Participante;
import org.generation.desafio.entity.Turma;
import org.generation.desafio.repository.ParticipanteRepository;
import org.generation.desafio.repository.TurmaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping({"api/v1/participante"})
public class ParticipanteController {

    @Resource
    private TurmaRepository turmaRepository;

    @Resource
    private ParticipanteRepository participanteRepository;

    /**
     * Retorna a listagem dos participantes cadastradas no banco de dados
     * @param name nome de referencia para Participante, caso for um filtro para a listagem
     * @return  200: corresponde a listagem dos participantes
     *          204: corresponde a listagem fazia
     *          500: corresponde que houve algum erro no servidor
     */
    @GetMapping
    public ResponseEntity<List<Participante>> getAllParticipante(@RequestParam(name="name", required=false) String name) {
        ResponseEntity<List<Participante>> entity;
        if (name != null && !"".equals(name))
            entity = getByNameParticipante(name);
        else entity = getAllParticipante();

        return entity;
    }

    /**
     * Retorna a listagem dos participantes cadastradas no banco de dados
     * @return  200: corresponde a listagem dos participantes
     *          204: corresponde a listagem fazia
     *          500: corresponde que houve algum erro no servidor
     */
    private ResponseEntity<List<Participante>> getAllParticipante() {
        ResponseEntity<List<Participante>> entity;

        try {
            List<Participante> participantes = participanteRepository.findAll();
            entity = participantes.size() > 0
                    ? ResponseEntity.ok(participantes)
                    : ResponseEntity.noContent().build();
        } catch (Exception ex) {
            entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return entity;
    }

    /**
     * Retorna a listagem dos participantes cadastradas no banco de dados
     * @param name nome de referencia para Participante
     * @return  200: corresponde a informacao da Participante
     *          404: corresponde que o nome da Participante nao existe
     *          500: corresponde que houve algum erro no servidor
     */
    public ResponseEntity<List<Participante>> getByNameParticipante(String name) {
        ResponseEntity<List<Participante>> entity;

        if (name != null && !"".equals(name)) {
            try {

                //Participante Participante = new Participante();
                //Participante.setNome(name);
                //List<Participante> participantes = participanteRepository.findAll(Example.of(Participante, ExampleMatcher.matchingAll().withIgnoreCase()));

                List<Participante> participantes = participanteRepository.getByNameParticipante(name);
                entity = participantes.size() > 0
                        ? ResponseEntity.ok(participantes)
                        : ResponseEntity.noContent().build();

            } catch (NoSuchElementException noSuchElementException) {
                entity = ResponseEntity.notFound().build();
            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else entity = ResponseEntity.badRequest().build();

        return entity;
    }


    /**
     * Retorna a informação de uma Participante através de seu id
     * @param id código representivo da Participante
     * @return  200: corresponde a informacao da Participante
     *          404: corresponde que a identificacao da Participante nao existe
     *          500: corresponde que houve algum erro no servidor
     */
    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<Participante> getByIdParticipante(@PathVariable("id") Long id) {
        ResponseEntity<Participante> entity;

        // tratamento para o id ser sempre maior que zero
        if (id > 0) {
            try {
                Optional<Participante> opt = participanteRepository.findById(id);

                /* a forma abaixo é a mais elegante do ternário
                 * que corresponde a mesma coisa
                entity = opt.isPresent()
                        ? ResponseEntity.ok(opt.get())
                        : ResponseEntity.notFound().build();
                 */
                entity = opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            } catch (NoSuchElementException noSuchElementException) {
                entity = ResponseEntity.notFound().build();
            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else entity = ResponseEntity.badRequest().build();

        return entity;
    }

    /**
     * Realiza a inserção da Participante na base de dados.
     *
     * @param participante json com as informações da Participante
     * @return  201: Participante com os dados consolidados
     *          400: corresponde o objeto Participante que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @PostMapping
    public ResponseEntity<Participante> postParticipante(@RequestBody Participante participante) {
        ResponseEntity<Participante> entity;

        Turma turma = participante.getTurma();
        if (turma != null && turma.getId() != null && turma.getId() > 0)
            turmaRepository.findById(turma.getId()).ifPresent(participante::setTurma);

        try {

            //validacao feita manual
            entity = validarRequestParticipante(participante);
            if(entity == null)
                entity = ResponseEntity.status(HttpStatus.CREATED).body(participanteRepository.saveAndFlush(participante));

        } catch (Exception exception) {
            entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return entity;
    }

    /**
     * Realiza a alteração dos dados da Participante na base de dados.
     *
     * @param id identificação da Participante a ser alterada
     * @param participante json com as informações da Participante
     * @return  200: Participante com os dados consolidados
     *          400: corresponde o objeto Participante que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @PutMapping("{id}")
    public ResponseEntity<Participante> putParticipante(@PathVariable("id") Long id, @RequestBody Participante participante) {
        ResponseEntity<Participante> entity;

        if (id > 0) {
            try {
                //validacao feita manual
                entity = validarRequestParticipante(participante);
                if (entity == null) {
                    participante.setId(id);
                    Turma turma = participante.getTurma();
                    if (turma.getId() > 0) {
                        turmaRepository.findById(turma.getId()).ifPresent(participante::setTurma);
                        participante.getTurma().setParticipantes(null);
                    }

                    entity = ResponseEntity.ok(participanteRepository.save(participante));
                }

            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else entity = ResponseEntity.badRequest().build();

        return entity;
    }

    /**
     * Realiza a exclusão dos dados da Participante na base de dados.
     *
     * @param id identificação da Participante a ser excluida
     * @return  200: objeto Participante foi excluído
     *          400: corresponde o objeto Participante que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @DeleteMapping("{id}")
    public ResponseEntity deletetParticipante(@PathVariable("id") Long id) {
        ResponseEntity entity;

        if (id > 0) {
            try {
                participanteRepository.deleteById(id);
                entity = ResponseEntity.ok().build();
            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else entity = ResponseEntity.badRequest().build();

        return entity;

    }


    /**
     * Método para validar o preenchimento do objeto Participante.
     * Será validado a descricao e tipo
     * @param participante
     * @return  null: passou pela validaçao
     *          ResponseEntity: corresponde a algum erro
     */
    private ResponseEntity validarRequestParticipante(Participante participante) {
        ResponseEntity entity = null;
        if (participante == null) entity = ResponseEntity.badRequest().build();
        if ("".equals(participante.getNome())) entity = ResponseEntity.badRequest().build();
        if ("".equals(participante.getEmail())) entity = ResponseEntity.badRequest().build();
        if (participante.getTurma() == null) entity = ResponseEntity.badRequest().build();
        return entity;
    }
}
