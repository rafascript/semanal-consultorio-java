package com.consultorioMedicoJava.dao;

import com.consultorioMedicoJava.model.Consulta;
import com.consultorioMedicoJava.model.Paciente;
import com.consultorioMedicoJava.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ConsultaDAO {



    public void insert(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_consulta, data_hora, status, paciente_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, consulta.getIdConsulta().toString());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(consulta.getData()));
            pstmt.setString(3, consulta.getStatus());
            pstmt.setString(4, consulta.getPaciente().getIdPaciente().toString());

            pstmt.executeUpdate();
            System.out.println("Consulta inserida!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir consulta: " + e.getMessage());
        }
    }

    public ArrayList<Consulta> findAll() {
        ArrayList<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id_consulta, data_hora, status, paciente_id FROM consulta";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            PacienteDAO pacienteDAO = new PacienteDAO();

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(UUID.fromString(rs.getString("id_consulta")));
                c.setData(rs.getTimestamp("data_hora").toLocalDateTime());
                c.setStatus(rs.getString("status"));
                consultas.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas: " + e.getMessage());
        }

        return consultas;
    }

    public void update(Consulta consulta) {
        String sql = "UPDATE consulta SET data_hora = ?, status = ? WHERE id_consulta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(consulta.getData()));
            pstmt.setString(2, consulta.getStatus());
            pstmt.setString(3, consulta.getIdConsulta().toString());

            pstmt.executeUpdate();
            System.out.println("Consulta atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    public void delete(String idConsulta) {
        String sql = "DELETE FROM consulta WHERE id_consulta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idConsulta);
            pstmt.executeUpdate();
            System.out.println("Consulta removida!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover consulta: " + e.getMessage());
        }
    }

    public Consulta findById(String idConsulta) {
        String sql = "SELECT id_consulta, data_hora, status, paciente_id FROM consulta WHERE id_consulta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idConsulta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(UUID.fromString(rs.getString("id_consulta")));
                c.setData(rs.getTimestamp("data_hora").toLocalDateTime());
                c.setStatus(rs.getString("status"));
                return c;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar consulta: " + e.getMessage());
        }

        return null;
    }
}

