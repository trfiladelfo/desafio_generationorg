DROP TABLE IF EXISTS turma;
CREATE TABLE turma (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(250) NOT NULL,
    tipo VARCHAR(20),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS participant;
CREATE TABLE participante (
    id BIGINT NOT NULL AUTO_INCREMENT,
    turma_id BIGINT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    observacoes VARCHAR(250) NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (turma_id) REFERENCES turma(id)
);

-- Tabela criada para normalizar (Adequação para 2. Forma normal)
DROP TABLE IF EXISTS turma_participante
CREATE TABLE participante (
    turma_id BIGINT NOT NULL,
    participante_id BIGINT NOT NULL
    FOREIGN KEY (turma_id) REFERENCES turma(id)
    FOREIGN KEY (participante_id) REFERENCES participante(id)
);