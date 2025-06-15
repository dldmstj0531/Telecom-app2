package org.example.dao;

import org.example.db.DatabaseManager;
import org.example.model.Agency;
import org.example.model.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    // 전체 요금제 목록 조회
    public List<Plan> getAllPlans() {
        List<Plan> list = new ArrayList<>();
        String sql = "SELECT * FROM Plan";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setMonthlyFee(rs.getInt("monthly_fee"));
                plan.setContractMonths(rs.getInt("contract_months"));
                plan.setDataAmount(rs.getInt("data_amount"));
                plan.setSmsAmount(rs.getInt("sms_amount"));
                plan.setCallMinutes(rs.getInt("call_minutes"));

                Agency agency = new Agency();
                agency.setId(rs.getInt("agency_id"));
                plan.setAgency(agency);

                list.add(plan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ID로 요금제 상세 조회
    public Plan getPlanById(int id) {
        String sql = "SELECT * FROM Plan WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setMonthlyFee(rs.getInt("monthly_fee"));
                plan.setContractMonths(rs.getInt("contract_months"));
                plan.setDataAmount(rs.getInt("data_amount"));
                plan.setSmsAmount(rs.getInt("sms_amount"));
                plan.setCallMinutes(rs.getInt("call_minutes"));

                Agency agency = new Agency();
                agency.setId(rs.getInt("agency_id"));
                plan.setAgency(agency);

                return plan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Plan> getPlansByAgencyId(int agencyId) {
        List<Plan> list = new ArrayList<>();
        String sql = "SELECT * FROM Plan WHERE agency_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agencyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setMonthlyFee(rs.getInt("monthly_fee"));
                p.setDataAmount(rs.getInt("data_amount"));
                p.setCallMinutes(rs.getInt("call_minutes"));
                // 필요한 경우 contract_months, sms_amount 등 추가

                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 추후 Logger로 교체 권장
        }

        return list;
    }
    // 필요시 insert/update/delete 메서드 추가 가능
}
