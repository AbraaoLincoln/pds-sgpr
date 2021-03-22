package br.com.sgpr.teste.data;

import org.springframework.data.repository.CrudRepository;

import br.com.sgpr.teste.business.entity.Motorista;

public interface MotoristaRepository extends CrudRepository<Motorista, String>{

}
