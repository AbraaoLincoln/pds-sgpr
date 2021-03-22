package br.com.sgpr.teste.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgpr.teste.business.entity.Onibus;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.OnibusService;
import br.com.sgpr.teste.business.util.Mensagem;

@RestController
@RequestMapping(path = "onibus")
public class OnibusController {
	@Autowired
	private OnibusService onibusService;
	
	@GetMapping()
	public Iterable<Onibus> getAllOnibus() {
        return onibusService.getOnibus();
    }
	
	@PostMapping()
    public Mensagem postOnibus(@RequestBody Onibus onibus){
        try {
        	onibusService.cadastrar(onibus);
            return new Mensagem("Sucesso");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            msg.setErros(e.getListOfMenssagens());
            return msg;
        }
    }
	
	@DeleteMapping(path = "/{numPlaca}")
    public String deleteOnibus(@PathVariable String numPlaca) {
        return onibusService.deleteOnibus(numPlaca);
    }
}
