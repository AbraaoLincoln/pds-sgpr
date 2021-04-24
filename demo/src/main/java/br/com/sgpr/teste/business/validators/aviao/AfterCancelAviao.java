package br.com.sgpr.teste.business.validators.aviao;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.service.interfaces.AfterCancelStrategy;
import br.com.sgpr.teste.data.PassagemUsadaRepository;

// @Component
public class AfterCancelAviao implements AfterCancelStrategy{
    @Autowired
    private PassagemUsadaRepository passagemUsadaRepository;

    @Override
    public void execute(TempPassagem pass) {
        PassagemUsada pu = new PassagemUsada();
        pu.setCodValidacao(pass.getCodValidacao());
        pu.setViagem(pass.getViagem());
        LocalTime timeNow = LocalTime.now();
        pu.setHoraCancelamento(timeNow.getHour() + ":" + timeNow.getMinute());
        passagemUsadaRepository.save(pu);
    }
    
}
