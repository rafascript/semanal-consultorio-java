package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.model.Paciente;
import com.consultorioMedicoJava.tui.MenuPaciente;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Paciente> lista_pacientes = new ArrayList<>();
    MenuPaciente pacientes = new MenuPaciente(lista_pacientes);
    MenuConsulta consulta = new MenuConsulta(lista_pacientes);

    public void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("==========Menu=========");
            System.out.println(" 1 - Gerenciar Paciente");
            System.out.println(" 2 - Gerenciar Endereços");
            System.out.println(" 3 - Gerenciar Consultas");
            System.out.println(" 0 - Sair");
            System.out.print("Digite uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    lista_pacientes = pacientes.exibirMenuPaciente();
                    break;
                case 2:
                    System.out.println("Menu de Endereços - em desenvolvimento");
                    break;
                case 3:
                    lista_pacientes = consulta.exibirMenuConsulta();
                    break;
                case 0:
                    System.out.println("Sair");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }

}
