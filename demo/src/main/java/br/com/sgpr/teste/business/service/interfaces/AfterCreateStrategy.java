package br.com.sgpr.teste.business.service.interfaces;

import br.com.sgpr.teste.business.entity.TempPassagem;

public interface AfterCreateStrategy {
	public void execute(TempPassagem pass);
}
