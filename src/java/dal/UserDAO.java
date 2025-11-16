package dal;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO extends DBContext {

    // Đăng nhập
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND status='active'";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("password_hash");
                // So sánh password nhập vào với hash trong DB
                if (BCrypt.checkpw(password, hash)) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("role"),
                            rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký
    public boolean register(User u) {
        String sql = "INSERT INTO users(username,password_hash,email,phone,role) VALUES(?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, u.getUsername());

            // ✅ Mã hoá password trước khi lưu
            String hashed = BCrypt.hashpw(u.getPasswordHash(), BCrypt.gensalt(12));
            st.setString(2, hashed);

            st.setString(3, u.getEmail());
            st.setString(4, u.getPhone());
            st.setString(5, u.getRole());

            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserRole(int userId, String role) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, role);
            st.setInt(2, userId);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getUsersByPage(int offset, int limit) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setRole(rs.getString("role"));
                    u.setStatus(rs.getString("status"));
                    list.add(u);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]) {
        UserDAO dao = new UserDAO();

//        // 1. Test Register
//        User newUser = new User(
//                0,
//                "tan", // username
//                "123456", // password (hiện tại chưa hash)
//                "tanvmhe186791@fpt.edu.vn", // email
//                "0919994398", // phone
//                "admin", // role
//                "active" // status
//        );
//
//        boolean registered = dao.register(newUser);
//        if (registered) {
//            System.out.println("Đăng ký thành công!");
//        } else {
//            System.out.println("Đăng ký thất bại!");
//        }
        // 2. Test Login
        User loginUser = dao.login("tanvmhe186791@fpt.edu.vn", "123456");
        if (loginUser != null) {
            System.out.println("Đăng nhập thành công: " + loginUser.getUsername() + " - role: " + loginUser.getRole());
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
    }
}
