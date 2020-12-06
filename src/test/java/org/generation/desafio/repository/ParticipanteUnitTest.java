package org.generation.desafio.repository;

import com.github.javafaker.Faker;
import org.generation.desafio.entity.Participante;
import org.generation.desafio.entity.utils.ParticipanteUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe para realização dos testes unitários para a entidade Participante
 */
@SpringBootTest
public class ParticipanteUnitTest {

    // Variaveis usadas para mockup de dados
    private static final long PARTICIPANTE_QUANTIDADE = 10;
    private static final Long PARTICIPANTE_ID = 1L;
    private static final String EMAIL = "thiago.filadelfo@gmail.com";
    private static final String EMAIL_ATUALIZADO = "thiagoo@arrayof.io";
    private static final String NOME = "Thiago";
    private static final String NOME_ATUALIZADO = "Thiago Filadelfo";

    /**
     * Variável representativo do acesso aos dados
     * @see ParticipanteRepository
     */
    @MockBean
    private ParticipanteRepository participanteRepository;

    /* ou esse jeito
    /**
     * Método chamado antes de começar a chamar os métodos de testes
    @BeforeEach
    public void setUp() {
        participanteRepository = Mockito.mock(ParticipanteRepository.class);
    }
    */


    /**
     * Método com a finalidade de testar a integridade dos dados armazenados na
     * entidade Participante
     */
    @Test
    @DisplayName("Testar a integridade dos dados armazenados na entidade Participante")
    public void testarInsercao() {

        /* Trecho de código de mockup para representar uma insercao na base de dados */
        Participante persisted = ParticipanteUtils.createModelObject(PARTICIPANTE_ID, EMAIL, NOME, null, null); // Variavel simbolica para representar o objeto de retorno após gravação do banco de dado
        Mockito.when(participanteRepository.saveAndFlush(Mockito.any(Participante.class))).thenReturn(persisted); // Mockup do processo de pesistencia dos dados
        /* Trecho de código de mockup para representar uma insercao na base de dados */

        //Objeto a ser gravado
        Participante created = ParticipanteUtils.createModelObject(null, EMAIL, NOME, null, null);

        //Objeto de retorno do banco de dados
        Participante returned = participanteRepository.saveAndFlush(created);

        /////
        // Teste de integridade
        /////
        assertEquals(persisted, returned);
    }

    /**
     * Método com a finalidade de testar a listagem de participantes cadastrado na base
     */
    @Test
    @DisplayName("Testar a listagem de participantes")
    public void testarListagem() {
        /* Trecho de código de mockup para representar uma listagem na base de dados */
        Faker faker = new Faker();
        List<Participante> fParticipantes = new ArrayList<>();

        for (int i = 0; i < PARTICIPANTE_QUANTIDADE; i++) {
            Participante participante = ParticipanteUtils.createModelObject((long) i, faker.internet().emailAddress(),
                    faker.artist().name(), null, null);
            fParticipantes.add(participante);
        }

        Mockito.when(participanteRepository.findAll()).thenReturn(fParticipantes); // Mockup do processo de listagem
        /* Trecho de código de mockup para representar uma listagem na base de dados */

        List<Participante> participantes = participanteRepository.findAll();

        /////
        // Teste de integridade
        /////
        assertEquals(participantes.size(), PARTICIPANTE_QUANTIDADE);
    }

    /**
     * Método com a finalidade de testar atualizacao dos dados do participante
     */
    @Test
    public void testarAtualizacao() {
        /* Trecho de código de mockup para representar uma insercao na base de dados */
        Participante persisted = ParticipanteUtils.createModelObject(PARTICIPANTE_ID, EMAIL, NOME, null, null); // Variavel simbolica para representar o objeto de retorno após gravação do banco de dado
        Mockito.when(participanteRepository.save(Mockito.any(Participante.class))).thenReturn(persisted); // Mockup do processo de pesistencia dos dados
        /* Trecho de código de mockup para representar uma insercao na base de dados */

        //Objeto a ser gravado
        Participante updated = ParticipanteUtils.createModelObject(PARTICIPANTE_ID, EMAIL_ATUALIZADO, NOME_ATUALIZADO, null, null);

        //Objeto de retorno do banco de dados
        Participante returned = participanteRepository.save(updated);

        /////
        // Teste de integridade
        /////
        assertEquals(persisted, returned);
    }
}
