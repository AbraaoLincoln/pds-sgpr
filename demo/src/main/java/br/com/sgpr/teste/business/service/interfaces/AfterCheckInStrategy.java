package br.com.sgpr.teste.business.service.interfaces;

import br.com.sgpr.teste.business.entity.TempPassagem;

public interface AfterCheckInStrategy {
    public void execute(TempPassagem pass);
}
