package org.generation.desafio.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.generation.desafio.entity.Turma;
import org.generation.desafio.entity.utils.TurmaUtils;
import org.generation.desafio.repository.TurmaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Random;


/**
 * Classe para realização dos testes de integracao para o recurso turma
 */
@SpringBootTest(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" }) // para criar e deletar a tabela de teste
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // para executar os testes em order conforme a anotacao @Order
@AutoConfigureMockMvc
public class TurmaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TurmaRepository turmaRepository;

    /**
     * Método com a finalidade de testar a integridade dos dados armazenados na
     * entidade Turma
     */
    @Test
    @Order(1)
    public void testarInsercao() throws Exception {
        String descricao = "Turma amarela";
        String tipo = "Presencial";

        //Objeto a ser gravado
        Turma created = TurmaUtils.createModelObject(null, descricao, tipo);

        // Convertendo para json o objeto
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Turma entity = turmaRepository.findOne(Example.of(created)).get();

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(tipo).isEqualTo(entity.getTipo());
    }

    /**
     * Método com a finalidade de testar a inserção em lote
     */
    @Test
    @Order(2)
    public void testarInsercaoTurmaDadosRandomicos() throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        int quantidade = random.nextInt(30);
        for (int i = 0; i < quantidade; i++) {
            String descricao = faker.funnyName().name();
            String tipo = faker.artist().name();

            //Objeto a ser gravado
            Turma created = TurmaUtils.createModelObject(null, descricao, tipo);

            // Convertendo para json o objeto
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(created);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/turma")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            Turma entity = turmaRepository.findOne(Example.of(created)).get();

            /////
            // Teste de integridade
            /////
            Assertions.assertThat(tipo).isEqualTo(entity.getTipo());
        }
    }

    /**
     * Método com a finalidade de testar a listagem completa das turmas cadastradas
     */
    @Test
    @Order(3)
    public void testarListagemCompleta() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Turma[] turmas = (mapper.readValue(json, Turma[].class));
        List<Turma> turmas = (mapper.readValue(json, new TypeReference<List<Turma>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(turmas.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar a busca por um nome exata
     */
    @Test
    @Order(4)
    public void testarListagemFiltradaPorNomeExato() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma")
                .param("name", "Turma amarela")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Turma[] turmas = (mapper.readValue(json, Turma[].class));
        List<Turma> turmas = (mapper.readValue(json, new TypeReference<List<Turma>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(turmas.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar a busca por um nome aproximado
     */
    @Test
    @Order(5)
    public void testarListagemFiltradaPorNomeAproximado() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma")
                .param("name", "amarela")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Turma[] turmas = (mapper.readValue(json, Turma[].class));
        List<Turma> turmas = (mapper.readValue(json, new TypeReference<List<Turma>>(){}));

        /////
        // Teste de integridade
        /////
        Assertions.assertThat(turmas.isEmpty()).isFalse();
    }

    /**
     * Método com a finalidade de testar busca por id
     */
    @Test
    @Order(6)
    public void testarBuscarPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Método com a finalidade de testar a atualizacao de uma entidade Turma
     */
    @Test
    @Order(7)
    public void testarAtualizacao() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Turma[] turmas = (mapper.readValue(json, Turma[].class));
        List<Turma> turmas = (mapper.readValue(json, new TypeReference<List<Turma>>(){}));

        //// Pegando uma posição do array de forma randomica
        Random randomico = new Random();
        int index = randomico.nextInt(turmas.size());

        //Objeto a ser alterado
        Turma turma = turmas.get(index);
        turma.setDescricao(String.format("Descricao alterada %s", index));
        turma.setParticipantes(null); // para nao parser coisa a mais
        String jsonTurma = mapper.writeValueAsString(turma);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/turma/{id}", turma.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonTurma))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Método com a finalidade de testar remoção de uma entidade Turma
     */
    @Test
    @Order(8)
    public void testarDelecao() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String json = result.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        //Turma[] turmas = (mapper.readValue(json, Turma[].class));
        List<Turma> turmas = (mapper.readValue(json, new TypeReference<List<Turma>>(){}));

        //// Pegando uma posição do array de forma randomica
        Random randomico = new Random();
        int index = randomico.nextInt(turmas.size());

        //Objeto a ser alterado
        Turma turma = turmas.get(index);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/turma/{id}", turma.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
