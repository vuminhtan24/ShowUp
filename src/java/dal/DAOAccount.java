package dal;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DAOAccount extends DBContext {

    // Lấy toàn bộ user
    public List<User> getAllAccount() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username") != null ? rs.getString("username").trim() : "",
                        rs.getString("password_hash") != null ? rs.getString("password_hash").trim() : "",
                        rs.getString("email") != null ? rs.getString("email").trim() : "",
                        rs.getString("phone") != null ? rs.getString("phone").trim() : "",
                        rs.getString("role"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đăng ký tài khoản mới
    public boolean createAccount(User acc) {
        String sql = "INSERT INTO users (username, password_hash, email, phone, role, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, acc.getUsername());

            // Mã hoá mật khẩu trước khi lưu
            String hashedPassword = BCrypt.hashpw(acc.getPasswordHash(), BCrypt.gensalt(12));
            ps.setString(2, hashedPassword);

            ps.setString(3, acc.getEmail());
            ps.setString(4, acc.getPhone());
            ps.setString(5, acc.getRole());
            ps.setString(6, acc.getStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        acc.setUserId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng nhập theo email
    public User validateEmail(String email, String rawPassword) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("password_hash").trim();
                if (BCrypt.checkpw(rawPassword, hash)) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username").trim(),
                            hash,
                            rs.getString("email").trim(),
                            rs.getString("phone").trim(),
                            rs.getString("role"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng nhập theo username
    public User validate(String username, String rawPassword) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("password_hash").trim();
                if (BCrypt.checkpw(rawPassword, hash)) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username").trim(),
                            hash,
                            rs.getString("email").trim(),
                            rs.getString("phone").trim(),
                            rs.getString("role"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy user theo ID
    public User getAccountById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username").trim(),
                        rs.getString("password_hash").trim(),
                        rs.getString("email").trim(),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xoá tài khoản
    public boolean deleteAccount(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin (trừ mật khẩu)
    public boolean updateAccount(User acc) {
        String sql = "UPDATE users SET username=?, email=?, phone=?, role=?, status=? WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getEmail());
            ps.setString(3, acc.getPhone());
            ps.setString(4, acc.getRole());
            ps.setString(5, acc.getStatus());
            ps.setInt(6, acc.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đổi mật khẩu
    public boolean changePassword(int userId, String newRawPassword) {
        String sql = "UPDATE users SET password_hash=? WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String hashed = BCrypt.hashpw(newRawPassword, BCrypt.gensalt(12));
            ps.setString(1, hashed);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        DAOAccount dao = new DAOAccount();

        // Tạo 1 user mới để test
        User newUser = new User(
                0, // user_id (sẽ được auto tăng)
                "testUser", // username
                "123456", // password (raw, sẽ được hash trong DAO)
                "testuser@example.com", // email
                "0123456789", // phone
                "customer", // role
                "active" // status
        );

        boolean success = dao.createAccount(newUser);
        if (success) {
            System.out.println("✅ Tạo tài khoản thành công! ID = " + newUser.getUserId());
        } else {
            System.out.println("❌ Tạo tài khoản thất bại!");
        }

        // In toàn bộ account trong DB để kiểm tra
        for (User u : dao.getAllAccount()) {
            System.out.println(u);
        }
    }

}
