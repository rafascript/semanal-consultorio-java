package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.dao.PacienteDAO;
import com.consultorioMedicoJava.dao.EnderecoDAO;
import com.consultorioMedicoJava.model.Paciente;
import com.consultorioMedicoJava.model.Endereco;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuPaciente {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenuPaciente(){
        int opcao = -1;

        while (opcao != 0){
            System.out.println("==========MenuPaciente=========");
            System.out.println(" 1 - Adicionar Paciente");
            System.out.println(" 2 - Lista pacientes");
            System.out.println(" 3 - Editar Paciente");
            System.out.println(" 4 - Remover Paciente");
            System.out.println(" 0 - Voltar");
            System.out.print("Digite uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite uma opção válida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarPaciente();
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    editarPaciente();
                    break;
                case 4:
                    removerPaciente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void adicionarPaciente(){
        System.out.println("=====Novo Paciente=====");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.println("=====Endereço=====");
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        UUID idPaciente = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();

        Endereco enderecoViaCep = com.consultorioMedicoJava.util.ViaCepClient.lookup(cep);
        Endereco endereco;
        if (enderecoViaCep == null) {
            System.out.println("Não foi possível localizar o CEP via ViaCep. Informe manualmente os dados do endereço.");
            System.out.print("Estado: ");
            String estado = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            endereco = new Endereco(idEndereco, estado, cidade, rua, numero, cep);
        } else {
            endereco = new Endereco(idEndereco, enderecoViaCep.getEstado(), enderecoViaCep.getCidade(), enderecoViaCep.getRua(), numero, cep);
        }

        Paciente paciente = new Paciente(idPaciente, nome, cpf, email, telefone, endereco);

        // Salvar no banco
        pacienteDAO.insert(paciente);
        enderecoDAO.insert(endereco, idPaciente.toString());

        System.out.println("Paciente adicionado com sucesso!");
    }

    private void listarPacientes(){
        System.out.println("=====Lista de Pacientes=====");

        // Buscar do banco
        ArrayList<Paciente> pacientes = pacienteDAO.findAll();

        if (pacientes.isEmpty()){
            System.out.println("Nenhum paciente encontrado!");
            return;
        }

        for (Paciente paciente : pacientes){
            System.out.println("============================");
            System.out.println("ID: " + paciente.getIdPaciente());
            System.out.println("Nome: " + paciente.getNome());
            System.out.println("CPF: " + paciente.getCpf());
            System.out.println("Email: " + paciente.getEmail());
            System.out.println("Telefone: " + paciente.getTelefone());

            // Buscar endereço do paciente
            Endereco endereco = enderecoDAO.findByPacienteId(paciente.getIdPaciente().toString());
            if (endereco != null) {
                System.out.println("Endereço: " + endereco.getRua() + ", " + endereco.getNumero() +
                        " - " + endereco.getCidade() + "/" + endereco.getEstado() +
                        " - CEP: " + endereco.getCep());
            }
            System.out.println("============================");
        }
    }

    private void editarPaciente(){
        System.out.println("=====Editar Paciente=====");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        // Buscar do banco
        Paciente paciente = pacienteDAO.findByCpf(cpf);

        if (paciente == null){
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.print("Nome: ");
        paciente.setNome(scanner.nextLine());

        System.out.print("Email: ");
        paciente.setEmail(scanner.nextLine());

        System.out.print("Telefone: ");
        paciente.setTelefone(scanner.nextLine());

        System.out.println("=====Novo Endereço=====");
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        Endereco endereco = enderecoDAO.findByPacienteId(paciente.getIdPaciente().toString());

        if (endereco != null) {
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
                    endereco.setEstado(enderecoViaCep.getEstado());
                    endereco.setCidade(enderecoViaCep.getCidade());
                    endereco.setRua(enderecoViaCep.getRua());
                    endereco.setCep(cep);
                }
            }

            if (!numero.isEmpty()) {
                endereco.setNumero(numero);
            }

            enderecoDAO.update(endereco, paciente.getIdPaciente().toString());
        } else {
            UUID idEndereco = UUID.randomUUID();
            Endereco novoEndereco;
            Endereco enderecoViaCep = com.consultorioMedicoJava.util.ViaCepClient.lookup(cep);
            if (enderecoViaCep == null) {
                System.out.print("Estado: ");
                String estado = scanner.nextLine();
                System.out.print("Cidade: ");
                String cidade = scanner.nextLine();
                System.out.print("Rua: ");
                String rua = scanner.nextLine();
                novoEndereco = new Endereco(idEndereco, estado, cidade, rua, numero, cep);
            } else {
                novoEndereco = new Endereco(idEndereco, enderecoViaCep.getEstado(), enderecoViaCep.getCidade(), enderecoViaCep.getRua(), numero, cep);
            }
            enderecoDAO.insert(novoEndereco, paciente.getIdPaciente().toString());
        }

        // Atualizar paciente no banco
        pacienteDAO.update(paciente);

        System.out.println("Paciente atualizado com sucesso!");
    }

    private void removerPaciente(){
        System.out.print("Digite o CPF do paciente para remover: ");
        String cpf = scanner.nextLine();

        // Buscar do banco
        Paciente paciente = pacienteDAO.findByCpf(cpf);

        if (paciente != null){
            // Deletar do banco (o endereço será deletado automaticamente por CASCADE)
            pacienteDAO.delete(cpf);
            System.out.println("Paciente removido com sucesso!");
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }
}
