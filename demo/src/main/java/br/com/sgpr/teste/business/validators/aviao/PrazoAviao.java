package br.com.sgpr.teste.business.validators.aviao;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.PrazoStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class PrazoAviao implements PrazoStrategy{
    @Autowired
    private ViagemRepository viagemRepository;
    
    @Override
    public void validate(TempPassagem pass) throws BusinessExceptions {
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();
        String msgForaDoPrazo = "A passagem não pode ser cancelada, o prazo é de 3 dias antes da saida do voo.";

        if(today.isBefore(viagemDate)) {
            LocalDate ThreeDaysBeforeViagemDate = viagemDate.minusDays(3);

            if(today.isEqual(ThreeDaysBeforeViagemDate)) {
                LocalTime timeVigem = LocalTime.parse(viagem.getHoraSaida());
                LocalTime timeNow = LocalTime.now();
    
                if(timeNow.isAfter(timeVigem)) throw new BusinessExceptions(msgForaDoPrazo);

            }else if(!today.isBefore(ThreeDaysBeforeViagemDate)) throw new BusinessExceptions(msgForaDoPrazo);
        }else {
            throw new BusinessExceptions(msgForaDoPrazo);
        }
    }
    
}
