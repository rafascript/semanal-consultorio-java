package com.consultorioMedicoJava.model;

import java.util.UUID;

public class Paciente {

    private UUID idPaciente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;

    public Paciente() {
    }

    public Paciente(UUID id, String nome, String cpf, String email, String telefone, Endereco endereco) {
        this.idPaciente = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email=" + email + '\'' +
                ", telefone=" + telefone + '\'' +
                ", endereco=" + endereco +
                '}';

    }

}
