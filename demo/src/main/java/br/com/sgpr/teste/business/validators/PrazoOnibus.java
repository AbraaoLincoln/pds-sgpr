package br.com.sgpr.teste.business.validators;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.PrazoStrategy;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class PrazoOnibus implements PrazoStrategy{
    @Autowired
    private ViagemRepository viagemRepository;
    
    @Override
    public void validate(TempPassagem pass) throws BusinessExceptions {
        ArrayList<String> listOfErros = new ArrayList<>();
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();

        if(today.isBefore(viagemDate)) {
            String mouth = viagemDate.getMonthValue() > 9 ? "" + viagemDate.getMonthValue() : "0" + viagemDate.getMonthValue();
            LocalDate dayBeforeViagem = LocalDate.parse(viagemDate.getYear() + "-" + mouth + "-" + (viagemDate.getDayOfMonth() - 1));

            if(today.isEqual(dayBeforeViagem)) {
                LocalTime timeNow = LocalTime.now();
                LocalTime horaSiadaViagem = LocalTime.parse(viagem.getHoraSaida());
                
                if(timeNow.isAfter(horaSiadaViagem)) {
                    listOfErros.add("Passagem não pode ser cancelada, menos de 24h para a viagem.");   
                }
            }

        }else {
            if(today.isEqual(viagemDate)) {
                listOfErros.add("Passagem não pode ser cancelada, menos de 24h para a viagem.");
            }else {
                listOfErros.add("Passagem não pode ser cancelada.");
            }
        }

        if(listOfErros.size() > 0) {
            throw new BusinessExceptions(listOfErros);
        }
    }  
}
