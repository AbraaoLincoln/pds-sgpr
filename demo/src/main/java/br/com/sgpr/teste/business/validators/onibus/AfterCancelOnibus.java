package br.com.sgpr.teste.business.validators.onibus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCancelStrategy;
import br.com.sgpr.teste.data.PassagemUsadaRepository;

// @Component
public class AfterCancelOnibus implements AfterCancelStrategy{
    @Autowired
    private PassagemUsadaRepository passagemUsadaRepository;

    @Override
    public void execute(TempPassagem pass) {
        passagemUsadaRepository.save(new PassagemUsada(pass));
    }
    
}
