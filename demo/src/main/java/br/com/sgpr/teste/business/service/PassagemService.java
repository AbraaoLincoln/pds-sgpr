package br.com.sgpr.teste.business.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.business.service.interfaces.AfterCancelStrategy;
import br.com.sgpr.teste.business.service.interfaces.AfterCheckInStrategy;
import br.com.sgpr.teste.business.service.interfaces.AfterCreateStrategy;
import br.com.sgpr.teste.business.service.interfaces.CheckInStrategy;
import br.com.sgpr.teste.business.service.interfaces.PassValidateStrategy;
import br.com.sgpr.teste.business.service.interfaces.PrazoStrategy;
import br.com.sgpr.teste.data.PassagensViagemsRepository;
import br.com.sgpr.teste.data.TempPassagemRepository;
import br.com.sgpr.teste.data.ViagemRepository;

@Service
public class PassagemService {
    private static final Logger logger = LoggerFactory.getLogger(PassagemService.class);
    @Autowired
    private PassagensViagemsRepository passagensViagensRepository;
    @Autowired
    private TempPassagemRepository passagemRepository;
    @Autowired
    private ViagemRepository viagemRepository;
    //Strategy
    @Autowired
    private PrazoStrategy prazoValidator;
    @Autowired
    private AfterCancelStrategy afterCancel;
    @Autowired
    private CheckInStrategy checkInValidator;
    @Autowired
    private AfterCheckInStrategy afterCheckIn;
    @Autowired
    private AfterCreateStrategy afterCreate;
    @Autowired
    private PassValidateStrategy passValidator;
    

    public Iterable<VisaoPassagens> getPassagensViagem(String viagemId){
        return passagensViagensRepository.getPassagens(viagemId);
    }

    public Iterable<VisaoPassagens> getUserPass(String userId) {
        return passagensViagensRepository.getUserPass(userId);
    }
    
    public void criarPassagem(TempPassagem passToCreate) throws Exception{
    	logger.info("Criando nova passagem para a viagem " +  passToCreate.getViagem() + "...");
        Viagem viagem = viagemRepository.findById(passToCreate.getViagem()).orElseGet(() -> null);

        if(viagem != null) {
            passValidator.validade(passToCreate);
            passToCreate.setCodValidacao(generateCodValidacao(passToCreate));
            passagemRepository.save(passToCreate);
            viagemRepository.updateAssentosDisponiveis(viagem.getId(), viagem.getAsssentosDisponiveis() - 1);
            afterCreate.execute(passToCreate);
        }else{
            throw new Exception("Não foi possível criar a passagem.");
        }
    }

    private String generateCodValidacao(TempPassagem pass) {
        return pass.getCpf() + pass.getNumAssento();
    }

    public void cancelarPassagem(String passId) throws BusinessExceptions{
        ArrayList<String> listOfErros = new ArrayList<>();
        System.out.println("Cancelando a passagem " + passId);
        TempPassagem pass = passagemRepository.findById(passId).orElseGet(() -> null);
        
        if(pass != null) {
            prazoValidator.validate(pass);
            deletePassagemOnDB(pass.getCodValidacao(), pass.getViagem());
            afterCancel.execute(pass);
        }else {
            listOfErros.add("Passagem Invalida");
        }

        if(listOfErros.size() > 0) {
            throw new BusinessExceptions(listOfErros);
        }
    }
    
    private void deletePassagemOnDB(String passId, int viagemOfPassagemToDeleteId){
        Viagem viagem = viagemRepository.findById(viagemOfPassagemToDeleteId).orElseGet(() -> null);
        if(viagem.getAsssentosDisponiveis() > 0) {
            passagemRepository.deleteById(passId);
            viagemRepository.updateAssentosDisponiveis(viagem.getId(), viagem.getAsssentosDisponiveis() + 1);
        }else {
            //to do, criar exeção para esse caso.
            System.out.println("Viagem não tem passagens");
        }
    }

    public void checkIn(TempPassagem passToValidate) throws Exception{
        System.out.println("Validando a passagem de id " + passToValidate.getCodValidacao() + " da viagem " +  passToValidate.getViagem());
        TempPassagem pass = passagemRepository.findById(passToValidate.getCodValidacao()).orElseGet(() -> null);
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);

        if(pass == null || viagem.getId() != passToValidate.getViagem()) {
            throw new Exception("Passagem Inválida");
        }else{
            checkInValidator.validate(pass);
            passagemRepository.deleteById(passToValidate.getCodValidacao());
            afterCheckIn.execute(pass);
        }
    }

}
