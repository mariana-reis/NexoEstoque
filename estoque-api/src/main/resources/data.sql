INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Notebook Dell XPS 15', 'Intel i7 12ª Gen, 16GB RAM, SSD 512GB', 'ELETRONICO', 4200.00, 8);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Smartphone Samsung Galaxy S24', 'Tela 6.2", 256GB, 5G', 'ELETRONICO', 2800.00, 15);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Smart TV LG 55"', 'OLED, 4K, 120Hz, WebOS', 'ELETRONICO', 3100.00, 5);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Geladeira Brastemp Frost Free', '375 litros, Inox, classe A', 'ELETRODOMESTICO', 2400.00, 6);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Lavadora Electrolux 12kg', 'Inverter, 12 programas, branca', 'ELETRODOMESTICO', 1600.00, 10);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Micro-ondas Panasonic 32L', 'Inox, 10 níveis de potência', 'ELETRODOMESTICO', 580.00, 20);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Sofá Retrátil 3 Lugares', 'Tecido suede, cor cinza chumbo', 'MOVEL', 1200.00, 4);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Mesa de Escritório L', 'MDF 25mm, 1,60m x 1,20m, nogal', 'MOVEL', 480.00, 12);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Cadeira Gamer Pro', 'Reclinável 180°, apoio lombar, preto/vermelho', 'MOVEL', 650.00, 7);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (1, 'SAIDA', 5500.00, '2026-06-10', 2);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (2, 'SAIDA', 3699.00, '2026-06-15', 3);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (3, 'SAIDA', 4200.00, '2026-06-20', 1);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (4, 'SAIDA', 3200.00, '2026-07-01', 2);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (9, 'SAIDA', 950.00, '2026-07-05', 3);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (6, 'ENTRADA', 0.00, '2026-07-10', 10);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Tablet Lenovo Tab P12', 'Tela 12.7", 128GB, Octa-Core', 'ELETRONICO', 1500.00, 12);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Fone de Ouvido Sony WH-1000XM5', 'Cancelamento de ruído ativo, Bluetooth', 'ELETRONICO', 1800.00, 25);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Monitor LG UltraWide 29"', 'IPS, Full HD, HDR10', 'ELETRONICO', 900.00, 6);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Caixa de Som JBL Boombox 3', 'À prova dágua, Bluetooth', 'ELETRONICO', 1900.00, 4);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Aspirador de Pó Robô Xiaomi', 'Mapeamento inteligente, Wi-Fi', 'ELETRODOMESTICO', 1100.00, 9);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Fritadeira Air Fryer Philips', 'Tecnologia Rapid Air, preta', 'ELETRODOMESTICO', 450.00, 18);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Cafeteira Nespresso Vertuo', 'Cápsulas variadas, preta', 'ELETRODOMESTICO', 600.00, 15);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Guarda-Roupa Casal MDF', '6 portas, 4 gavetas, cor branca', 'MOVEL', 1400.00, 3);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Poltrona de Amamentação', 'Tecido linho, cor bege', 'MOVEL', 750.00, 5);

INSERT INTO tb_produto (nome, descricao, tipo, valor_fornecedor, quantidade_estoque)
VALUES ('Escrivaninha Industrial', 'Estrutura metálica, tampo amadeirado', 'MOVEL', 350.00, 11);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (10, 'SAIDA', 2100.00, '2026-07-11', 2);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (11, 'SAIDA', 2300.00, '2026-07-12', 5);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (12, 'ENTRADA', 0.00, '2026-07-13', 5);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (13, 'SAIDA', 2600.00, '2026-07-14', 1);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (14, 'SAIDA', 1600.00, '2026-07-15', 2);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (15, 'SAIDA', 650.00, '2026-07-16', 4);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (16, 'ENTRADA', 0.00, '2026-07-17', 5);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (17, 'SAIDA', 1999.00, '2026-07-18', 1);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (18, 'SAIDA', 1100.00, '2026-07-19', 2);

INSERT INTO tb_movimento_estoque (produto_id, tipo, valor_venda, data_movimentacao, quantidade)
VALUES (19, 'ENTRADA', 0.00, '2026-07-20', 3);