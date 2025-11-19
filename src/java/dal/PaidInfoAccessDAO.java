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
public class PaidInfoAccessDAO extends DBContext {

    // Kiểm tra viewer đã mua quyền xem PHONE của artist chưa
    public boolean hasAccess(int viewerId, int targetUserId, String infoType) {
        String sql = "SELECT 1 FROM paid_info_access "
                + "WHERE viewer_id = ? AND target_user_id = ? AND info_type = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, viewerId);
            st.setInt(2, targetUserId);
            st.setString(3, infoType);
            ResultSet rs = st.executeQuery();
            return rs.next(); // có bản ghi => đã mua
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lưu lịch sử user trả điểm để xem thông tin
    public void insertAccess(int viewerId, int targetUserId, String infoType) {
        String sql = "INSERT INTO paid_info_access (viewer_id, target_user_id, info_type) "
                + "VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, viewerId);
            st.setInt(2, targetUserId);
            st.setString(3, infoType);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
