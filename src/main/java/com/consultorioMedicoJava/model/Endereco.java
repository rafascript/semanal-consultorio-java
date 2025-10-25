package com.consultorioMedicoJava.model;

import java.util.UUID;

public class Endereco {

    private UUID idEndereco;
    private String estado;
    private String cidade;
    private String rua;
    private String numero;
    private String cep;

    public Endereco(){};

    public Endereco(UUID idEndereco, String estado, String cidade, String rua, String numero, String cep){
        this.idEndereco = idEndereco;
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    public UUID getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(UUID idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "Estado=" + estado + '\'' +
                ", Cidade=" + cidade + '\'' +
                ", Rua=" + rua + '\'' +
                ", Numero=" + numero + '\'' +
                ", cep=" + cep + '\'' +
                '}';
    }

}
