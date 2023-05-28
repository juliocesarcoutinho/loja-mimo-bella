CREATE TABLE tabela_acesso_end_point
(
    nome_end_point character varying,
    qtd_acesso_end_point integer
);

INSERT INTO tabela_acesso_end_point (nome_end_point, qtd_acesso_end_point)
VALUES ('END_POINT_NOME_PESSOA_FISICA', 0);

ALTER TABLE tabela_acesso_end_point ADD CONSTRAINT nome_end_point_unique UNIQUE (nome_end_point);


