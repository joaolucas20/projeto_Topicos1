INSERT INTO Cliente (id, nome, email) VALUES (1, 'Nome Teste', 'email@teste.com');
 INSERT INTO Fabricante (id, nome, cnpj) VALUES (1, 'Nome Teste', '12345678901234');
 INSERT INTO TipoBetoneiraEntity (id, tipo) VALUES (1, 'HORIZONTAL');
 INSERT INTO Betoneira (id, marca, modelo, capacidade, tipo, fabricante_id) VALUES (1, 'Marca Teste', 'Modelo Teste', 10.0, 1, 1);
 INSERT INTO Pedido (id, data_pedido, cliente_id) VALUES (1, CURRENT_DATE(), 1);
 INSERT INTO ItemPedido (id, quantidade, preco, pedido_id) VALUES (1, 5, 25.0, 1);