create user 'springuser'@'%' identified by 'ThePassword321#';
grant all on sgpr.* to 'springuser'@'%';

select * from viagens;

select * from viagem;

-- delete from viagem;

select * from empresa;

select v.id_viagem as id, c.nome as Origem, c1.nome as Destino, v.data_viagem, v.hora_saida, v.preco, m.nome as motorista, v.motorista as cpf_motorista, o.num_placa as onibus, r.id_rota as rota, v.status_saida as estado, e.nome as empresa 
from viagem as v, motorista as m, onibus as o, rota as r, cidades as c, cidades as c1, empresa as e
where 
v.rota = r.id_rota and 
v.motorista = m.cpf and
v.onibus = o.num_placa and
r.origem = c.id_cidade and
r.destino = c1.id_cidade and
v.empresa = e.id_empresa;

select * from rota;

select * from rotas;

select * from rota_cidades;

select * from cidades;

select rc.id_rota, c.nome as nome_cidade, rc.num_seq from rota_cidades as rc, cidades as c where rc.id_rota = 'a-n' and rc.id_cidade = c.id_cidade;

select * from passagem;

select cod_validacao, data_validade, num_assento, cpf, nome from passagem as ps, passageiro as p where ps.cpf_dono = p.cpf;

delete from rota_cidades where id_rota =  't-5';

alter table rota_cidades drop column nome_cidade;

select id_viagem, cpf_dono, data_viagem, hora_saida, rota, onibus, hora_saida, num_assento, status_saida
from viagem as v, passagem as p 
where v.id_viagem = p.viagem;

update viagem set data_viagem = '2021-04-13' where id_viagem = 2;

update viagem set hora_saida = '15:00' where id_viagem = 2;

update viagem set assentos_disponiveis = 19 where id_viagem = 1;

insert into passagem values('1', 1, 5, '40811470784');

insert into passagem values('2', 2, 5, '40811470784');

select * from passagem where cod_validacao = '8';

select * from passagem_usadas;

select * from motorista;

select * from viagens_simples;

insert into passagem  values('7', 1, 5, '40811470784'),
('8',2, 5, '40811470784'), ('9',3, 10, '40811470784'), ('10',4, 5, '40811470784');
