package org.generation.desafio.controller;

import org.generation.desafio.controller.exception.ArgumentoIlegalException;
import org.generation.desafio.controller.exception.DesafioException;
import org.generation.desafio.controller.exception.NaoExisteRegistroException;
import org.generation.desafio.core.Validator;
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
        ResponseEntity<List<Participante>> entity = null;

        List<Participante> participantes = participanteRepository.findAll();
        /*entity = participantes.size() > 0
                ? ResponseEntity.ok(participantes)
                : ResponseEntity.noContent().build();*/

        if(participantes.size() > 0)
            entity = ResponseEntity.ok(participantes);
        else throw new NaoExisteRegistroException();

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
        ResponseEntity<List<Participante>> entity = null;

        if (name != null && !"".equals(name)) {
            try {

                //Participante Participante = new Participante();
                //Participante.setNome(name);
                //List<Participante> participantes = participanteRepository.findAll(Example.of(Participante, ExampleMatcher.matchingAll().withIgnoreCase()));

                List<Participante> participantes = participanteRepository.getByNameParticipante(name);
                /*entity = participantes.size() > 0
                ? ResponseEntity.ok(participantes)
                : ResponseEntity.noContent().build();*/

                if(participantes.size() > 0)
                    entity = ResponseEntity.ok(participantes);
                else throw new NaoExisteRegistroException();

            } catch (NoSuchElementException noSuchElementException) {
                throw new NaoExisteRegistroException();
                
            } catch (Exception exception) {
                entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else throw new ArgumentoIlegalException("Você não especificou o paramento name");

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
                 throw new NaoExisteRegistroException(noSuchElementException);
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
        ResponseEntity<Participante> entity = null;

        boolean validado = validarRequestParticipante(participante);

        //validacao feita manual
        if(validado) {
            turmaRepository.findById(participante.getTurma().getId()).ifPresent(participante::setTurma);

            if(participante.getTurma() != null)
                entity = ResponseEntity.status(HttpStatus.CREATED).body(participanteRepository.saveAndFlush(participante));

            else throw new NaoExisteRegistroException("Não existe registro desta turma cadastrada");
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
        ResponseEntity<Participante> entity = null;

        if (id > 0) {
            //validacao feita manual
            boolean validado = validarRequestParticipante(participante);

            if(validado) {

                participante.setId(id);
                turmaRepository.findById(participante.getTurma().getId()).ifPresent(participante::setTurma);

                if(participante.getTurma() != null) {
                    participante.getTurma().setParticipantes(null);
                    entity = ResponseEntity.ok(participanteRepository.save(participante));
                } else throw new NaoExisteRegistroException("Não existe registro desta turma cadastrada");
            }

        } else throw new ArgumentoIlegalException();

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
    private boolean validarRequestParticipante(Participante participante) throws DesafioException {
        if (participante == null) throw new DesafioException("Requisição sem a informação de participante");

        if ("".equals(participante.getNome()))
            throw new DesafioException("Você deve especifcar um nome para esse participante");
        else if(participante.getNome().length() > 120) throw new DesafioException("Você execedeu o limite de 120 caracteres para o nome");

        if ("".equals(participante.getEmail()))
            throw new DesafioException("Você deve especifcar um e-mail para esse participante");
        else if(participante.getEmail().length() > 120) throw new DesafioException("Você execedeu o limite de 120 caracteres para o e-mail");
        else if(!Validator.isEMail(participante.getEmail())) throw new DesafioException("Parece não ser um e-mail");


        if (participante.getTurma() == null) throw new DesafioException("Você deve especifcar uma turma para esse participante");
        else if (participante.getTurma().getId() < 1) throw new DesafioException("Você deve especifcar a idenficação desta turma");

        return true;
    }
}
