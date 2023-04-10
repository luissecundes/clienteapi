CREATE TABLE cliente (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  tipo VARCHAR(10),
  cpf_cnpj VARCHAR(14) UNIQUE,
  rg_ie VARCHAR(20),
  data_do_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  situacao VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE telefone (
  id SERIAL PRIMARY KEY,
  telefone VARCHAR(20),
  principal BOOLEAN,
  cliente_id INTEGER REFERENCES cliente(id)
);