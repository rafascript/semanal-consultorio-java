package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.dao.EnderecoDAO;
import com.consultorioMedicoJava.dao.PacienteDAO;
import com.consultorioMedicoJava.model.Endereco;
import com.consultorioMedicoJava.model.Paciente;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuEndereco {

    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenuEndereco() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=====Menu Endereço=====");
            System.out.println("1 - Adicionar endereço");
            System.out.println("2 - Listar endereços");
            System.out.println("3 - Editar endereço");
            System.out.println("4 - Remover endereço");
            System.out.println("0 - Voltar");
            System.out.print("Digite uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um valor válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarEndereco();
                    break;
                case 2:
                    listarEnderecos();
                    break;
                case 3:
                    editarEndereco();
                    break;
                case 4:
                    removerEndereco();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void adicionarEndereco() {
        System.out.println("=====Adicionar Endereço=====");

        ArrayList<Paciente> pacientes = pacienteDAO.findAll();

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado! Cadastre um paciente primeiro.");
            return;
        }

        System.out.println("Pacientes cadastrados:");
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            System.out.println(i + " - " + p.getNome() + " (CPF: " + p.getCpf() + ")");
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

        System.out.print("CEP: ");
        String cep = scanner.nextLine().trim();

        System.out.print("Número: ");
        String numero = scanner.nextLine().trim();

        Endereco enderecoViaCep = com.consultorioMedicoJava.util.ViaCepClient.lookup(cep);
        if (enderecoViaCep == null) {
            System.out.println("Não foi possível localizar o CEP via ViaCep. Informe manualmente os dados do endereço.");
            System.out.print("Estado: ");
            String estado = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            UUID idEndereco = UUID.randomUUID();
            Endereco endereco = new Endereco(idEndereco, estado, cidade, rua, numero, cep);
            enderecoDAO.insert(endereco, paciente.getIdPaciente().toString());
        } else {
            String estado = enderecoViaCep.getEstado();
            String cidade = enderecoViaCep.getCidade();
            String rua = enderecoViaCep.getRua();
            if (estado == null || estado.isBlank()) {
                System.out.print("Estado não retornado pelo ViaCep. Informe o Estado: ");
                estado = scanner.nextLine();
            }
            if (cidade == null || cidade.isBlank()) {
                System.out.print("Cidade não retornada pelo ViaCep. Informe a Cidade: ");
                cidade = scanner.nextLine();
            }
            if (rua == null || rua.isBlank()) {
                System.out.print("Rua não retornada pelo ViaCep. Informe a Rua: ");
                rua = scanner.nextLine();
            }
            UUID idEndereco = UUID.randomUUID();
            Endereco endereco = new Endereco(idEndereco, estado, cidade, rua, numero, cep);
            enderecoDAO.insert(endereco, paciente.getIdPaciente().toString());
        }

        System.out.println("Endereço adicionado com sucesso ao paciente " + paciente.getNome() + "!");
    }

    private void listarEnderecos() {
        System.out.println("=====Lista de Endereços=====");

        // Buscar pacientes do banco
        ArrayList<Paciente> pacientes = pacienteDAO.findAll();

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        boolean temEndereco = false;

        for (Paciente p : pacientes) {
            // Buscar endereço do paciente no banco
            Endereco endereco = enderecoDAO.findByPacienteId(p.getIdPaciente().toString());

            if (endereco != null) {
                temEndereco = true;
                System.out.println("----------------------------");
                System.out.println("Paciente: " + p.getNome());
                System.out.println("CPF: " + p.getCpf());
                System.out.println("Estado: " + endereco.getEstado());
                System.out.println("Cidade: " + endereco.getCidade());
                System.out.println("Rua: " + endereco.getRua());
                System.out.println("Número: " + endereco.getNumero());
                System.out.println("CEP: " + endereco.getCep());
                System.out.println("----------------------------");
            }
        }

        if (!temEndereco) {
            System.out.println("Nenhum endereço cadastrado.");
        }
    }

    private void editarEndereco() {
        System.out.println("=====Editar Endereço=====");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        Paciente paciente = pacienteDAO.findByCpf(cpf);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        Endereco endereco = enderecoDAO.findByPacienteId(paciente.getIdPaciente().toString());

        if (endereco == null) {
            System.out.println("Este paciente não possui endereço cadastrado.");
            return;
        }

        System.out.print("Informe o CEP (deixe vazio para manter): ");
        String cep = scanner.nextLine().trim();

        System.out.print("Informe o número da residência (deixe vazio para manter): ");
        String numero = scanner.nextLine().trim();

        if (!cep.isEmpty()) {
            Endereco enderecoViaCep = com.consultorioMedicoJava.util.ViaCepClient.lookup(cep);
            if (enderecoViaCep == null) {
                System.out.println("Não foi possível localizar o CEP via ViaCep. Informe manualmente os dados do endereço.");
                System.out.print("Estado: ");
                String estado = scanner.nextLine();
                System.out.print("Cidade: ");
                String cidade = scanner.nextLine();
                System.out.print("Rua: ");
                String rua = scanner.nextLine();
                endereco.setEstado(estado);
                endereco.setCidade(cidade);
                endereco.setRua(rua);
                endereco.setCep(cep);
            } else {
                String estado = enderecoViaCep.getEstado();
                String cidade = enderecoViaCep.getCidade();
                String rua = enderecoViaCep.getRua();
                if (estado == null || estado.isBlank()) {
                    System.out.print("Estado não retornado pelo ViaCep. Informe o Estado: ");
                    estado = scanner.nextLine();
                }
                if (cidade == null || cidade.isBlank()) {
                    System.out.print("Cidade não retornada pelo ViaCep. Informe a Cidade: ");
                    cidade = scanner.nextLine();
                }
                if (rua == null || rua.isBlank()) {
                    System.out.print("Rua não retornada pelo ViaCep. Informe a Rua: ");
                    rua = scanner.nextLine();
                }
                endereco.setEstado(estado);
                endereco.setCidade(cidade);
                endereco.setRua(rua);
                endereco.setCep(cep);
            }
        }

        if (!numero.isEmpty()) {
            endereco.setNumero(numero);
        }

        enderecoDAO.update(endereco, paciente.getIdPaciente().toString());

        System.out.println("Endereço atualizado com sucesso!");
    }

    private void removerEndereco() {
        System.out.println("=====Remover Endereço=====");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        // Buscar paciente do banco
        Paciente paciente = pacienteDAO.findByCpf(cpf);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        // Buscar endereço do banco
        Endereco endereco = enderecoDAO.findByPacienteId(paciente.getIdPaciente().toString());

        if (endereco == null) {
            System.out.println("Este paciente não possui endereço cadastrado.");
            return;
        }

        // Deletar do banco
        enderecoDAO.delete(paciente.getIdPaciente().toString());

        System.out.println("Endereço removido com sucesso!");
    }
}
