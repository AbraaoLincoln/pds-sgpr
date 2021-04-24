package br.com.sgpr.teste.business.validators.aviao;

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
public class PassValidateAviao implements PassValidateStrategy{
    @Autowired
    private ViagemRepository viagemRepository;

    @Override
    public void validade(TempPassagem pass) throws BusinessExceptions {
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();

        if(today.equals(viagemDate)) {
            LocalTime viagemTime = LocalTime.parse(viagem.getHoraSaida());
            LocalTime timeNow = LocalTime.now();

            if(!isAtLeast3hoursBeforeViagemTime(timeNow, viagemTime)) throw new BusinessExceptions("A passagem só pode ser comprada em até 3 horas antes do voo.");
        }else if(today.isAfter(viagemDate)) {
            throw new BusinessExceptions("A passagem não pode ser criada, pois a viagem já aconteceu");
        }
    }

    private boolean isAtLeast3hoursBeforeViagemTime(LocalTime timeNow, LocalTime viagemTime) {
        int timeNowInMinutes = timeNow.getHour() * 60 + timeNow.getMinute();
        int viagemTimeInMinutes = viagemTime.getHour() * 60 + viagemTime.getMinute();

        return viagemTimeInMinutes - timeNowInMinutes >= 180;
    }
    
}
