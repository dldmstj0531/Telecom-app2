package org.example.dao;

import org.example.db.DatabaseManager;
import org.example.model.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {

    // ✅ 전체 할인 목록 조회 (현재 BenefitChecker에서 사용되는 핵심 메소드)
    public static List<Discount> getAllDiscounts() {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT * FROM Discount";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setName(rs.getString("name"));
                discount.setDiscountAmount(rs.getString("discount_amount"));
                discount.setCondition(rs.getString("condition"));
                discount.setExtraBenefit(rs.getString("extra_benefit"));
                // plan_id는 현재 사용하지 않으므로 생략 가능 (로직에서 필요없음)
                list.add(discount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ (보존) 특정 요금제에 대한 할인 목록 조회 (현재 사용하지 않지만 구조 보존)
    public static List<Discount> getDiscountsByPlanId(int planId) {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT * FROM Discount WHERE plan_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, planId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setName(rs.getString("name"));
                discount.setDiscountAmount(rs.getString("discount_amount"));
                discount.setCondition(rs.getString("condition"));

                list.add(discount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ (보존) 할인 ID로 단일 할인 정보 조회 (혹시 추후 확장 대비 그대로 유지)
    public static Discount getDiscountById(int id) {
        String sql = "SELECT * FROM Discount WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setName(rs.getString("name"));
                discount.setDiscountAmount(rs.getString("discount_amount"));
                discount.setCondition(rs.getString("condition"));

                return discount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}


