package br.com.sgpr.teste.business.validators.barco;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.CheckInStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class CheckInBarco implements CheckInStrategy{
	@Autowired
    private ViagemRepository viagemRepository;
	
	@Override
	public void validate(TempPassagem pass) throws BusinessExceptions {
		   Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
	        LocalTime viagemTime = LocalTime.parse(viagem.getHoraSaida());
	        LocalTime timeNow = LocalTime.now();
	        LocalDate viagemDate = LocalDate.parse(viagem.getData());
	        LocalDate today = LocalDate.now();
	        
	        if(!today.isEqual(viagemDate)) throw new BusinessExceptions("A passagem só pode ser validada no dia da viagem.");

	        if(today.isEqual(viagemDate) || !isOneHourAndHalfBeforeOrLess(timeNow, viagemTime)) throw new BusinessExceptions("A passagem só pode ser validada no máximo com 1:30h (uma hora e meia) até a hora da viagem");		
	}
	
	private boolean isOneHourAndHalfBeforeOrLess(LocalTime startTime, LocalTime finalTime) {
        int startM = startTime.getHour() * 60 + startTime.getMinute();
        int finalM = finalTime.getHour() * 60 + finalTime.getMinute();
        int interval = finalM - startM;
        if(interval > 0 && interval <= 90) return true;

        return false;
    }
}
