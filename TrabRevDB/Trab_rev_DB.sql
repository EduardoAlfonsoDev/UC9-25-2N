CREATE TABLE Funcionarios (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100),
	cargo VARCHAR(80),
	salario NUMERIC(10,2),
	idade INTEGER,
	cidade VARCHAR(80),
	ativo BOOLEAN
)

CREATE TABLE Produtos (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100),
	categoria VARCHAR(80),
	preco NUMERIC(10,2),
	estoque INTEGER
)

CREATE TABLE Pedidos (
	id SERIAL PRIMARY KEY,
	cliente VARCHAR(100),
	produto VARCHAR(100),
	quantidade INTEGER,
	valor_total NUMERIC(10,2),
	data_pedido DATE
)

INSERT INTO Funcionarios (nome, cargo, salario, idade, cidade, ativo)
VALUES
('João Silva', 'Analista de Sistemas', 5500.00, 29, 'São Paulo', TRUE),
('Maria Oliveira', 'Assistente Administrativo', 3200.00, 25, 'Campinas', TRUE),
('Carlos Souza', 'Desenvolvedor', 6800.00, 31, 'Belo Horizonte', TRUE),
('Ana Santos', 'Gerente de RH', 8500.00, 40, 'Rio de Janeiro', TRUE),
('Pedro Lima', 'Contador', 4900.00, 35, 'Curitiba', FALSE),
('Juliana Costa', 'Analista Financeiro', 4700.00, 28, 'Porto Alegre', TRUE),
('Lucas Ferreira', 'Técnico de Suporte', 3100.00, 24, 'Salvador', TRUE),
('Fernanda Almeida', 'Coordenadora de Marketing', 7200.00, 37, 'Recife', TRUE),
('Ricardo Gomes', 'Vendedor', 3800.00, 30, 'Fortaleza', FALSE),
('Patrícia Rocha', 'Recepcionista', 2500.00, 22, 'Goiânia', TRUE);

INSERT INTO Produtos (nome, categoria, preco, estoque)
VALUES
('Notebook Dell Inspiron', 'Informática', 3899.90, 15),
('Mouse Logitech M170', 'Periféricos', 79.90, 120),
('Teclado Mecânico Redragon', 'Periféricos', 249.90, 45),
('Monitor LG 24"', 'Monitores', 899.90, 30),
('Smartphone Samsung Galaxy A55', 'Celulares', 2199.90, 25),
('Fone de Ouvido JBL Tune 520BT', 'Áudio', 279.90, 60),
('Impressora HP LaserJet', 'Impressoras', 1299.90, 12),
('HD Externo Seagate 1TB', 'Armazenamento', 399.90, 40),
('Webcam Logitech C920', 'Acessórios', 449.90, 20),
('Cadeira Gamer ThunderX3', 'Móveis', 1199.90, 10);

INSERT INTO Pedidos (cliente, produto, quantidade, valor_total, data_pedido)
VALUES
('Gabriel Martins', 'Notebook Dell Inspiron', 1, 3899.90, '2026-06-10'),
('Beatriz Ferreira', 'Mouse Logitech M170', 3, 239.70, '2026-06-12'),
('Rafael Almeida', 'Monitor LG 24"', 2, 1799.80, '2026-06-14'),
('Camila Barbosa', 'Smartphone Samsung Galaxy A55', 1, 2199.90, '2026-06-16'),
('Thiago Ribeiro', 'Fone de Ouvido JBL Tune 520BT', 2, 559.80, '2026-06-18'),
('Larissa Mendes', 'HD Externo Seagate 1TB', 1, 399.90, '2026-06-20'),
('Bruno Carvalho', 'Webcam Logitech C920', 4, 1799.60, '2026-06-22'),
('Aline Moreira', 'Teclado Mecânico Redragon', 1, 249.90, '2026-06-24'),
('Eduardo Nunes', 'Impressora HP LaserJet', 2, 2599.80, '2026-06-26'),
('Natália Pereira', 'Cadeira Gamer ThunderX3', 1, 1199.90, '2026-06-28');

SELECT *
FROM funcionarios;

UPDATE Funcionarios
SET cidade = 'Canoas'
WHERE id = 8;

SELECT *
FROM Produtos;

SELECT *
FROM Pedidos;

SELECT nome
FROM Funcionarios;

SELECT nome, cargo
FROM Funcionarios;

SELECT *
FROM Funcionarios
WHERE salario > 3000.00;

SELECT *
FROM Funcionarios
WHERE idade < 30;

SELECT *
FROM Funcionarios
WHERE ativo = TRUE;

SELECT *
FROM Funcionarios
WHERE ativo = FALSE;

SELECT *
FROM Produtos
WHERE preco > 500.00;

SELECT *
FROM Produtos
WHERE preco < 100.00;

SELECT *
FROM Produtos
WHERE preco > 500.00;

SELECT *
FROM Produtos
WHERE estoque > 20;

SELECT *
FROM Pedidos
WHERE valor_total > 500.00;

SELECT *
FROM Pedidos
WHERE valor_total BETWEEN 200.00 AND 800.00;

SELECT *
FROM Funcionarios
WHERE cidade = 'Porto Alegre';

SELECT *
FROM Funcionarios
WHERE cidade IN 
('Porto Alegre', 'Canoas');

SELECT *
FROM Funcionarios
WHERE idade BETWEEN 20 AND 40;

SELECT *
FROM Produtos
WHERE nome LIKE 'M%';

-- não encontrei produtos com modelos terminados com a letra A
SELECT *
FROM Produtos
WHERE nome LIKE '%A';

SELECT *
FROM Produtos
WHERE nome LIKE '%Mouse%';

SELECT *
FROM Funcionarios
WHERE idade > 25
AND ativo = TRUE;

UPDATE Funcionarios
SET cidade = 'Novo Hamburgo'
WHERE id = 4;

SELECT *
FROM Funcionarios
WHERE cidade = 'Canoas'
OR cidade = 'Novo Hamburgo';

SELECT *
FROM Funcionarios
WHERE NOT ativo;

SELECT *
FROM Produtos
ORDER BY preco ASC;

SELECT *
FROM Produtos
ORDER BY preco DESC;

SELECT *
FROM Funcionarios
ORDER BY nome ASC;

SELECT *
FROM Produtos
LIMIT 5;

SELECT *
FROM Funcionarios
LIMIT 3;

SELECT *
FROM Produtos
WHERE categoria IN ('Informática', 'Escritório');

SELECT *
FROM Pedidos
WHERE data_pedido BETWEEN '2026-06-01' AND '2026-06-30';

