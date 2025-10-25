package com.consultorioMedicoJava.tui;

import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private MenuPaciente menuPaciente = new MenuPaciente();
    private MenuEndereco menuEndereco = new MenuEndereco();
    private MenuConsulta menuConsulta = new MenuConsulta();

    public void exibirMenuPrincipal() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==========Menu Principal=========");
            System.out.println(" 1 - Gerenciar Pacientes");
            System.out.println(" 2 - Gerenciar Endereços");
            System.out.println(" 3 - Gerenciar Consultas");
            System.out.println(" 0 - Sair");
            System.out.print("Digite uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    menuPaciente.exibirMenuPaciente();
                    break;
                case 2:
                    menuEndereco.exibirMenuEndereco();
                    break;
                case 3:
                    menuConsulta.exibirMenuConsulta();
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }
}
