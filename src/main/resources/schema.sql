create table Cliente (
  id int primary key auto_increment,
  nome varchar (255) not null,
  tipo varchar(1),
  identificacao varchar(14) unique,
  rg_ie varchar (9),
  data_do_cadastro date,
  situacao varchar (255)
)

create table telefone (
  id int primary key auto_increment,
  telefone varchar (20),
  principal boolean,
  cliente_id int,
  foreign key (cliente_id) references cliente(id)
)
