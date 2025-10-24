package com.consultorioMedicoJava.model;

public class Endereco {

    private String estado;
    private String cidade;
    private String rua;
    private String numero;
    private String cep;

    public Endereco(){};

    public Endereco(String estado, String cidade, String rua, String numero, String cep){
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
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
