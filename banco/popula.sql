insert into salao(num_mesas, nome) values
(4, 'salao unico');

-- v = vazia
-- o = ocupada
insert into mesa(num_cadeiras, numero, status_mesa, id_salao) values
(5, 1, 'v', 1),
(5, 2, 'v', 1),
(5, 3, 'v', 1),
(5, 4, 'v', 1);

insert into funcionario(cpf, nome, data_nascimento, telefone, salario, cargo) values
('023.344.123-22', 'Nome Garçon', '1990-01-01', '(41)2341-2341', 1000.0, 'garcon'),
('093.564.783-22', 'Nome Gerente', '1980-11-11', '(41)2991-8741', 2000.0, 'gerente');

-- s = super usuario
-- n = normal usuario
insert into usuario(login, senha, tipo_usuario) values
('garcon', '123', 'n'),
('adm', 'adm', 's');

--- unidade_medida 1 = unidade
--- unidade_medida 2 = kilos
--- unidade_medida 3 = litros
insert into ingrediente(nome, quantidade, unidade_medida) values
('carne', 100, 1),
('alface', 100, 1),
('feijao', 100, 2),
('arroz', 100, 2),
('oleo', 100, 3),
('beterraba', 100, 1),
('abacaxi', 100, 1);

-- tipo 1 = refeicao
-- tipo 2 = bebida
insert into produto(nome, tipo, preco_venda) values
('hot-dog', 1, 6.0),
('pf', 1, 10.0),
('suco de beterraba', 2, 5.0),
('refrigerente', 2, 5.50);

insert into prato(id_produto) values
(1),
(2);

insert into bebida(id_produto, tamanho, quantidade) values
(3, 350, 50),
(4, 500, 100);

insert into compra(data, preco_compra, quantidade, id_compra_bebida, id_compra_ingrediente) values
('2016-11-01', 1000.0, 100, 1, null),
('2016-11-01', 100.0, 100, 2, null),
('2016-11-01', 350.0, 100, null, 1),
('2016-11-01', 270.0, 100, null, 2),
('2016-11-01', 200.0, 100, null, 3),
('2016-11-01', 130.0, 100, null, 4),
('2016-11-01', 400.0, 100, null, 5),
('2016-11-01', 400.0, 100, null, 6),
('2016-11-01', 400.0, 100, null, 7);

insert into prato_ingrediente(id_ingrediente, id_prato) values
(1, 1),
(2, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2);
