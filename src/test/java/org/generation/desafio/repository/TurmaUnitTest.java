package org.generation.desafio.repository;

import com.github.javafaker.Faker;
import org.generation.desafio.entity.Turma;
import org.generation.desafio.entity.utils.TurmaUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe para realização dos testes unitários para a entidade Turma
 */
@SpringBootTest
public class TurmaUnitTest {

    private static final long TURMA_QUANTIDADE = 10;
    private static final Long TURMA_ID = 1L;
    private static final String DESCRICAO = "Turma Amarela";
    private static final String DESCRICAO_ATUALIZADO = "Turma Verde";
    private static final String TIPO = "Presencial";
    private static final String TIPO_ATUALIZADO = "A Distancia";

    @MockBean
    private TurmaRepository turmaRepository;

    /* ou esse jeito
    @BeforeEach
    public void setUp() {
        turmaRepository = Mockito.mock(TurmaRepository.class);
    }
    */


    /**
     * Método com a finalidade de testar a integridade dos dados armazenados na
     * entidade Turma
     */
    @Test
    @DisplayName("Testar a integridade dos dados armazenados na entidade Turma")
    public void testarInsercao() {

        /* Trecho de código de mockup para representar uma insercao na base de dados */
        Turma persisted = TurmaUtils.createModelObject(TURMA_ID, DESCRICAO, TIPO); // Variavel simbolica para representar o objeto de retorno após gravação do banco de dado
        Mockito.when(turmaRepository.saveAndFlush(Mockito.any(Turma.class))).thenReturn(persisted); // Mockup do processo de pesistencia dos dados
        /* Trecho de código de mockup para representar uma insercao na base de dados */

        //Objeto a ser gravado
        Turma created = TurmaUtils.createModelObject(null, DESCRICAO, TIPO);

        //Objeto de retorno do banco de dados
        Turma returned = turmaRepository.saveAndFlush(created);

        /////
        // Teste de integridade
        /////
        assertEquals(persisted, returned);
    }

    @Test
    public void testarListagem() {
        /* Trecho de código de mockup para representar uma listagem na base de dados */
        Faker faker = new Faker();
        List<Turma> fTurmas = new ArrayList<>();

        for(int i=0; i < TURMA_QUANTIDADE; i++) {
            Turma turma = TurmaUtils.createModelObject((long) i, faker.funnyName().name(), faker.artist().name());
            fTurmas.add(turma);
        }

        Mockito.when(turmaRepository.findAll()).thenReturn(fTurmas); // Mockup do processo de listagem
        /* Trecho de código de mockup para representar uma listagem na base de dados */

        List<Turma> turmas = turmaRepository.findAll();

        /////
        // Teste de integridade
        /////
        assertEquals(turmas.size(), TURMA_QUANTIDADE);
    }

    @Test
    public void testarAtualizacao() {
        /* Trecho de código de mockup para representar uma insercao na base de dados */
        Turma persisted = TurmaUtils.createModelObject(TURMA_ID, DESCRICAO_ATUALIZADO, TIPO_ATUALIZADO); // Variavel simbolica para representar o objeto de retorno após gravação do banco de dado
        Mockito.when(turmaRepository.save(Mockito.any(Turma.class))).thenReturn(persisted); // Mockup do processo de pesistencia dos dados
        /* Trecho de código de mockup para representar uma insercao na base de dados */

        //Objeto a ser gravado
        Turma updated = TurmaUtils.createModelObject(TURMA_ID, DESCRICAO_ATUALIZADO, TIPO_ATUALIZADO);

        //Objeto de retorno do banco de dados
        Turma returned = turmaRepository.save(updated);

        /////
        // Teste de integridade
        /////
        assertEquals(persisted, returned);
    }
}
