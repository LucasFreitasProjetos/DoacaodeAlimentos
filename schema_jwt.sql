-- Tabela de usuários para autenticação JWT
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir usuários de teste (usar senha em produção com hash bcrypt)
INSERT INTO usuarios (email, senha) VALUES 
('teste@exemplo.com', '123456'),
('admin@exemplo.com', 'senha123');
