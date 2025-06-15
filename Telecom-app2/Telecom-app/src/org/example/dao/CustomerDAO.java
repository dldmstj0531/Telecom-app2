package org.example.dao;

import org.example.db.DatabaseManager;
import org.example.model.Agency;
import org.example.model.Customer;
import org.example.model.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void create(Customer customer) {
        String sql = """
            INSERT INTO Customer 
            (phone_number, age, current_agency_id, current_plan_id, months_using_plan, 
             desired_price, desired_agency_id, 
             is_disabled, is_living_support, is_housing_support, is_pensioner) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getPhoneNumber());
            pstmt.setInt(2, customer.getAge());
            pstmt.setInt(3, customer.getCurrentAgency().getId());
            pstmt.setInt(4, customer.getCurrentPlan().getId());
            pstmt.setInt(5, customer.getMonthsUsingPlan());
            pstmt.setInt(6, customer.getDesiredPrice());
            pstmt.setInt(7, customer.getDesiredAgency().getId());

            // 복지 입력 기능 추가
            pstmt.setBoolean(8, customer.isDisabled());
            pstmt.setBoolean(9, customer.isLivingSupport());
            pstmt.setBoolean(10, customer.isHousingSupport());
            pstmt.setBoolean(11, customer.isPensioner());

            pstmt.executeUpdate();
            System.out.println("✅ 고객 정보 저장 완료");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAge(rs.getInt("age"));

                // 현재 통신사 (ID만 셋팅)
                Agency currentAgency = new Agency();
                currentAgency.setId(rs.getInt("current_agency_id"));
                customer.setCurrentAgency(currentAgency);

                // 현재 요금제 (ID만 셋팅)
                Plan currentPlan = new Plan();
                currentPlan.setId(rs.getInt("current_plan_id"));
                customer.setCurrentPlan(currentPlan);

                customer.setMonthsUsingPlan(rs.getInt("months_using_plan"));
                customer.setDesiredPrice(rs.getInt("desired_price"));

                // 변경 희망 통신사 (ID만 셋팅)
                Agency desiredAgency = new Agency();
                desiredAgency.setId(rs.getInt("desired_agency_id"));
                customer.setDesiredAgency(desiredAgency);

                // 복지 입력 기능 추가
                customer.setDisabled(rs.getBoolean("is_disabled"));
                customer.setLivingSupport(rs.getBoolean("is_living_support"));
                customer.setHousingSupport(rs.getBoolean("is_housing_support"));
                customer.setPensioner(rs.getBoolean("is_pensioner"));

                list.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 필요 시 update(), delete()도 추가 가능
}

