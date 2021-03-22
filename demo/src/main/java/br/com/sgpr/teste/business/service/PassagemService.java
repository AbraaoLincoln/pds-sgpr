package br.com.sgpr.teste.business.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgpr.teste.business.entity.Cidade;
import br.com.sgpr.teste.business.entity.Passageiro;
import br.com.sgpr.teste.business.entity.PassagemUsada;
import br.com.sgpr.teste.business.entity.Rota;
import br.com.sgpr.teste.business.entity.visoes.VisaoPassagens;
import br.com.sgpr.teste.business.entity.TempPassagem;
import br.com.sgpr.teste.business.entity.Viagem;
import br.com.sgpr.teste.business.exceptions.BusinessExceptions;
import br.com.sgpr.teste.data.PassageiroRepository;
import br.com.sgpr.teste.data.PassagemUsadaRepository;
import br.com.sgpr.teste.data.PassagensViagemsRepository;
import br.com.sgpr.teste.data.TempPassagemRepository;
import br.com.sgpr.teste.data.ViagemRepository;

@Service
public class PassagemService {
    @Autowired
    private PassagensViagemsRepository passagensViagensRepository;
    @Autowired
    private TempPassagemRepository passagemRepository;
    @Autowired
    private PassagemUsadaRepository passagemUsadaRepository;
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private PassageiroRepository passageiroRepository;

    public Iterable<VisaoPassagens> getPassagensViagem(String viagemId){
        return passagensViagensRepository.getPassagens(viagemId);
    }

    public Iterable<VisaoPassagens> getUserPass(String userId) {
        return passagensViagensRepository.getUserPass(userId);
    }

    public void cancelarPassagem(String passId) throws BusinessExceptions{
        ArrayList<String> listOfErros = new ArrayList<>();
        System.out.println("Cancelando a passagem " + passId);
        TempPassagem pass = passagemRepository.findById(passId).orElseGet(() -> null);
        
        if(pass != null) {
            Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);
            LocalDate viagemDate = LocalDate.parse(viagem.getData());
            LocalDate today = LocalDate.now();

            
            if(today.isBefore(viagemDate)) {
                String mouth = viagemDate.getMonthValue() > 9 ? "" + viagemDate.getMonthValue() : "0" + viagemDate.getMonthValue();
                LocalDate dayBeforeViagem = LocalDate.parse(viagemDate.getYear() + "-" + mouth + "-" + (viagemDate.getDayOfMonth() - 1));

                if(today.isEqual(dayBeforeViagem)) {
                    LocalTime timeNow = LocalTime.now();
                    LocalTime horaSiadaViagem = LocalTime.parse(viagem.getHoraSaida());

                    int timeNowInt = (timeNow.getHour() * 100) + timeNow.getMinute();
                    int horaSiadaViagemInt = (horaSiadaViagem.getHour() * 100) + horaSiadaViagem.getMinute();
                    if((timeNowInt - horaSiadaViagemInt) <= 0) {
                        //pode cancelar a viagem
                        deletePassagemOnDB(passId, viagem.getId());
                    }else {
                        listOfErros.add("Passagem não pode ser cancelada, menos de 24h para a viagem.");
                    }
                }else {
                    //pode cancelar a viagem
                    deletePassagemOnDB(passId, viagem.getId());
                }

            }else {
                if(today.isEqual(viagemDate)) {
                    listOfErros.add("Passagem não pode ser cancelada, menos de 24h para a viagem.");
                }else {
                    listOfErros.add("Passagem não pode ser cancelada.");
                }
            }
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

    public void validetedPassagem(VisaoPassagens passToValidate) throws Exception{
        System.out.println("Validando a passagem de id " + passToValidate.getCodValidacao() + " da viagem " +  passToValidate.getViagem());
        TempPassagem pass = passagemRepository.findById(passToValidate.getCodValidacao()).orElseGet(() -> null);
        Viagem viagem = viagemRepository.findById(pass.getViagem()).orElseGet(() -> null);

        if(pass == null || viagem.getId() != passToValidate.getViagem()) {
            throw new Exception("Passagem Inválida");
        }else{
            PassagemUsada oldPass = new PassagemUsada(pass);
            passagemRepository.deleteById(pass.getCodValidacao());
            passagemUsadaRepository.save(oldPass);
        }
    }
    public void savePassagem(TempPassagem passagem) throws BusinessExceptions{
        System.out.println("Salvando nova passagem CPF, " + passagem.getCpf());
    	
        ArrayList<String> listOfErros = new ArrayList<>();
        
        Optional<Viagem> viagem = viagemRepository.findById(passagem.getViagem());
        
        //verificando se o id da viagem existe na tabela viagem
        if(!viagem.isPresent()) {
        	System.out.println("Essa viagem não existe");
        	listOfErros.add("Essa viagem não existe");
        }
        ArrayList<Passageiro> passageiro = passageiroRepository.getPassageiroByCpf(passagem.getCpf());
        //verificando se o cpf da viagem existe na tabela passageiro
        if(passageiro.size()==0) {
        	System.out.println("O passageiro não existe");
        	listOfErros.add("O passageiro não existe");
        }
        //Obtendo a quantiade de passagem já cadastrada referente a um viagem na tabela passagem
        Long qtdAssentos = passagemRepository.getQtdPassagensCompradasPorViagem(passagem.getViagem());
        System.out.println(qtdAssentos);
        
        int qtd = qtdAssentos.intValue();
        //O número do assento do passageiro é número de passagens cadastradas mais um
        passagem.setNumAssento(qtd+1);
        //Código é gerado com a composição do id da viagem mais o número do assento
        passagem.setCodValidacao(passagem.getViagem()+""+passagem.getNumAssento());
        
        //verifca se ainda existe assento disponível
        if(viagem.get().getAsssentosDisponiveis()==0) {
        	System.out.println("Sem assentos disponíveis.");
        	listOfErros.add("Sem assentos disponíveis.");
        }
        
        System.out.println("cod="+passagem.getCodValidacao()+" assento="+passagem.getNumAssento()+" viagem="+passagem.getViagem()+" cpf="+passagem.getCpf());
    	
        
        if(listOfErros.size() > 0) {
            throw new BusinessExceptions(listOfErros);
        }else {
        	passagemRepository.save(passagem);
        }
        //return new String("Passagem salva");
    }
}
