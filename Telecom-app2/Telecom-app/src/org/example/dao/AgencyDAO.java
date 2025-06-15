package org.example.dao;

import org.example.db.DatabaseManager;
import org.example.model.Agency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgencyDAO {

    // 전체 통신사 목록 조회
    public List<Agency> getAllAgencies() {
        List<Agency> list = new ArrayList<>();
        String sql = "SELECT * FROM Agency";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Agency agency = new Agency();
                agency.setId(rs.getInt("id"));
                agency.setName(rs.getString("name"));
                list.add(agency);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ID로 단일 통신사 조회
    public Agency getAgencyById(int id) {
        String sql = "SELECT * FROM Agency WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Agency agency = new Agency();
                agency.setId(rs.getInt("id"));
                agency.setName(rs.getString("name"));
                return agency;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 필요 시 insert(), update(), delete()도 추가 가능
}