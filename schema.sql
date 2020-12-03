DROP TABLE IF EXISTS class;
CREATE TABLE class (
    id BIGINT NOT NULL AUTO_INCREMENT,
    description VARCHAR(250) NOT NULL,
    type VARCHAR(20),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS participant;
CREATE TABLE participant (
    id BIGINT NOT NULL AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    observations VARCHAR(250) NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (class_id) REFERENCES class(id)
);

-- Tabela criada para normalizar (Adequação para 2. Forma normal)
-- DROP TABLE IF EXISTS class_participant
-- CREATE TABLE participant (
--    class_id BIGINT NOT NULL,
--    participant_id BIGINT NOT NULL
--    FOREIGN KEY (class_id) REFERENCES class(id)
--    FOREIGN KEY (participant_id) REFERENCES participant(id)
--);