package br.com.sgpr.teste.business.validators.aviao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCheckInStrategy;
import br.com.sgpr.teste.data.PassagemUsadaRepository;

//@Component
public class AfterCheckInAviao implements AfterCheckInStrategy{
    @Autowired
    private PassagemUsadaRepository passagemUsadaRepository;

    @Override
    public void execute(TempPassagem pass) {
        passagemUsadaRepository.save(new PassagemUsada(pass));   
    }
    
}
