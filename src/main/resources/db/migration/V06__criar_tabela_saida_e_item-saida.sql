CREATE TABLE saida (
    codigo_saida BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_criacao DATETIME NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    codigo_departamento BIGINT(20) NOT NULL,
    codigo_divisao BIGINT(20) NOT NULL,
    codigo_secao BIGINT(20) NOT NULL,
    codigo_funcionario BIGINT(20) NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_departamento) REFERENCES departamento(codigo_departamento),
    FOREIGN KEY (codigo_divisao) REFERENCES divisao(codigo_divisao),
    FOREIGN KEY (codigo_secao) REFERENCES secao(codigo_secao),
    FOREIGN KEY (codigo_funcionario) REFERENCES funcionario(codigo_funcionario),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_saida (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    codigo_produto BIGINT(20) NOT NULL,
    codigo_saida BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_produto) REFERENCES produto(codigo_produto),
    FOREIGN KEY (codigo_saida) REFERENCES saida(codigo_saida)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;