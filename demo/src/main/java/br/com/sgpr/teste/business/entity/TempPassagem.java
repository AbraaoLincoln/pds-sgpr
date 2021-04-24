package br.com.sgpr.teste.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passagem")
public class TempPassagem {
    private String codValidacao;
    private int viagem;
    private int numAssento;
    private String cpf;

    @Id
    @Column(name = "cod_validacao")
    public String getCodValidacao(){
        return codValidacao;
    }

    @Column(name = "cod_validacao")
    public void setCodValidacao(String novoCodValidacao){
        codValidacao = novoCodValidacao;
    }


    public int getViagem() {
        return viagem;
    }

    public void setViagem(int viagem) {
        this.viagem = viagem;
    }

    @Column(name = "num_assento")
    public int getNumAssento() {
        return numAssento;
    }

    @Column(name = "num_assento")
    public void setNumAssento(int numAssento) {
        this.numAssento = numAssento;
    }

    @Column(name = "cpf_dono")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
