package br.com.sgpr.teste.business.validators.onibus;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.CheckInStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

// @Component
public class CheckInOnibus implements CheckInStrategy{
    @Autowired
    private ViagemRepository viagemRepository;

    @Override
    public void validate(TempPassagem pass) throws BusinessExceptions{
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();
        
        if(!today.equals(viagemDate)) {
            throw new BusinessExceptions("A passagem só pode ser validada no dia da viagem"); 
        }
    }
    
}
