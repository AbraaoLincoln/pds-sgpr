package br.com.sgpr.teste.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;

public interface PassagemUsadaRepository extends CrudRepository<PassagemUsada, String>{
	@Query(value = "select cod_validacao, viagem, num_assento, cpf, nome from passagem as ps, passageiro as p where ps.cpf_dono = :userId and ps.cpf_dono = p.cpf;", nativeQuery = true)
    public Iterable<VisaoPassagens> getUserPass(@Param("userId") String userId);
}
