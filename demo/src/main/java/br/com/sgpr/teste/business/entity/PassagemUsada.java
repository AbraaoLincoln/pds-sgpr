package br.com.sgpr.teste.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passagem_usadas")
public class PassagemUsada {
    private String codValidacao;
    private int viagem;
    private int numAssento;
    private String cpf;
    private String horaCancelamento;

    public PassagemUsada() {}

    public PassagemUsada(TempPassagem pass) {
        this.codValidacao = pass.getCodValidacao();
        this.viagem = pass.getViagem();
        this.numAssento = pass.getNumAssento();
        this.cpf = pass.getCpf();
    }

    @Id
    @Column(name = "cod_validacao")
    public String getCodValidacao(){
        return codValidacao;
    }

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

    public String getHoraCancelamento() {
        return horaCancelamento;
    }

    public void setHoraCancelamento(String horaCancelamento) {
        this.horaCancelamento = horaCancelamento;
    }
    
}
