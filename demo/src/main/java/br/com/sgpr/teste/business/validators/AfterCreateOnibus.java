package br.com.sgpr.teste.business.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCreateStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class AfterCreateOnibus implements AfterCreateStrategy{
	@Autowired
    private ViagemRepository viagemRepository;
	
	@Override
	public void execute(TempPassagem pass) {
		Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
		Integer num_assentos = viagem.getAsssentosDisponiveis();
		
		viagem.setAsssentosDisponiveis(num_assentos - 1);
		
		viagemRepository.save(viagem);
	}

}
