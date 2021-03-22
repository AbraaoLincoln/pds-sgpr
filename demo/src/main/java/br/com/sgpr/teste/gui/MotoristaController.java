package br.com.sgpr.teste.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgpr.teste.business.entity.Motorista;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.MotoristaService;
import br.com.sgpr.teste.business.util.Mensagem;

@RestController
@RequestMapping(path = "motorista")
public class MotoristaController {
	@Autowired
    private MotoristaService motoristaService;
	
	@GetMapping()
    public Iterable<Motorista> getMotoristas() {
        return motoristaService.getMotoristas();
    }
	
	@PostMapping()
    public Mensagem postMotorista(@RequestBody Motorista motorista){
        try {
            motoristaService.cadastrar(motorista);
            return new Mensagem("Sucesso");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            msg.setErros(e.getListOfMenssagens());
            return msg;
        }
    }
	
	@DeleteMapping(path = "/{motoristaCPF}")
    public Mensagem deleteMotorista(@PathVariable String motoristaCPF) {
		try {
			motoristaService.deleteMotorista(motoristaCPF);
            return new Mensagem("Sucesso");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            msg.setErros(e.getListOfMenssagens());
            return msg;
        }
    }
}