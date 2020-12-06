package org.generation.desafio.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de representação da tabela do banco de dados. Para mais informações
 * busque no README.md deste pacote.
 */
@Entity // JPA: indica para o processador que é uma entidade mapeável e uma tabela
@Table(name = "turma") // JPA: realizamos o alias do nome da tabela para a classe
@JsonIdentityInfo(scope = Turma.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") //Forma diferente para cancelar a recursividade no momento de preparar o json
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // JPA: indica qque esse atributo é identificador (chave da tabela)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA: caso não ter preenchimento antes de confirma na base
    // gerará um número único
    private Long id;

    @Column(name = "descricao", nullable = false, length = 250) // JPA alias para o nome da coluna da tabela e
    // marcação para não ser
    // de valor nulo
    private String descricao;

    @Column(name = "tipo", nullable = false, length = 20) // JPA alias para o nome da coluna da tabela e
    // marcação para não ser
    // de valor nulo
    private String tipo;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonManagedReference //Anotacao usada para cancelar a recursividade no momento de preparar o json
    private List<Participante> participantes;

    /**
     * Retorna o valor da chave exclusiva da base de dados que representa a
     * identificação da turma
     *
     * @return identificação única da turma
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Marca o valor da chave exclusiva que representa a identificação da turma.
     * Alterando essa chave pode ocasionar grandes problemas nos vinculos das
     * informações desta turma com outras entidades relacionadas.
     *
     * @param id identificação única da turma
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Valor referente a descrição da turma, uma forma humana de facilmente
     * identificar a que se refere essa turma.
     *
     * @return descrição da turma
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Marca o valor do rotulo da turma. Sua alteração não causa impactos nos
     * vinculos das informações desta turma, pois é uma forma mais legivel de
     * apresentar a identificação de qual turma estamos nos referindo.
     *
     * @param descricao descrição da turma
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Lista de pessoas que participam desta turma.
     *
     * @return participantes cadastrados nesta turma
     */
    public List<Participante> getParticipantes() {
        return this.participantes;
    }

    /**
     * Cadastra os participantes para esta turma
     *
     * @param participantes lista de participantes para serem cadastrados nesta
     *                      turma
     */
    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }
}
