package br.com.sgpr.teste.data;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.sgpr.teste.business.entity.Passageiro;

public interface PassageiroRepository extends CrudRepository<Passageiro, Integer>{
	@Query(value = "select * from passageiro where cpf = :cpf", nativeQuery = true)
    public ArrayList<Passageiro> getPassageiroByCpf(@Param("cpf") String id);

}
