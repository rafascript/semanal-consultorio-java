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
        this.status = null;
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
        if (status != null && status.equalsIgnoreCase("Cancelada")) {
            return "Cancelada";
        }
        if (status != null && status.equalsIgnoreCase("Reagendada")) {
            return "Reagendada";
        }
        if (status != null && status.equalsIgnoreCase("Confirmada")) {
            if (data != null && data.isBefore(LocalDateTime.now())) {
                return "Realizada";
            }
            return "Confirmada";
        }

        if (data != null && data.isBefore(LocalDateTime.now())) {
            return "Realizada";
        }
        return "Agendada";
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
