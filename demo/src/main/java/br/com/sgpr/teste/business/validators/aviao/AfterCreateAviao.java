package br.com.sgpr.teste.business.validators.aviao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.Passageiro;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCreateStrategy;
import br.com.sgpr.teste.business.util.EmailSender;
import br.com.sgpr.teste.data.PassageiroRepository;

//@Component
public class AfterCreateAviao implements AfterCreateStrategy{
    @Autowired
    private PassageiroRepository passageiroRepository;
    @Autowired
    private EmailSender emailSender;

    @Override
    public void execute(TempPassagem pass) throws Exception {
        Passageiro passageiro = passageiroRepository.findById(pass.getCpf()).orElseGet(() -> null);    
        
        if(passageiro == null || passageiro.getEmail() == null) {
            throw new Exception("Passageiro não cadastrado ou email invalido");
        }else {
            emailSender.setRecipient(passageiro.getEmail());
            emailSender.setContent("Ola " + passageiro.getNome() + " sua passagem foi gerada com sucesso");
            emailSender.send();
        }
    }
    
}
