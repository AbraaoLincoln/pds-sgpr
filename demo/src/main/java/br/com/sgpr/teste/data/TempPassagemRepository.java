package br.com.sgpr.teste.data;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.sgpr.teste.business.entity.Rota;
import br.com.sgpr.teste.business.entity.TempPassagem;

public interface TempPassagemRepository extends CrudRepository<TempPassagem, String> {
	@Query(value = "SELECT count(*) as qtd FROM `passagem` WHERE `viagem`=:viagem", nativeQuery = true)
	long getQtdPassagensCompradasPorViagem(@Param("viagem") int id);
    
}
