create database sgpr;
use sgpr;

create table passageiro(
	cpf char(11), 
    nome varchar(200) not null,
    telefone varchar(15),
    email varchar(100),
    
    primary key(cpf)
);

create table motorista(
	cpf char(11), 
    nome varchar(200) not null,
    telefone varchar(15) not null,
    email varchar(100) not null,
    
    primary key(cpf)
);

create table onibus(
	num_placa char(7),
    num_assentos int not null,
    tipo varchar(45),
    
    primary key(num_placa)
);

create table cidades(
	id_cidade int auto_increment,
    nome varchar(100),
    
    primary key(id_cidade)
);

create table empresa(
	id_empresa int auto_increment,
    nome varchar(45),
    
    primary key(id_empresa)
);
/*As tabelas a partir daqui fazem referencia as que estão acima.*/ 

create table rota(
	id_rota varchar(10),
    nome_rota varchar(45),
    origem int not null,
    destino int not null,
    
    primary key(id_rota),
    
    foreign key(origem)
		references cidades(id_cidade)
	on update cascade
    on delete cascade,
    
    foreign key(destino)
		references cidades(id_cidade)
	on update cascade
    on delete cascade
);

create table rota_cidades(
	id_rota varchar(10),
	id_cidade int,
    num_seq int not null,
    
    primary key(id_rota, id_cidade),
    
    foreign key(id_rota)
		references rota(id_rota)
	on update cascade
    on delete cascade,
    
    foreign key(id_cidade)
		references cidades(id_cidade)
	on update cascade
    on delete cascade
);

create table viagem(
	id_viagem int auto_increment,
    data_viagem date,
    status_saida varchar(45),
    hora_saida varchar(5),
    preco decimal(5,2),
    rota varchar(10),
    motorista varchar(11),
    onibus varchar(7),
    empresa int,
    assentos_disponiveis int,
    
    primary key(id_viagem),
    
    foreign key(rota)
		references rota(id_rota)
	on update cascade
    on delete cascade,
    
    foreign key(motorista)
		references motorista(cpf)
	on update cascade
    on delete cascade,
    
    foreign key(onibus)
		references onibus(num_placa)
	on update cascade
    on delete cascade,
    
    foreign key(empresa)
		references empresa(id_empresa)
	on update cascade
    on delete cascade
);

create table passagem(
	cod_validacao varchar(45),
    viagem int not null,
    num_assento int not null,
    cpf_dono varchar(11) not null,
    
    primary key(cod_validacao),
    
    foreign key(cpf_dono)
		references passageiro(cpf)
	on update cascade
    on delete cascade,
    
    foreign key(viagem)
		references viagem(id_viagem)
	on update cascade
    on delete cascade
);

create table passagem_usadas(
	cod_validacao varchar(45),
    viagem int,
    num_assento int,
    cpf_dono varchar(11),
    hora_cancelamento char(5),
    
    primary key(cod_validacao),
    
    foreign key(cpf_dono)
		references passageiro(cpf)
	on update cascade
    on delete cascade,
    
    foreign key(viagem)
		references viagem(id_viagem)
	on update cascade
    on delete cascade
);

create view viagens as
select v.id_viagem as id, c.nome as Origem, c1.nome as Destino, v.data_viagem, v.hora_saida, v.preco, m.nome as motorista, o.num_placa as onibus, r.id_rota as rota, v.status_saida as estado, e.nome as empresa, v.assentos_disponiveis 
from viagem as v, motorista as m, onibus as o, rota as r, cidades as c, cidades as c1, empresa as e
where 
v.rota = r.id_rota and 
v.motorista = m.cpf and
v.onibus = o.num_placa and
r.origem = c.id_cidade and
r.destino = c1.id_cidade and
v.empresa = e.id_empresa;

-- drop view viagens;

create view rotas as
select rota.id_rota, rota.nome_rota, c1.nome as origem, c2.nome as destino
from rota, cidades as c1, cidades as c2 
where rota.origem = c1.id_cidade and rota.destino = c2.id_cidade;

-- drop view rotas;

create view viagens_simples as
select v.id_viagem as id, c.nome as Origem, c1.nome as Destino, v.data_viagem, v.hora_saida, v.preco, v.motorista, v.onibus as onibus, r.id_rota as rota, v.status_saida as estado, e.nome as empresa, v.assentos_disponiveis 
from viagem as v, rota as r, cidades as c, cidades as c1, empresa as e
where 
v.rota = r.id_rota and 
r.origem = c.id_cidade and
r.destino = c1.id_cidade and
v.empresa = e.id_empresa;

-- drop view viagens_simples;

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
  login varchar(100) NOT NULL,
  nome_completo varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,
  PRIMARY KEY (login)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;