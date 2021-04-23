package br.com.sgpr.teste.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import javax.annotation.PostConstruct;

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

import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.PassagemService;
import br.com.sgpr.teste.business.util.Mensagem;

@RestController
@RequestMapping(path="passagens")
public class PassagensController {
    private static final Logger logger = LoggerFactory.getLogger(PassagensController.class);
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

    @PostMapping()
    public Mensagem saveNewPass(@RequestBody TempPassagem pass) {
        logger.info("executando saveNewPass endpoint");
        try {
            passagemService.criarPassagem(pass);
            return new Mensagem("Passagem Salva");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            if(e.getListOfMenssagens() == null) {
                msg.addErro(e.getMessage());
            }else {
                msg.setErros(e.getListOfMenssagens());
            }
            return msg;
        } catch (Exception e) {
            Mensagem msg = new Mensagem("Error");
            msg.addErro(e.getMessage());
            return msg;
        }
    }

    @DeleteMapping(path = "/{passId}")
    public Mensagem cancelarPassagem(@PathVariable("passId") String passId) {
        try {
            passagemService.cancelarPassagem(passId);
            return new Mensagem("Passagem Cancelada");
        } catch (BusinessExceptions e) {
            Mensagem msg = new Mensagem("Error");
            if(e.getListOfMenssagens() == null) {
                msg.addErro(e.getMessage());
            }else {
                msg.setErros(e.getListOfMenssagens());
            }
            return msg;
        }
    }

    @PutMapping(path = "/validate")
    public Mensagem validadePass(@RequestBody TempPassagem pass) {
        try {
            passagemService.checkIn(pass);
            return new Mensagem("Sucesso");
        } catch (Exception e) {
            Mensagem msg = new Mensagem("Error");
            msg.addErro(e.getMessage());
            return msg;
        }
    }
}
