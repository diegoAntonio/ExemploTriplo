DROP TABLE IF EXIST role_usuario;
DROP TABLE IF EXIST role_historico;
DROP TABLE IF EXIST usuario;
DROP TABLE IF EXIST livro;
DROP TABLE IF EXIST historico_usuario;
DROP TABLE IF EXIST pessoa;

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

CREATE TABLE role_historico
(
  historicousuario_id bigint NOT NULL,
  role character varying(255),
  CONSTRAINT fk_historico_usuario_role FOREIGN KEY (historicousuario_id)
      REFERENCES historico_usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE role_usuario
(
  usuario_id bigint NOT NULL,
  role character varying(255),
  CONSTRAINT fk_usuario_role FOREIGN KEY (usuario_id)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);





