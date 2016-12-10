-- Gera��o de Modelo f�sico
-- Sql ANSI 2003 - brModelo.



CREATE TABLE IF NOT EXISTS salao (
    id_salao serial,
    CONSTRAINT id_salao_pk PRIMARY KEY(id_salao),
    num_mesas integer,
    nome varchar(50)
);

-- status_mesa
-- v = vazia
-- o = ocupada
CREATE TABLE IF NOT EXISTS mesa (
    id_mesa serial,
    CONSTRAINT id_mesa_pk PRIMARY KEY(id_mesa),
    num_cadeiras integer,
    numero integer,
    status_mesa varchar(1),
    id_salao integer,
    CONSTRAINT id_salao_fk FOREIGN KEY(id_salao) REFERENCES salao (id_salao) ON UPDATE CASCADE ON DELETE CASCADE
);

-- status_pedido
-- a = aberto
-- f = fechado
CREATE TABLE IF NOT EXISTS pedido (
    id_pedido serial,
    CONSTRAINT id_pedido_pk PRIMARY KEY(id_pedido),
    hora time,
    data date,
    status_pedido varchar(1),
    id_mesa integer,
    CONSTRAINT id_mesa_fk FOREIGN KEY(id_mesa) REFERENCES mesa (id_mesa) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pedido_usuario (
    id_pedido_usuario serial,
    CONSTRAINT id_pedido_usuario_pk PRIMARY KEY(id_pedido_usuario),
    id_usuario integer,
    id_pedido integer,
    CONSTRAINT id_pedido_fk FOREIGN KEY(id_pedido) REFERENCES pedido (id_pedido) ON UPDATE CASCADE ON DELETE CASCADE
);

-- tipo_usuario
-- s = super user
-- n = normal user
-- 0 = not setted
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario serial,
    CONSTRAINT id_usuario_pk PRIMARY KEY(id_usuario),
    login varchar(50),
    senha varchar(50),
    tipo_usuario varchar(1)
);

CREATE TABLE IF NOT EXISTS funcionario (
    id_funcionario serial,
    CONSTRAINT id_funcionario_pk PRIMARY KEY(id_funcionario),
    cpf varchar(14),
    nome varchar(50),
    data_nascimento date,
    telefone varchar(13),
    salario float,
    cargo varchar(50)
);

--- unidade_medida 1 = unidade
--- unidade_medida 2 = kilos
--- unidade_medida 3 = litros
CREATE TABLE IF NOT EXISTS ingrediente (
    id_ingrediente serial,
    CONSTRAINT id_ingrediente_pk PRIMARY KEY(id_ingrediente),
    nome varchar(50),
    quantidade integer,
    unidade_medida integer
);

CREATE TABLE IF NOT EXISTS prato_ingrediente (
    id_prato_ingrediente serial,
    CONSTRAINT id_prato_ingrediente_pk PRIMARY KEY(id_prato_ingrediente),
    id_ingrediente integer,
    id_prato integer,
    CONSTRAINT id_ingrediente_fk FOREIGN KEY(id_ingrediente) REFERENCES ingrediente (id_ingrediente) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pedido_produto (
    id_pedido_produto serial,
    CONSTRAINT id_pedido_produto_pk PRIMARY KEY(id_pedido_produto),
    id_produto integer,
    id_pedido integer,
    CONSTRAINT id_pedido_fk FOREIGN KEY(id_pedido) REFERENCES pedido (id_pedido) ON UPDATE CASCADE ON DELETE CASCADE
);

-- tipo 1 = refeicao
-- tipo 2 = bebida
CREATE TABLE IF NOT EXISTS produto (
    id_produto serial,
    CONSTRAINT id_produto1_pk PRIMARY KEY(id_produto),
    nome varchar(50),
    tipo integer,
    preco_venda float
);

CREATE TABLE IF NOT EXISTS prato (
    id_prato serial,
    id_produto integer,
    CONSTRAINT id_prato_pk PRIMARY KEY(id_prato),
    CONSTRAINT id_produto_fk FOREIGN KEY(id_produto) REFERENCES produto (id_produto) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bebida (
    id_bebida serial,
    id_produto integer,
    CONSTRAINT id_bebida_pk PRIMARY KEY(id_bebida),
    tamanho integer,
    quantidade integer,
    CONSTRAINT id_produto_fk FOREIGN KEY(id_produto) REFERENCES produto (id_produto) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compra (
    id_compra serial,
    CONSTRAINT id_compra_pk PRIMARY KEY(id_compra),
    data date,
    preco_compra float,
    quantidade integer,
    id_compra_bebida integer,
    id_compra_ingrediente integer,
    FOREIGN KEY(id_compra_bebida) REFERENCES bebida (id_bebida) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY(id_compra_ingrediente) REFERENCES ingrediente (id_ingrediente) ON UPDATE CASCADE ON DELETE SET NULL
);

ALTER TABLE pedido_usuario ADD CONSTRAINT id_usuario_fk FOREIGN KEY(id_usuario) REFERENCES usuario (id_usuario) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE usuario ADD CONSTRAINT id_usuario_fk FOREIGN KEY(id_usuario) REFERENCES funcionario (id_funcionario) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE prato_ingrediente ADD CONSTRAINT id_prato_fk FOREIGN KEY(id_prato) REFERENCES prato (id_prato) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE pedido_produto ADD CONSTRAINT id_produto_fk FOREIGN KEY(id_produto) REFERENCES produto (id_produto) ON UPDATE CASCADE ON DELETE CASCADE;
