package br.com.sgpr.teste.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgpr.teste.business.entity.Cidade;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.PassagemService;
import br.com.sgpr.teste.business.util.Mensagem;

@RestController
@RequestMapping(path="passagens")
public class PassagensViagemController {
    @Autowired
    private PassagemService passagemService;

    @GetMapping()
    public Iterable<VisaoPassagens> getPassagensViagem(@RequestParam String viagemId){
        System.out.println("Pegando as passagens da viagem " + viagemId + "...");
        return passagemService.getPassagensViagem(viagemId);
    }

    @GetMapping(path = "/{userId}")
    public Iterable<VisaoPassagens> getUserPass(@PathVariable("userId") String userId) {
        return passagemService.getUserPass(userId);
    }

    @DeleteMapping(path = "/{passId}")
    public Mensagem cancelarPassagem(@PathVariable("passId") String passId) {
        try {
            passagemService.cancelarPassagem(passId);
            return new Mensagem("Passagem Cancelada");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            msg.setErros(e.getListOfMenssagens());
            return msg;
        }
    }

    @PutMapping(path = "/validate")
    public Mensagem validadePass(@RequestBody VisaoPassagens pass) {
        try {
            passagemService.validetedPassagem(pass);
            return new Mensagem("Sucesso");
        } catch (Exception e) {
            Mensagem msg = new Mensagem("Error");
            msg.addErro(e.getMessage());
            return msg;
        }
    }
    @PostMapping()
    public Mensagem agendarPassagem(@RequestBody TempPassagem novaPassagem) {
        try {
        	passagemService.savePassagem(novaPassagem);
            return new Mensagem("Sucesso");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            msg.setErros(e.getListOfMenssagens());
            return msg;
        }
    }
}
