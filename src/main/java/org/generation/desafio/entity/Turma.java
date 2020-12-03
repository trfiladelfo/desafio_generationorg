package org.generation.desafio.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Classe de representação da tabela do banco de dados. Para mais informações
 * busque no README.md deste pacote.
 */
@Entity // JPA: indica para o processador que é uma entidade mapeável e uma tabela
@Table(name = "class") // JPA: realizamos o alias do nome da tabela para a classe
public class Turma implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id // JPA: indica qque esse atributo é identificador (chave da tabela)
    @GeneratedValue // JPA: caso não ter preenchimento antes de confirma na base gerará um número
                    // único
    private Long id;

    @Column(name = "description", nullable = false, length = 250) // JPA alias para o nome da coluna da tabela e
                                                                  // marcação para não ser
    // de valor nulo
    private String descricao;

    @ManyToMany
    private Set<Participante> participantes;

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

    /**
     * Lista de pessoas que participam desta turma.
     * 
     * @return participantes cadastrados nesta turma
     */
    public Set<Participante> getParticipantes() {
        return this.participantes;
    }

    /**
     * Cadastra os participantes para esta turma
     * 
     * @param participantes lista de participantes para serem cadastrados nesta
     *                      turma
     */
    public void setParticipantes(Set<Participante> participantes) {
        this.participantes = participantes;
    }

}
