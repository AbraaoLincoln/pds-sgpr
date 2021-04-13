package br.com.sgpr.teste.business.validators;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.CheckInStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

// @Component
public class CheckInAviao implements CheckInStrategy{
    @Autowired
    private ViagemRepository viagemRepository;
    
    @Override
    public void validade(TempPassagem pass) throws BusinessExceptions {
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalTime viagemTime = LocalTime.parse(viagem.getHoraSaida());
        LocalTime timeNow = LocalTime.now();
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();
        
        if( !today.isEqual(viagemDate) || !timeNow.isBefore(viagemTime)) throw new BusinessExceptions("A passagem não pode ser validada, a viagem não é hoje ou a hora da validação é invalida");
        
        if(!isOneHourBeforeOrLess(timeNow, viagemTime)) throw new BusinessExceptions("A passagem não pode ser validada neste momentos, apenas faltando uma 1 hora ou menos");
    }
    
    private boolean isOneHourBeforeOrLess(LocalTime startTime, LocalTime finalTime) {
        int startM = startTime.getHour() * 60 + startTime.getMinute();
        int finalM = finalTime.getHour() * 60 + finalTime.getMinute();
        int interval = finalM - startM;
        if(interval > 0 && interval <= 60) return true;

        return false;
    }
}
