package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.dao.ConsultaDAO;
import com.consultorioMedicoJava.dao.PacienteDAO;
import com.consultorioMedicoJava.model.Consulta;
import com.consultorioMedicoJava.model.Paciente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuConsulta {

    private ConsultaDAO consultaDAO = new ConsultaDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void exibirMenuConsulta(){
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("=====Menu Consulta=======");
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
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void adicionarConsulta() {
        System.out.println("=====Nova Consulta======");

        // Buscar pacientes do banco
        ArrayList<Paciente> pacientes = pacienteDAO.findAll();

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado! Cadastre um paciente primeiro.");
            return;
        }

        System.out.println("Pacientes cadastrados: ");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + " - " + pacientes.get(i).getNome() + " (CPF: " + pacientes.get(i).getCpf() + ")");
        }

        System.out.print("Escolha o número do paciente: ");
        int indicePaciente;
        try {
            indicePaciente = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Índice inválido!");
            return;
        }

        if (indicePaciente < 0 || indicePaciente >= pacientes.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente);

        System.out.print("Data e hora da consulta (formato: dd/MM/yyyy HH:mm): ");
        String dataHoraString = scanner.nextLine();
        LocalDateTime dataHora;
        try {
            dataHora = LocalDateTime.parse(dataHoraString, formatter);
        } catch (Exception e) {
            System.out.println("Data/hora em formato inválido.");
            return;
        }

        System.out.print("Status (Agendada, Confirmada, Reagendada, Cancelada) - deixe vazio para Agendada: ");
        String statusInput = scanner.nextLine().trim();
        String statusFinal;
        if (statusInput.isEmpty()) {
            statusFinal = "Agendada";
        } else {
            String lower = statusInput.toLowerCase();
            if (lower.equals("agendada") || lower.equals("confirmada") ||
                    lower.equals("reagendada") || lower.equals("cancelada")) {
                statusFinal = statusInput.substring(0,1).toUpperCase() + statusInput.substring(1).toLowerCase();
            } else {
                System.out.println("Status inválido. Usando 'Agendada'.");
                statusFinal = "Agendada";
            }
        }

        Consulta consulta = new Consulta(UUID.randomUUID(), dataHora, paciente);
        consulta.setStatus(statusFinal);

        // Salvar no banco
        consultaDAO.insert(consulta);

        System.out.println("Consulta cadastrada com sucesso!");
        System.out.println("ID da consulta: " + consulta.getIdConsulta());
    }

    private void listarConsultas() {
        System.out.println("=====Lista de Consultas======");

        // Buscar do banco
        ArrayList<Consulta> consultas = consultaDAO.findAll();

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada!");
            return;
        }

        for (Consulta consulta : consultas) {
            System.out.println("============================");
            System.out.println("ID: " + consulta.getIdConsulta());

            // Se tiver paciente carregado
            if (consulta.getPaciente() != null) {
                System.out.println("Paciente: " + consulta.getPaciente().getNome());
                System.out.println("CPF: " + consulta.getPaciente().getCpf());
            }

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

            // Buscar do banco
            Consulta consulta = consultaDAO.findById(id.toString());

            if (consulta == null) {
                System.out.println("Consulta não encontrada.");
                return;
            }

            System.out.print("Deseja alterar a data/hora? (s/n): ");
            String alterarData = scanner.nextLine().trim();
            if (alterarData.equalsIgnoreCase("s") || alterarData.equalsIgnoreCase("sim")) {
                System.out.print("Nova data e hora (formato: dd/MM/yyyy HH:mm): ");
                String dataHoraString = scanner.nextLine();
                try {
                    LocalDateTime novaDataHora = LocalDateTime.parse(dataHoraString, formatter);
                    consulta.setData(novaDataHora);
                } catch (Exception e) {
                    System.out.println("Data/hora em formato inválido. A data não foi alterada.");
                }
            }

            System.out.println("Deseja alterar o status? (opções: 0-Não, 1-Agendada, 2-Confirmada, 3-Cancelada, 4-Reagendada)");
            System.out.print("Escolha uma opção: ");
            String opc = scanner.nextLine().trim();

            switch (opc) {
                case "1":
                    consulta.setStatus("Agendada");
                    break;
                case "2":
                    consulta.setStatus("Confirmada");
                    break;
                case "3":
                    consulta.setStatus("Cancelada");
                    break;
                case "4":
                    consulta.setStatus("Reagendada");
                    break;
                default:
                    System.out.println("Nenhuma alteração de status realizada.");
            }

            // Atualizar no banco
            consultaDAO.update(consulta);
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

            // Verificar se existe
            Consulta consulta = consultaDAO.findById(id.toString());

            if (consulta != null) {
                // Deletar do banco
                consultaDAO.delete(id.toString());
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

            // Buscar do banco
            Consulta consulta = consultaDAO.findById(id.toString());

            if (consulta != null) {
                consulta.setStatus("Cancelada");

                // Atualizar no banco
                consultaDAO.update(consulta);
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

            // Buscar do banco
            Consulta consulta = consultaDAO.findById(id.toString());

            if (consulta != null) {
                System.out.print("Nova data e hora (formato: dd/MM/yyyy HH:mm): ");
                String dataHoraString = scanner.nextLine();
                LocalDateTime novaDataHora = LocalDateTime.parse(dataHoraString, formatter);

                consulta.setData(novaDataHora);
                consulta.setStatus("Reagendada");

                // Atualizar no banco
                consultaDAO.update(consulta);
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

            // Buscar do banco
            Consulta consulta = consultaDAO.findById(id.toString());

            if (consulta != null) {
                consulta.setStatus("Confirmada");

                // Atualizar no banco
                consultaDAO.update(consulta);
                System.out.println("Consulta confirmada com sucesso!");
            } else {
                System.out.println("Consulta não encontrada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido!");
        }
    }

}
