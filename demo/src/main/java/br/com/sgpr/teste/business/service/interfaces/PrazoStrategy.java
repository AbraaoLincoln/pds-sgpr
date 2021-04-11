package br.com.sgpr.teste.business.service.interfaces;

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;

public interface PrazoStrategy {
    public void validate(TempPassagem pass) throws BusinessExceptions;
}
