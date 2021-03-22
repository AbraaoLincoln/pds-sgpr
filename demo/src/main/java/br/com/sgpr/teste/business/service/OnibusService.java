package br.com.sgpr.teste.business.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgpr.teste.business.entity.Onibus;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.data.OnibusRepository;

@Service
public class OnibusService {
	@Autowired
	private OnibusRepository onibusRepository;

	public void cadastrar(Onibus onibus) throws BusinessExceptions{
		System.out.println("Cadastrando ônibus...");
		validate(onibus);
		onibusRepository.save(onibus);
	}

	private void validate(Onibus onibus) throws BusinessExceptions {
		ArrayList<String> listOfInvalidFeild = new ArrayList<>();
		
		if (onibus.getPlaca() == null) {
			listOfInvalidFeild.add("Placa vazia");
		}
		
		if (onibusRepository.findById(onibus.getPlaca() ) == null) {
			listOfInvalidFeild.add("Já existe um ônibus com essa placa");
		}
		
		if (onibus.getNumAssentos() <= 0 ) {
			listOfInvalidFeild.add("Quantidade de assentos insuficientes");
		}
		
		if (onibus.getTipo() == null ) {
			listOfInvalidFeild.add("Tipo de veículo inválido");
		}
		
		if (listOfInvalidFeild.size() > 0) {
			throw new BusinessExceptions(listOfInvalidFeild);
		}
	}

	public Iterable<Onibus> getOnibus() {
		return onibusRepository.findAll();
	}

	public String deleteOnibus(String numPlaca) {
		onibusRepository.deleteById(numPlaca);
		return "Onibus deletado";
	}
}
