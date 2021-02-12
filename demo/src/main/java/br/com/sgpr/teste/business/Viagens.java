package br.com.sgpr.teste.business;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Table(name="viagens")
public class Viagens {
    private String origem;
    private String destino;
    private String data;
    private String hora_saida;
    private String motorista;
    private String onibus;
    private String rota;

    public Viagens(){

    }

    public String getOrigem(){
        return origem;
    }

    public void setOrigem(String novaOrigem){
        origem = novaOrigem;
    }

    public String getDestino(){
        return destino;
    }

    public void setDestino(String novoDestino){
        destino = novoDestino;
    }

    @Id
    @Column(name="data_viagem")
    public String getData(){
        return data;
    }

    @Column(name="data_viagem")
    public void setData(String novaData){
        data = novaData;
    }

    @Column(name="hora_saida")
    public String getHoraSaida(){
        return hora_saida;
    }

    @Column(name="hora_saida")
    public void setHoraSaida(String novaHoraSaida){
        hora_saida = novaHoraSaida;
    }

    public String getMotorista(){
        return motorista;
    }

    public void setMotorista(String novoMotorista){
        motorista = novoMotorista;
    }

    public String getOnibus(){
        return onibus;
    }

    public void setOnibus(String novoOnibus){
        onibus = novoOnibus;
    }

    public String getRota(){
        return rota;
    }

    public void setRota(String novaRota){
        rota = novaRota;
    }

}
