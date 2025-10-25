package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.model.Consulta;
import com.consultorioMedicoJava.model.Paciente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuConsulta {

    private ArrayList<Consulta> consultas = new ArrayList<>();
    private ArrayList<Paciente> pacientes;
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MenuConsulta(ArrayList<Paciente>  pacientes) {
        this.pacientes = pacientes;
    }

    public ArrayList<Paciente> exibirMenuConsulta(){
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("=====Mecu Consulta=======");
            System.out.println("1 - Adicionar consulta");
            System.out.println("2 - Listar consultas");
            System.out.println("3 - Editar consulta");
            System.out.println("4 - Remover consulta");
            System.out.println("5 - Cancelar consulta");
            System.out.println("6 - Reagendar consulta");
            System.out.println("7 - Confirmar consulta");
            System.out.println("0 - Voltar");
            System.out.print("Digite uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch  (Exception e)  {
                System.out.println("Digite um valor válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarConsulta();
                    break;
                case 2:
                    listarConsultas();
                    break;
                case 3:
                    editarConsulta();
                    break;
                case 4:
                    removerConsulta();
                    break;
                case 5:
                    cancelarConsulta();
                    break;
                case 6:
                    reagendarConsulta();
                    break;
                case 7:
                    confirmarConsulta();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    return pacientes;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        return null;
    }

    private void adicionarConsulta() {
        System.out.println("=====Nova Consulta======");

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado! Cadastre um paciente primeiro.");
        }

        System.out.println("Pacientes cadastrados: ");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + " - " + pacientes.get(i).getNome() + " (CPF: " + pacientes.get(i).getCpf() + ")");
        }

        System.out.print("Escolha o número do paciente: ");
        int indicePaciente = Integer.parseInt(scanner.nextLine());

        if (indicePaciente < 0 || indicePaciente > pacientes.size()) {
            System.out.print("Indice inválido!");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente);

        System.out.print("Data e hora da consulta (fomato: dd/MM/yyyy HH:mm): ");
        String dataHoraString = scanner.nextLine();
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, formatter);

        Consulta consulta = new Consulta(UUID.randomUUID(), dataHora, paciente);
        consultas.add(consulta);

        System.out.println("Consulta cadastrada com sucesso!");
        System.out.println("ID da consulta: "  + consulta.getIdConsulta());

    }

    private void listarConsultas() {
        System.out.println("=====Lista de Consultas======");

        if (consultas.isEmpty()) {
            System.out.println("Nenhum consulta encontrada!");
        }

        for (Consulta consulta : consultas) {
            System.out.println("============================");
            System.out.println("ID: " + consulta.getIdConsulta());
            System.out.println("Paciente: " + consulta.getPaciente().getNome());
            System.out.println("CPF: " + consulta.getPaciente().getCpf());
            System.out.println("Data/Hora: " + consulta.getData().format(formatter));
            System.out.println("Status: " + consulta.getStatus());
            System.out.println("============================");
        }

    }

    private void editarConsulta() {
        System.out.println("=====Editar Consulta=====");
        System.out.print("Digite o ID da consulta: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Consulta consulta = buscarConsultaPorId(id);

            if (consulta == null) {
                System.out.println("Consulta não encontrada.");
                return;
            }

            System.out.print("Nova data e hora (formato: dd/MM/yyyy HH:mm): ");
            String dataHoraString = scanner.nextLine();
            LocalDateTime novaDataHora = LocalDateTime.parse(dataHoraString, formatter);

            consulta.setData(novaDataHora);
            System.out.println("Consulta atualizada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

    private void removerConsulta() {
        System.out.println("=====Remover Consulta=====");
        System.out.print("Digite o ID da consulta: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Consulta consulta = buscarConsultaPorId(id);

            if (consulta != null) {
                consultas.remove(consulta);
                System.out.println("Consulta removida com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

    private void cancelarConsulta() {
        System.out.println("=====Cancelar Consulta=====");
        System.out.print("Digite o ID da consulta: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Consulta consulta = buscarConsultaPorId(id);

            if (consulta != null) {
                consulta.setStatus("Cancelada");
                System.out.println("Consulta cancelada com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

    private void reagendarConsulta() {
        System.out.println("=====Reagendar Consulta=====");
        System.out.print("Digite o ID da consulta: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Consulta consulta = buscarConsultaPorId(id);

            if (consulta != null) {
                System.out.print("Nova data e hora (formato: dd/MM/yyyy HH:mm): ");
                String dataHoraString = scanner.nextLine();
                LocalDateTime novaDataHora = LocalDateTime.parse(dataHoraString, formatter);

                consulta.setData(novaDataHora);
                consulta.setStatus("Reagendada");
                System.out.println("Consulta reagendada com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

    private void confirmarConsulta() {
        System.out.println("=====Confirmar Consulta=====");
        System.out.print("Digite o ID da consulta: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Consulta consulta = buscarConsultaPorId(id);

            if (consulta != null) {
                consulta.setStatus("Confirmada");
                System.out.println("Consulta confirmada com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

    private Consulta buscarConsultaPorId(UUID id) {
        for (Consulta consulta : consultas) {
            if (consulta.getIdConsulta().equals(id)) {
                return consulta;
            }
        }
        return null;
    }
}
