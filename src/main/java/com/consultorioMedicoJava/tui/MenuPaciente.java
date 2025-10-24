package com.consultorioMedicoJava.tui;


import com.consultorioMedicoJava.model.Paciente;
import com.consultorioMedicoJava.model.Endereco;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MenuPaciente {

    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenuPaciente(){
        int opcao = -1;

        while (opcao != 0){
            System.out.println("==========MenuPaciente=========");
            System.out.println(" 1 - Adicionar Paciente");
            System.out.println(" 2 - Lista pacientes");
            System.out.println(" 3 - Editar Paciente");
            System.out.println(" 0 - Voltar");
            System.out.print("Digite uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digiteuma opção válida.");
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
        System.out.println("Nome: ");
        String nome = scanner.nextLine();

        System.out.println("CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Telefone: ");
        String telefone = scanner.nextLine();

        // Sem consulta em API
        System.out.println("Estado: ");
        String estado = scanner.nextLine();

        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.println("Rua: ");
        String rua = scanner.nextLine();

        System.out.println("Número: ");
        String numero = scanner.nextLine();

        System.out.println("CEP: ");
        String cep = scanner.nextLine();

        Endereco endereco = new Endereco(estado, cidade, rua, numero, cep);
        UUID novoId =  UUID.randomUUID();

        Paciente paciente = new Paciente(novoId, nome, cpf, email, telefone, endereco);
        pacientes.add(paciente);

        System.out.println("Paciente adicionado com sucesso! ");
    }

    private void listarPacientes(){
        System.out.println("=====Lista de  Pacientes=====");
        if (pacientes.isEmpty()){
            System.out.println("Nenhum paciente encontrado! ");
            return;
        }
        for (Paciente paciente : pacientes){
            System.out.println(paciente);
        }
    }

    private void editarPaciente(){
        System.out.println("=====Editar Paciente=====");
        System.out.println("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null){
            System.out.println("Paciente não encontrado.");
            return;
        }
        System.out.println("Nome: ");
        paciente.setNome(scanner.nextLine());

        System.out.println("CPF: ");
        paciente.setCpf(scanner.nextLine());

        System.out.println("Email: ");
        paciente.setEmail(scanner.nextLine());



        System.out.println("Paciente atualizado com sucesso! ");
    }

    private void removerPaciente(){
        System.out.println("Digite o CPF do paciente para remover: ");
        String cpf = scanner.nextLine();
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null){
            pacientes.remove(paciente);
            System.out.println("Paciente removido com sucesso!");
        } else {
            System.out.println("Paciente não encontrado. ");
        }
    }

    private Paciente buscarPacientePorCpf(String cpf) {
        for (Paciente paciente : pacientes){
            if (paciente.getCpf().equals(cpf)){
                return paciente;
            }
        }
        return null;
    }

}
