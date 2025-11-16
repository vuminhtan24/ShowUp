/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;

/**
 *
 * @author VU MINH TAN
 */
public class TransactionHistoryDAO extends DBContext {

    public void insert(int userId, int credits, String type, String description) {
        String sql = "INSERT INTO transaction_history (user_id, credits, type, description) "
                + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, credits);     // ví dụ: -5
            st.setString(3, type);     // SPEND_VIEW_INFO
            st.setString(4, description);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(int userId, int amount, String description) {
        String sql = "INSERT INTO transaction_history (user_id, amount, description, created_at) "
                + "VALUES (?, ?, ?, GETDATE())";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, amount); // -5 khi trừ
            st.setString(3, description);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("addTransaction: " + e.getMessage());
        }
    }
}
