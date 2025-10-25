package com.consultorioMedicoJava;

import com.consultorioMedicoJava.tui.MenuPrincipal;
import com.consultorioMedicoJava.util.DatabaseConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        DatabaseConnection.getConnection();

        MenuPrincipal menu = new MenuPrincipal();
        menu.exibirMenuPrincipal();

        DatabaseConnection.closeConnection();

    }
}
