package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 1. DB 연결 클래스
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/telecom";
    private static final String USER = "root";
    private static final String PASSWORD = "changemanage1234"; // 각자 설정한 비밀번호로 입력
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}