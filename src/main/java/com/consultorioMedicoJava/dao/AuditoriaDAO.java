package com.consultorioMedicoJava.dao;

import com.consultorioMedicoJava.model.Auditoria;
import com.consultorioMedicoJava.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuditoriaDAO {

    public ArrayList<Auditoria> findAll() {
        ArrayList<Auditoria> lista = new ArrayList<>();
        String sql = "SELECT id_auditoria, operacao, tabela, registro_id, data_operacao, usuario FROM auditoria ORDER BY data_operacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Auditoria a = new Auditoria();
                a.setIdAuditoria(rs.getInt("id_auditoria"));
                a.setOperacao(rs.getString("operacao"));
                a.setTabela(rs.getString("tabela"));
                a.setRegistroId(rs.getString("registro_id"));
                Timestamp ts = rs.getTimestamp("data_operacao");
                if (ts != null) a.setDataOperacao(ts.toLocalDateTime());
                a.setUsuario(rs.getString("usuario"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar auditoria: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Auditoria> findByTabela(String tabelaFilter) {
        ArrayList<Auditoria> lista = new ArrayList<>();
        String sql = "SELECT id_auditoria, operacao, tabela, registro_id, data_operacao, usuario FROM auditoria WHERE tabela = ? ORDER BY data_operacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tabelaFilter);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Auditoria a = new Auditoria();
                a.setIdAuditoria(rs.getInt("id_auditoria"));
                a.setOperacao(rs.getString("operacao"));
                a.setTabela(rs.getString("tabela"));
                a.setRegistroId(rs.getString("registro_id"));
                Timestamp ts = rs.getTimestamp("data_operacao");
                if (ts != null) a.setDataOperacao(ts.toLocalDateTime());
                a.setUsuario(rs.getString("usuario"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar auditoria por tabela: " + e.getMessage());
        }

        return lista;
    }
}
