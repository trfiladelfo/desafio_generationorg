package org.generation.desafio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * Classe de representação da tabela do banco de dados. Para mais informações
 * busque no README.md deste pacote.
 */
@Entity // JPA: indica para o processador que é uma entidade mapeável e uma tabela
@Table(name = "participante") // JPA: realizamos o alias do nome da tabela para a classe
public class Participante {
    @Id // JPA: indica qque esse atributo é identificador (chave da tabela)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA: caso não ter preenchimento antes de confirma na base
    // gerará um número
    // único
    private Long id;

    @Column(name = "nome", nullable = false, length = 120) // JPA alias para o nome da coluna da tabela e marcação para
    // não ser
    // de valor nulo
    private String nome;

    @Column(name = "email", nullable = false, length = 120) // JPA alias para o nome da coluna da tabela e marcação para
    // não ser
    // de valor nulo
    private String email;

    @Column(name = "observacoes", length = 250) // JPA alias para o nome da coluna da tabela
    private String observacoes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "turma", nullable = false)
    @JsonBackReference  //Anotacao usada para cancelar a recursividade no momento de preparar o json
    private Turma turma;

    /**
     * Retorna o valor da chave exclusiva da base de dados que representa a
     * identificação do participante
     *
     * @return identificação única do participante
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Marca o valor da chave exclusiva que representa a identificação do
     * participante. Alterando essa chave pode ocasionar grandes problemas nos
     * vinculos das informações deste participante com outras entidades
     * relacionadas.
     *
     * @param id identificação única do participante
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o nome completo do participante
     *
     * @return nome do participante
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Marca o nome do participante
     *
     * @param nome nome do participante
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o e-mail principal do participante
     *
     * @return e-mail do participante
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Marca o e-mail do participante
     *
     * @param email e-mail do participante
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna as observações realizados do participante
     *
     * @return observações cadastradas
     */
    public String getObservacoes() {
        return this.observacoes;
    }

    /**
     * Marca as observações do participante
     *
     * @param observacoes observações para cadastrar
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Retorna a turma que esse participante está vinculado
     *
     * @return turma vinculada
     */
    public Turma getTurma() {
        return this.turma;
    }

    /**
     * Marca a turma do participante está vinculado
     *
     * @param turma turma do participante
     */
    public void setTurma(Turma turma) {
        this.turma = turma;
    }

}
