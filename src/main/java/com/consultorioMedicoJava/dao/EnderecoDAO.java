package com.consultorioMedicoJava.dao;

import com.consultorioMedicoJava.model.Endereco;
import com.consultorioMedicoJava.util.DatabaseConnection;
import java.sql.*;
import java.util.UUID;

public class EnderecoDAO {

    public void insert(Endereco endereco, String pacienteId) {
        String sql = "INSERT INTO endereco (id_endereco, estado, cidade, rua, numero, cep, paciente_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, endereco.getIdEndereco().toString());
            pstmt.setString(2, endereco.getEstado());
            pstmt.setString(3, endereco.getCidade());
            pstmt.setString(4, endereco.getRua());
            pstmt.setString(5, endereco.getNumero());
            pstmt.setString(6, endereco.getCep());
            pstmt.setString(7, pacienteId);

            pstmt.executeUpdate();
            System.out.println("Endereço inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir endereço: " + e.getMessage());
        }
    }

    public Endereco findByPacienteId(String pacienteId) {
        String sql = "SELECT id_endereco, estado, cidade, rua, numero, cep FROM endereco WHERE paciente_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pacienteId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Endereco e = new Endereco();
                e.setIdEndereco(UUID.fromString(rs.getString("id_endereco")));
                e.setEstado(rs.getString("estado"));
                e.setCidade(rs.getString("cidade"));
                e.setRua(rs.getString("rua"));
                e.setNumero(rs.getString("numero"));
                e.setCep(rs.getString("cep"));
                return e;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar endereço: " + e.getMessage());
        }

        return null;
    }

    public void update(Endereco endereco, String pacienteId) {
        String sql = "UPDATE endereco SET estado = ?, cidade = ?, rua = ?, numero = ?, cep = ? WHERE paciente_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, endereco.getEstado());
            pstmt.setString(2, endereco.getCidade());
            pstmt.setString(3, endereco.getRua());
            pstmt.setString(4, endereco.getNumero());
            pstmt.setString(5, endereco.getCep());
            pstmt.setString(6, pacienteId);

            pstmt.executeUpdate();
            System.out.println("Endereço atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    public void delete(String pacienteId) {
        String sql = "DELETE FROM endereco WHERE paciente_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pacienteId);
            pstmt.executeUpdate();
            System.out.println("Endereço removido!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover endereço: " + e.getMessage());
        }
    }
}
