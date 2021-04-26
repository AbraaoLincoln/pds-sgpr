package br.com.sgpr.teste.business.validators.barco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.Passageiro;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCreateStrategy;
import br.com.sgpr.teste.business.util.SMSSender;
import br.com.sgpr.teste.data.PassageiroRepository;

@Component
public class AfterCreateBarco implements AfterCreateStrategy{
	@Autowired
    private PassageiroRepository passageiroRepository;
	@Autowired
    private SMSSender smsSender;
	
	@Override
	public void execute(TempPassagem pass) throws Exception {		
		Passageiro passageiro = passageiroRepository.findById(pass.getCpf()).orElseGet(() -> null);    
        
        if(passageiro == null || passageiro.getTelefone() == null) {
            throw new Exception("Passageiro não cadastrado ou telefone invalido");
        }else {
        	smsSender.setPhoneNumber(passageiro.getTelefone());
        	smsSender.setMessage("Ola " + passageiro.getNome() + " sua passagem foi gerada com sucesso");
        	smsSender.send();
        }	
	}
}
