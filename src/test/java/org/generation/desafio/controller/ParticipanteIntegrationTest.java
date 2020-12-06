package org.generation.desafio.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.generation.desafio.entity.Participante;
import org.generation.desafio.entity.Turma;
import org.generation.desafio.entity.utils.ParticipanteUtils;
import org.generation.desafio.entity.utils.TurmaUtils;
import org.generation.desafio.repository.ParticipanteRepository;
import org.generation.desafio.repository.TurmaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Random;


/**
 * Classe para realização dos testes de integracao para o recurso participante
 */
@SpringBootTest(properties = { "spring.jpa.hibernate.ddl-auto=update" }) // para criar e deletar a tabela de teste
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // para executar os testes em order conforme a anotacao @Order
@AutoConfigureMockMvc
public class ParticipanteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    /**
     * Método com a finalidade de testar a integridade dos dados armazenados na
     * entidade Participante
     */
    @Test
    @Order(1)
    public void testarInsercao() throws Exception {

        Turma turma = turmaRepository.save(TurmaUtils.createModelObject(null, "Turma verde", "Presencial"));

        String email = "thiago.filadelfo@gmail.com";
        String nome = "Thiago";
        String obs = "obs";

        //Objeto a ser gravado
        Participante created = ParticipanteUtils.createModelObject(null, email, nome, obs, turma);

        // Convertendo para json o objeto
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/participante")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Participante entity = participanteRepository.findOne(Example.of(created)).get();

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(nome).isEqualTo(entity.getNome());
    }

    /**
     * Método com a finalidade de testar a inserção em lote
     */
    @Test
    @Order(2)
    public void testarInsercaoParticipanteDadosRandomicos() throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        Turma turma1 = turmaRepository.save(TurmaUtils.createModelObject(null, faker.funnyName().name(), "Presencial"));
        Turma turma2 = turmaRepository.save(TurmaUtils.createModelObject(null, faker.funnyName().name(), "A Distancia"));

        int quantidade = random.nextInt(30);
        for (int i = 0; i < quantidade; i++) {
            String email = faker.internet().emailAddress();
            String nome = faker.artist().name();
            String obs = faker.lorem().fixedString(20);

            //Objeto a ser gravado
            Participante created = ParticipanteUtils.createModelObject(null, email, nome, obs, i%2 == 0 ? turma1 : turma2 );

            // Convertendo para json o objeto
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(created);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/participante")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            Participante entity = participanteRepository.findOne(Example.of(created)).get();

            /////
            // Teste de integridade
            /////
            Assertions.assertThat(nome).isEqualTo(entity.getNome());
        }
    }

    /**
     * Método com a finalidade de testar a listagem completa das participantes cadastradas
     */
    @Test
    @Order(3)
    public void testarListagemCompleta() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Participante[] participantes = (mapper.readValue(json, Participante[].class));
        List<Participante> participantes = (mapper.readValue(json, new TypeReference<List<Participante>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(participantes.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar a busca por um nome exata
     */
    @Test
    @Order(4)
    public void testarListagemFiltradaPorNomeExato() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante")
                .param("name", "Thiago")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Participante[] participantes = (mapper.readValue(json, Participante[].class));
        List<Participante> participantes = (mapper.readValue(json, new TypeReference<List<Participante>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(participantes.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar a busca por um nome aproximado
     */
    @Test
    @Order(5)
    public void testarListagemFiltradaPorNomeAproximado() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante")
                .param("name", "a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Participante[] participantes = (mapper.readValue(json, Participante[].class));
        List<Participante> participantes = (mapper.readValue(json, new TypeReference<List<Participante>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(participantes.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar busca por id
     */
    @Test
    @Order(6)
    public void testarBuscarPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    /**
     * Método com a finalidade de testar a atualizacao de uma entidade Participante
     */
    @Test
    @Order(7)
    public void testarAtualizacao() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Participante[] participantes = (mapper.readValue(json, Participante[].class));
        List<Participante> participantes = (mapper.readValue(json, new TypeReference<List<Participante>>(){}));

        //// Pegando uma posição do array de forma randomica
        Random randomico = new Random();
        int index = randomico.nextInt(participantes.size());

        //Objeto a ser alterado
        Participante participante = participantes.get(index);
        participante.setEmail(String.format("Email alterada %s", index));
        Turma turma = participante.getTurma();
        turma.setParticipantes(null); // para nao parser coisa a mais
        participante.setTurma(turma);
        String jsonParticipante = mapper.writeValueAsString(participante);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/participante/{id}", participante.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonParticipante))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Método com a finalidade de testar remoção de uma entidade Participante
     */
    @Test
    @Order(8)
    public void testarDelecao() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/participante")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Participante[] participantes = (mapper.readValue(json, Participante[].class));
        List<Participante> participantes = (mapper.readValue(json, new TypeReference<List<Participante>>(){}));

        //// Pegando uma posição do array de forma randomica
        Random randomico = new Random();
        int index = randomico.nextInt(participantes.size());

        //Objeto a ser alterado
        Participante participante = participantes.get(index);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/participante/{id}", participante.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
