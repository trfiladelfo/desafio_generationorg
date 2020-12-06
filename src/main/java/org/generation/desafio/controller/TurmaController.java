package org.generation.desafio.controller;

import org.generation.desafio.entity.Turma;
import org.generation.desafio.repository.TurmaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/turma")
public class TurmaController {

    @Resource
    private TurmaRepository turmaRepository;

    /**
     * Retorna a listagem das turmas cadastradas no banco de dados
     * @param name nome de referencia para turma, caso for um filtro para a listagem
     * @return  200: corresponde a listagem das turmas
     *          204: corresponde a listagem fazia
     *          500: corresponde que houve algum erro no servidor
     */
    @GetMapping
    public ResponseEntity<List<Turma>> getAllTurma(@RequestParam(name="name", required=false) String name) {
        ResponseEntity<List<Turma>> entity;
        if (name != null && !"".equals(name))
            entity = getByNameTurma(name);
        else entity = getAllTurma();

        return entity;
    }

    /**
     * Retorna a listagem das turmas cadastradas no banco de dados
     * @return  200: corresponde a listagem das turmas
     *          204: corresponde a listagem fazia
     *          500: corresponde que houve algum erro no servidor
     */
    private ResponseEntity<List<Turma>> getAllTurma() {
        ResponseEntity<List<Turma>> entity;

        try {
            List<Turma> turmas = turmaRepository.findAll();
            entity = turmas.size() > 0
                    ? ResponseEntity.ok(turmas)
                    : ResponseEntity.noContent().build();
        } catch (Exception ex) {
            entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return entity;
    }

    /**
     * Retorna a listagem das turmas cadastradas no banco de dados
     * @param name nome de referencia para turma
     * @return  200: corresponde a informacao da turma
     *          404: corresponde que o nome da turma nao existe
     *          500: corresponde que houve algum erro no servidor
     */
    public ResponseEntity<List<Turma>> getByNameTurma(String name) {
        ResponseEntity<List<Turma>> entity;

        if (name != null && !"".equals(name)) {
            try {

                //Turma turma = new Turma();
                //turma.setDescricao(name);
                //List<Turma> turmas = turmaRepository.findAll(Example.of(turma, ExampleMatcher.matchingAll().withIgnoreCase()));

                List<Turma> turmas = turmaRepository.getByNameTurma(name);

                entity = turmas.size() > 0
                        ? ResponseEntity.ok(turmas)
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
     * Retorna a informação de uma turma através de seu id
     * @param id código representivo da turma
     * @return  200: corresponde a informacao da turma
     *          404: corresponde que a identificacao da turma nao existe
     *          500: corresponde que houve algum erro no servidor
     */
    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<Turma> getByIdTurma(@PathVariable("id") Long id) {
        ResponseEntity<Turma> entity;

        // tratamento para o id ser sempre maior que zero
        if (id > 0) {
            try {
                Optional<Turma> opt = turmaRepository.findById(id);

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
     * Realiza a inserção da turma na base de dados.
     *
     * @param turma json com as informações da turma
     * @return  201: turma com os dados consolidados
     *          400: corresponde o objeto turma que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @PostMapping
    public ResponseEntity<Turma> postTurma(@RequestBody Turma turma) {
        ResponseEntity<Turma> entity;

//        List<Participante> participantes = turma.getParticipantes();
//        if (participantes != null) {
//            List<Participante> oParticipantes = new ArrayList<Participante>();
//            participantes.forEach(it -> {
//                oParticipantes.add(participanteRepository.findById(it.getId()).get());
//            });
//            turma.setParticipantes(oParticipantes);
//        }

        try {

            //validacao feita manual
            entity = validarRequestTurma(turma);
            if(entity == null)
                entity = ResponseEntity.status(HttpStatus.CREATED).body(turmaRepository.saveAndFlush(turma));

        } catch (Exception exception) {
            entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return entity;
    }

    /**
     * Realiza a alteração dos dados da turma na base de dados.
     *
     * @param id identificação da turma a ser alterada
     * @param turma json com as informações da turma
     * @return  200: turma com os dados consolidados
     *          400: corresponde o objeto turma que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @PutMapping("{id}")
    public ResponseEntity<Turma> putTurma(@PathVariable("id") Long id, @RequestBody Turma turma) {
        ResponseEntity<Turma> entity;

        if (id > 0) {
            try {
                //validacao feita manual
                entity = validarRequestTurma(turma);
                if (entity == null) {
                    turma.setId(id);
                    entity = ResponseEntity.ok(turmaRepository.save(turma));
                }

            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else entity = ResponseEntity.badRequest().build();

        return entity;
    }

    /**
     * Realiza a exclusão dos dados da turma na base de dados.
     *
     * @param id identificação da turma a ser excluida
     * @return  200: objeto turma foi excluído
     *          400: corresponde o objeto turma que nao atendeu a validação
     *          500: corresponde que houve algum erro no servidor
     */
    @DeleteMapping("{id}")
    public ResponseEntity deletetTurma(@PathVariable("id") Long id) {
        ResponseEntity entity;

        if (id > 0) {
            try {
                turmaRepository.deleteById(id);
                entity = ResponseEntity.ok().build();
            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else entity = ResponseEntity.badRequest().build();

        return entity;

    }


    /**
     * Método para validar o preenchimento do objeto turma.
     * Será validado a descricao e tipo
     * @param turma
     * @return  null: passou pela validaçao
     *          ResponseEntity: corresponde a algum erro
     */
    private ResponseEntity validarRequestTurma(Turma turma) {
        ResponseEntity entity = null;
        if (turma == null) entity = ResponseEntity.badRequest().build();
        if ("".equals(turma.getDescricao())) entity = ResponseEntity.badRequest().build();
        if ("".equals(turma.getTipo())) entity = ResponseEntity.badRequest().build();
        return entity;
    }

}
