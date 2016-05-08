DROP TABLE IF EXISTS auth_role_usuario;
DROP TABLE IF EXISTS auth_role_historico;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS livro;
DROP TABLE IF EXISTS historico_usuario;
DROP TABLE IF EXISTS pessoa;

CREATE TABLE pessoa
(
  id bigint NOT NULL,
  cpf character varying(11),
  dtnascimento date,
  bairro character varying(255),
  cep character varying(255),
  cidade character varying(255),
  complemento character varying(255),
  numero integer NOT NULL,
  rua character varying(255),
  nome character varying(255),
  "timestamp" timestamp without time zone,
  CONSTRAINT pessoa_pkey PRIMARY KEY (id),
  CONSTRAINT unique_pessoa_cpf UNIQUE (cpf)
);

CREATE TABLE historico_usuario
(
  id bigint NOT NULL,
  acao character varying(255),
  pessoa_usuario_id bigint,
  usuario_id bigint,
  login character varying(255),
  pass character varying(255),
  "timestamp" timestamp without time zone,
  CONSTRAINT historico_usuario_pkey PRIMARY KEY (id),
  CONSTRAINT unique_historico_usuario_usuario_pessoa UNIQUE (id, usuario_id, pessoa_usuario_id)
);
  
  CREATE TABLE livro
(
  id bigint NOT NULL,
  autores character varying(255),
  caracteristicas character varying(255),
  isbn character varying(255),
  preco double precision NOT NULL,
  quantidadepaginas integer NOT NULL,
  titulo character varying(255),
  CONSTRAINT livro_pkey PRIMARY KEY (id)
);

CREATE TABLE usuario
(
  id bigint NOT NULL,
  login character varying(255),
  pass character varying(255),
  "timestamp" timestamp without time zone,
  pessoa_id bigint,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT fk_usuario_pessoa FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE auth_role
(
  id bigint NOT NULL,
  nivel integer NOT NULL,
  nome character varying(255),
  "timestamp" timestamp without time zone,
  CONSTRAINT auth_role_pkey PRIMARY KEY (id),
  CONSTRAINT unique_nome UNIQUE (nome)
);

CREATE TABLE auth_role_historico
(
  historico_usuario_id bigint NOT NULL,
  auth_role_id bigint NOT NULL,
  CONSTRAINT fk_auth_role_historico_historico_usuario_id FOREIGN KEY (historico_usuario_id)
      REFERENCES historico_usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_auth_role_historico_auth_role_id FOREIGN KEY (auth_role_id)
      REFERENCES auth_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE auth_role_usuario
(
  usuario_id bigint NOT NULL,
  auth_role_id bigint NOT NULL,
  CONSTRAINT fk_auth_role_usuario_auth_role_id FOREIGN KEY (auth_role_id)
      REFERENCES auth_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_auth_role_usuario_usuario_id FOREIGN KEY (usuario_id)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


INSERT INTO auth_role(id, nome, nivel, "timestamp") VALUES (1, 'ADMIN', 1, now()),
(2, 'MODERADOR', 2, now()),(3, 'COLABORADOR', 3, now()),(4, 'USER', 100, now());

INSERT INTO pessoa(id, cpf, dtnascimento, bairro, cep, cidade, complemento, numero, rua, nome, "timestamp") VALUES
(nextval ('pessoa_id_seq'),'12345678909', to_date('04/10/1989','dd/MM/YYYY'),'Nossa Senhora do O',
'53431265','Paulista', 'Casa',140,'Rua Barra Longa','Andre Alcantara', now());		
INSERT INTO usuario(id, login, pass, "timestamp", pessoa_id ) VALUES(nextval('usuario_id_seq'), 'admin', '$2a$10$IBctXSUwEGeZk9fbvWCmBuIyT68dxwrRjUGfW6vXGtG7r1F/vdNUS', now(), currval('pessoa_id_seq'));

INSERT INTO auth_role_usuario(usuario_id, auth_role_id) values(currval('usuario_id_seq'), 1);