package br.com.sgpr.teste.business.validators.barco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.Passageiro;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCancelStrategy;
import br.com.sgpr.teste.business.util.SMSSender;
import br.com.sgpr.teste.data.PassageiroRepository;

@Component
public class AfterCancelBarco implements AfterCancelStrategy {
	@Autowired
    private PassageiroRepository passageiroRepository;
	@Autowired
    private SMSSender smsSender;
	
	@Override
	public void execute(TempPassagem pass) {
		Passageiro passageiro = passageiroRepository.findById(pass.getCpf()).orElseGet(() -> null);    
        
    	smsSender.setPhoneNumber(passageiro.getTelefone());
    	smsSender.setMessage("Ola " + passageiro.getNome() + " sua passagem  de código " + pass.getCodValidacao() + " foi cancelada com sucesso");
    	smsSender.send();
	}

}
