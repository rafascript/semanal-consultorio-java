package com.consultorioMedicoJava.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consulta {

    private UUID idConsulta;
    private LocalDateTime data;
    private Paciente paciente;
    private String status;

    public Consulta(){}

    public Consulta(UUID idConsulta, LocalDateTime data, Paciente paciente) {
        this.idConsulta = idConsulta;
        this.data = data;
        this.paciente = paciente;
        this.status = status; //cancelada , reagendada , confirmada
    }

    public UUID getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(UUID idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", data=" + data +
                ", paciente=" + paciente +
                ", status='" + status + '\'' +
                '}';
    }
}
