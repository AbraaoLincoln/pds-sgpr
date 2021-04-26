package br.com.sgpr.teste.business.validators.barco;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.PassValidateStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class PassValidateBarco implements PassValidateStrategy{
	@Autowired
    private ViagemRepository viagemRepository;
	
	@Override
	public void validate(TempPassagem pass) throws BusinessExceptions {
		Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
		
		if (viagem != null) {
			Integer num_assentos = viagem.getAsssentosDisponiveis();
			
			if (num_assentos == 0) {
				throw new BusinessExceptions("Todos os assentos para essa viagem já foram ocupados");
			}					
		}else {
			throw new BusinessExceptions("Viagem não encontrada");
		}	
		
		LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();

        if(today.equals(viagemDate)) {
            LocalTime viagemTime = LocalTime.parse(viagem.getHoraSaida());
            LocalTime timeNow = LocalTime.now();

            if(!isAtLeast1hourBeforeViagemTime(timeNow, viagemTime)) throw new BusinessExceptions("A passagem só pode ser comprada em até 1 hora antes da viagem.");
        }else if(today.isAfter(viagemDate)) {
            throw new BusinessExceptions("A passagem não pode ser criada, pois a viagem já aconteceu");
        }
		
	}
	
	private boolean isAtLeast1hourBeforeViagemTime(LocalTime timeNow, LocalTime viagemTime) {
        int timeNowInMinutes = timeNow.getHour() * 60 + timeNow.getMinute();
        int viagemTimeInMinutes = viagemTime.getHour() * 60 + viagemTime.getMinute();

        return viagemTimeInMinutes - timeNowInMinutes >= 60;
    }

}
