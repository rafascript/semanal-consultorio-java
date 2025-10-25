package com.consultorioMedicoJava.tui;

import com.consultorioMedicoJava.dao.AuditoriaDAO;
import com.consultorioMedicoJava.model.Auditoria;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuAuditoria {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void exibirMenuAuditoria() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=====Menu Auditoria=====");
            System.out.println("1 - Listar todas auditorias");
            System.out.println("2 - Filtrar por tabela");
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
                    listarTodas();
                    break;
                case 2:
                    filtrarPorTabela();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarTodas() {
        ArrayList<Auditoria> lista = auditoriaDAO.findAll();
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro de auditoria encontrado.");
            return;
        }
        for (Auditoria a : lista) {
            System.out.println("---------------------------");
            System.out.println("ID: " + a.getIdAuditoria());
            System.out.println("Operação: " + a.getOperacao());
            System.out.println("Tabela: " + a.getTabela());
            System.out.println("Registro ID: " + a.getRegistroId());
            if (a.getDataOperacao() != null) System.out.println("Data: " + a.getDataOperacao().format(formatter));
            System.out.println("Usuário: " + a.getUsuario());
        }
    }

    private void filtrarPorTabela() {
        System.out.print("Digite o nome da tabela (ex: paciente, consulta, endereco): ");
        String tabela = scanner.nextLine().trim();
        if (tabela.isEmpty()) {
            System.out.println("Nome de tabela inválido.");
            return;
        }
        ArrayList<Auditoria> lista = auditoriaDAO.findByTabela(tabela);
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro de auditoria encontrado para a tabela: " + tabela);
            return;
        }
        for (Auditoria a : lista) {
            System.out.println("---------------------------");
            System.out.println("ID: " + a.getIdAuditoria());
            System.out.println("Operação: " + a.getOperacao());
            System.out.println("Tabela: " + a.getTabela());
            System.out.println("Registro ID: " + a.getRegistroId());
            if (a.getDataOperacao() != null) System.out.println("Data: " + a.getDataOperacao().format(formatter));
            System.out.println("Usuário: " + a.getUsuario());
        }
    }
}
