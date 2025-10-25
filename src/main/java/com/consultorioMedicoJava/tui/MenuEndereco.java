package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.model.Endereco;
import com.consultorioMedicoJava.model.Paciente;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuEndereco {

    private ArrayList<Paciente> pacientes; // Para vincular endereços aos pacientes
    private Scanner scanner = new Scanner(System.in);

    public MenuEndereco(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

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
        int indicePaciente = Integer.parseInt(scanner.nextLine());

        if (indicePaciente < 0 || indicePaciente >= pacientes.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente);

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        paciente.setEndereco(endereco);

        System.out.println("Endereço adicionado com sucesso ao paciente " + paciente.getNome() + "!");
    }

    private void listarEnderecos() {
        System.out.println("=====Lista de Endereços=====");

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        boolean temEndereco = false;

        for (Paciente p : pacientes) {
            if (p.getEndereco() != null) {
                temEndereco = true;
                System.out.println("----------------------------");
                System.out.println("Paciente: " + p.getNome());
                System.out.println("CPF: " + p.getCpf());
                System.out.println("Estado: " + p.getEndereco().getEstado());
                System.out.println("Cidade: " + p.getEndereco().getCidade());
                System.out.println("Rua: " + p.getEndereco().getRua());
                System.out.println("Número: " + p.getEndereco().getNumero());
                System.out.println("CEP: " + p.getEndereco().getCep());
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

        Paciente paciente = buscarPacientePorCpf(cpf);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        if (paciente.getEndereco() == null) {
            System.out.println("Este paciente não possui endereço cadastrado.");
            return;
        }

        System.out.println("=====Novo Endereço=====");
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        Endereco novoEndereco = new Endereco(estado, cidade, rua, numero, cep);
        paciente.setEndereco(novoEndereco);

        System.out.println("Endereço atualizado com sucesso!");
    }

    private void removerEndereco() {
        System.out.println("=====Remover Endereço=====");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        Paciente paciente = buscarPacientePorCpf(cpf);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        if (paciente.getEndereco() == null) {
            System.out.println("Este paciente não possui endereço cadastrado.");
            return;
        }

        paciente.setEndereco(null);
        System.out.println("Endereço removido com sucesso!");
    }


    private Paciente buscarPacientePorCpf(String cpf) {
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }
}

