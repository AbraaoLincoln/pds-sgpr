package br.com.sgpr.teste.business.validators;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.PassValidatorCreateStrategy;
import br.com.sgpr.teste.data.PassagensViagemsRepository;
import br.com.sgpr.teste.data.ViagemRepository;

@Component
public class PassValidatorCreateOnibus implements PassValidatorCreateStrategy{
	@Autowired
    private ViagemRepository viagemRepository;
	@Autowired
	private PassagensViagemsRepository passagensViagemRepository;
	
	@Override
	public void validade(TempPassagem pass) throws BusinessExceptions {
		Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
        LocalDate viagemDate = LocalDate.parse(viagem.getData());
        LocalDate today = LocalDate.now();
        LocalTime viagemTime = LocalTime.parse(viagem.getHoraSaida());
        LocalTime now = LocalTime.now();
        Integer idViagem = pass.getViagem();
        
        if(today.isAfter(viagemDate) || (today.isEqual(viagemDate) && now.isAfter(viagemTime))) {
        	throw new BusinessExceptions("Não é possível gerar uma passagem para uma viagem que já ocorreu");
        }
        
        if (pass.getCodValidacao() == null) {
        	throw new BusinessExceptions("O nome do passageiro não foi fornecido");
        }
        
        if (pass.getCpf() == null) {
        	throw new BusinessExceptions("O cpf do passageiro não foi fornecido");
        }
        
        Iterable<VisaoPassagens> passagens = passagensViagemRepository.getPassagens(idViagem.toString());
        for (VisaoPassagens visaoPassagens : passagens) {
        	if(visaoPassagens.getNumAssento() == pass.getNumAssento()) {
        		throw new BusinessExceptions("O assento escolhido já está ocupado");
        	}
		}
        
        Integer num_assentos = viagem.getAsssentosDisponiveis();
        if(num_assentos <= 0) {
        	throw new BusinessExceptions("Não existe assento disponível para essa viagem");
        }

	}

}
