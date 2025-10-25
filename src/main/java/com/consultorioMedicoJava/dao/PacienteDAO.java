package com.consultorioMedicoJava.dao;

import com.consultorioMedicoJava.model.Paciente;
import com.consultorioMedicoJava.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class PacienteDAO {

    public void insert(Paciente paciente) {
        String sql = "INSERT INTO paciente (id_paciente, nome, cpf, email, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getIdPaciente().toString());
            pstmt.setString(2, paciente.getNome());
            pstmt.setString(3, paciente.getCpf());
            pstmt.setString(4, paciente.getEmail());
            pstmt.setString(5, paciente.getTelefone());

            pstmt.executeUpdate();
            System.out.println("Paciente inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }

    public ArrayList<Paciente> findAll() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT id_paciente, nome, cpf, email, telefone FROM paciente";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(UUID.fromString(rs.getString("id_paciente")));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                pacientes.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pacientes: " + e.getMessage());
        }

        return pacientes;
    }

    public Paciente findByCpf(String cpf) {
        String sql = "SELECT id_paciente, nome, cpf, email, telefone FROM paciente WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(UUID.fromString(rs.getString("id_paciente")));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                return p;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar paciente: " + e.getMessage());
        }

        return null;
    }

    public void update(Paciente paciente) {
        String sql = "UPDATE paciente SET nome = ?, email = ?, telefone = ? WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getEmail());
            pstmt.setString(3, paciente.getTelefone());
            pstmt.setString(4, paciente.getCpf());

            pstmt.executeUpdate();
            System.out.println("Paciente atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void delete(String cpf) {
        String sql = "DELETE FROM paciente WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
            System.out.println("Paciente removido!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }
}
