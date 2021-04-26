package br.com.sgpr.teste.business.validators.barco;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.PrazoStrategy;
import br.com.sgpr.teste.data.PassagemUsadaRepository;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class PrazoValidatorBarco implements PrazoStrategy{
	@Autowired
    private ViagemRepository viagemRepository;
	@Autowired
    private PassagemUsadaRepository passagemUsadaRepository;
	
	@Override
	public void validate(TempPassagem pass) throws BusinessExceptions {
		Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();
        String msgForaDoPrazo = "A passagem não pode ser cancelada, o prazo é de 2 dias antes da saida do voo.";
        
//        if (hasCancelledPassageIn30Days(pass.getCpf())) throw new BusinessExceptions("Já existe uma passagem cancelada nos últimos 30 dias.");     
        
        if(today.isBefore(viagemDate)) {
            LocalDate ThreeDaysBeforeViagemDate = viagemDate.minusDays(2);

            if(today.isEqual(ThreeDaysBeforeViagemDate)) {
                LocalTime timeVigem = LocalTime.parse(viagem.getHoraSaida());
                LocalTime timeNow = LocalTime.now();
    
                if(timeNow.isAfter(timeVigem)) throw new BusinessExceptions(msgForaDoPrazo);

            }else if(!today.isBefore(ThreeDaysBeforeViagemDate)) throw new BusinessExceptions(msgForaDoPrazo);
        }else {
            throw new BusinessExceptions(msgForaDoPrazo);
        }
	}
	
//	public Boolean hasCancelledPassageIn30Days(String cpfCliente){
//		List<PassagemUsada> passagens = passagemUsadaRepository.getUserPass(cpfCliente);
//		
//	}

}
