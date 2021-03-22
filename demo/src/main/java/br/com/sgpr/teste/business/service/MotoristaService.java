package br.com.sgpr.teste.business.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgpr.teste.business.entity.Motorista;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.data.MotoristaRepository;

@Service
public class MotoristaService {
	@Autowired
	private MotoristaRepository motoristaRepository;

	public String cadastrar(Motorista motorista) throws BusinessExceptions{
		System.out.println("Cadastrando motorista...");
		validateMotorista(motorista);
		motoristaRepository.save(motorista);
		return "Motorista cadastrado";
	}

	private void validateMotorista(Motorista motorista) throws BusinessExceptions{
		ArrayList<String> listOfInvalidFeilds = new ArrayList<>();
		
		if (motoristaRepository.findById(motorista.getCpf()) == null ) {
			listOfInvalidFeilds.add("Já existe um motorista cadastrado com esse cpf.");
		}
		
		if (motorista.getCpf() == null) {
			listOfInvalidFeilds.add("CPF inválido.");
		}
		
		if (motorista.getNome() == null) {
			listOfInvalidFeilds.add("Nome inválido.");
		}
		
		if (motorista.getEmail() == null) {
			listOfInvalidFeilds.add("Email inválido.");
		}
		
		if(listOfInvalidFeilds.size() > 0) {
            throw new BusinessExceptions(listOfInvalidFeilds);
        }
	}

	public Iterable<Motorista> getMotoristas() {
		return motoristaRepository.findAll();
	}

	public void deleteMotorista(String cpfMotorista) throws BusinessExceptions{
		ArrayList<String> listOfInvalidFeild = new ArrayList<>();
		
		Optional<Motorista> motorista = motoristaRepository.findById(cpfMotorista);
		
		if (motorista.get().getCpf() == cpfMotorista) {
			motoristaRepository.deleteById(cpfMotorista);
			System.out.println("Motorista deletado");
		}else {
			listOfInvalidFeild.add("Não foi possível excluir o motorista.");
		}
		
		if (listOfInvalidFeild.size() > 0) {
			throw new BusinessExceptions(listOfInvalidFeild);
		}
	}
}