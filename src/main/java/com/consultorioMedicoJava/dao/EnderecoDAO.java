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

            // Auditoria manual (trigger não existente para endereco)
            String sqlAudit = "INSERT INTO auditoria (operacao, tabela, registro_id) VALUES (?, ?, ?)";
            try (PreparedStatement pa = conn.prepareStatement(sqlAudit)) {
                pa.setString(1, "INSERT");
                pa.setString(2, "endereco");
                pa.setString(3, endereco.getIdEndereco().toString());
                pa.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Erro ao inserir auditoria (endereco insert): " + ex.getMessage());
            }

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

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Endereço atualizado!");

                String sqlAudit = "INSERT INTO auditoria (operacao, tabela, registro_id) VALUES (?, ?, ?)";
                try (PreparedStatement pa = conn.prepareStatement(sqlAudit)) {
                    pa.setString(1, "UPDATE");
                    pa.setString(2, "endereco");
                    pa.setString(3, endereco.getIdEndereco() != null ? endereco.getIdEndereco().toString() : null);
                    pa.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Erro ao inserir auditoria (endereco update): " + ex.getMessage());
                }
            } else {
                System.out.println("Nenhum endereço atualizado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    public void delete(String pacienteId) {
        String sqlFetch = "SELECT id_endereco FROM endereco WHERE paciente_id = ?";
        String sql = "DELETE FROM endereco WHERE paciente_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement fetch = conn.prepareStatement(sqlFetch)) {

            fetch.setString(1, pacienteId);
            ResultSet rs = fetch.executeQuery();
            String idEndereco = null;
            if (rs.next()) {
                idEndereco = rs.getString("id_endereco");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, pacienteId);
                int deleted = pstmt.executeUpdate();
                if (deleted > 0) {
                    System.out.println("Endereço removido!");

                    String sqlAudit = "INSERT INTO auditoria (operacao, tabela, registro_id) VALUES (?, ?, ?)";
                    try (PreparedStatement pa = conn.prepareStatement(sqlAudit)) {
                        pa.setString(1, "DELETE");
                        pa.setString(2, "endereco");
                        pa.setString(3, idEndereco);
                        pa.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("Erro ao inserir auditoria (endereco delete): " + ex.getMessage());
                    }

                } else {
                    System.out.println("Nenhum endereço removido.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover endereço: " + e.getMessage());
        }
    }
}
