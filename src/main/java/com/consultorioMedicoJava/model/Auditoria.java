package com.consultorioMedicoJava.model;

import java.time.LocalDateTime;

public class Auditoria {
    private int idAuditoria;
    private String operacao;
    private String tabela;
    private String registroId;
    private LocalDateTime dataOperacao;
    private String usuario;

    public Auditoria() {}

    public Auditoria(int idAuditoria, String operacao, String tabela, String registroId, LocalDateTime dataOperacao, String usuario) {
        this.idAuditoria = idAuditoria;
        this.operacao = operacao;
        this.tabela = tabela;
        this.registroId = registroId;
        this.dataOperacao = dataOperacao;
        this.usuario = usuario;
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getRegistroId() {
        return registroId;
    }

    public void setRegistroId(String registroId) {
        this.registroId = registroId;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
                "idAuditoria=" + idAuditoria +
                ", operacao='" + operacao + '\'' +
                ", tabela='" + tabela + '\'' +
                ", registroId='" + registroId + '\'' +
                ", dataOperacao=" + dataOperacao +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
