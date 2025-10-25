-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS consultorio_medico;
USE consultorio_medico;

-- Tabela paciente
CREATE TABLE paciente (
    id_paciente VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela endereco
CREATE TABLE endereco (
    id_endereco VARCHAR(36) PRIMARY KEY,
    estado VARCHAR(255) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    paciente_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id_paciente) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela consulta
CREATE TABLE consulta (
    id_consulta VARCHAR(36) PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    status ENUM('Agendada', 'Confirmada', 'Cancelada', 'Reagendada') NOT NULL,
    paciente_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id_paciente) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela auditoria
CREATE TABLE auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    operacao ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    tabela VARCHAR(50) NOT NULL,
    registro_id VARCHAR(36),
    data_operacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(100) DEFAULT 'sistema'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Índices
CREATE INDEX idx_paciente_cpf ON paciente(cpf);
CREATE INDEX idx_consulta_data ON consulta(data_hora);
CREATE INDEX idx_consulta_status ON consulta(status);
CREATE INDEX idx_endereco_paciente ON endereco(paciente_id);
CREATE INDEX idx_consulta_paciente ON consulta(paciente_id);

-- Trigger audit INSERT paciente
DELIMITER $$
CREATE TRIGGER trigger_audit_paciente_insert
AFTER INSERT ON paciente
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('INSERT', 'paciente', NEW.id_paciente);
END$$
DELIMITER ;

-- Trigger audit UPDATE paciente
DELIMITER $$
CREATE TRIGGER trigger_audit_paciente_update
AFTER UPDATE ON paciente
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('UPDATE', 'paciente', NEW.id_paciente);
END$$
DELIMITER ;

-- Trigger audit DELETE paciente
DELIMITER $$
CREATE TRIGGER trigger_audit_paciente_delete
AFTER DELETE ON paciente
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('DELETE', 'paciente', OLD.id_paciente);
END$$
DELIMITER ;

-- Trigger audit INSERT consulta
DELIMITER $$
CREATE TRIGGER trigger_audit_consulta_insert
AFTER INSERT ON consulta
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('INSERT', 'consulta', NEW.id_consulta);
END$$
DELIMITER ;

-- Trigger audit UPDATE consulta
DELIMITER $$
CREATE TRIGGER trigger_audit_consulta_update
AFTER UPDATE ON consulta
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('UPDATE', 'consulta', NEW.id_consulta);
END$$
DELIMITER ;

-- Trigger audit DELETE consulta
DELIMITER $$
CREATE TRIGGER trigger_audit_consulta_delete
AFTER DELETE ON consulta
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (operacao, tabela, registro_id)
    VALUES ('DELETE', 'consulta', OLD.id_consulta);
END$$
DELIMITER ;

-- View agenda completa
CREATE VIEW view_agenda_completa AS
SELECT 
    c.id_consulta,
    c.data_hora,
    c.status,
    p.id_paciente,view_agenda_completa
    p.nome AS paciente_nome,
    p.cpf AS paciente_cpf,
    p.telefone AS paciente_telefone,
    e.cidade,
    e.estado
FROM consulta c
INNER JOIN paciente p ON c.paciente_id = p.id_paciente
LEFT JOIN endereco e ON e.paciente_id = p.id_paciente
ORDER BY c.data_hora ASC;
