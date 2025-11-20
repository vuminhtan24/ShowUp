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
    // Lấy danh sách user có credits + fullname (phục vụ trang manageCredits)

    public List<User> getUsersByPageWithCredits(int offset, int limit) {
        List<User> list = new ArrayList<>();

        String sql = """
        SELECT user_id, username, credits
        FROM users
        ORDER BY user_id
        LIMIT ? OFFSET ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setCredits(rs.getInt("credits"));
                    list.add(u);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<User> searchUsersByPageWithCredits(String keyword, int offset, int limit) {
        List<User> list = new ArrayList<>();

        String sql = """
        SELECT u.user_id, u.username, u.role, u.status, c.credits
        FROM users u
        LEFT JOIN user_credits c ON u.user_id = c.user_id
        WHERE u.username LIKE ? OR u.email LIKE ?
        ORDER BY u.user_id
        LIMIT ? OFFSET ?
    """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            st.setString(2, "%" + keyword + "%");
            st.setInt(3, limit);
            st.setInt(4, offset);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                u.setCredits(rs.getInt("credits"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("searchUsersByPageWithCredits: " + e.getMessage());
        }

        return list;
    }

    public void updateCredits(int userId, int credits) {
        String sql = "UPDATE users SET credits=? WHERE user_id=?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, credits);
            st.setInt(2, userId);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> searchUsersByPage(String keyword, int offset, int limit) {
        List<User> list = new ArrayList<>();

        String sql = """
        SELECT user_id, fullname, credits
        FROM users
        WHERE fullname LIKE ? OR CAST(user_id AS CHAR) LIKE ?
        ORDER BY user_id
        LIMIT ? OFFSET ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, limit);
            ps.setInt(4, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setCredits(rs.getInt("credits"));
                    list.add(u);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getTotalUsersSearch(String keyword) {
        String sql = """
        SELECT COUNT(*)
        FROM users
        WHERE username LIKE ? OR CAST(user_id AS CHAR) LIKE ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void decreaseCredits(int userId, int amount) {
        String sql = "UPDATE users SET credits = credits - ? WHERE user_id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, amount); // amount = 5
            st.setInt(2, userId);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCredits(int userId) {
        String sql = "select credits from users where user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("credits");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        System.out.println("===== TEST REGISTER =====");
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setPasswordHash("123456");  // Sẽ được BCrypt mã hoá
        newUser.setEmail("test@example.com");
        newUser.setPhone("0123456789");
        newUser.setRole("customer");

        boolean reg = dao.register(newUser);
        System.out.println("Register result: " + reg);

        System.out.println("\n===== TEST LOGIN =====");
        User login = dao.login("test@example.com", "123456");
        System.out.println("Login: " + (login != null ? "SUCCESS → " + login.getUsername() : "FAIL"));

        System.out.println("\n===== TEST GET TOTAL USERS =====");
        System.out.println("Total users: " + dao.getTotalUsers());

        System.out.println("\n===== TEST PAGINATION (getUsersByPage) =====");
        List<User> pageUsers = dao.getUsersByPage(0, 5);
        for (User u : pageUsers) {
            System.out.println(u.getUserId() + " | " + u.getUsername() + " | " + u.getRole());
        }

        System.out.println("\n===== TEST UPDATE ROLE =====");
        boolean roleUpdated = dao.updateUserRole(1, "admin");
        System.out.println("Update role result: " + roleUpdated);

        System.out.println("\n===== TEST GET USERS WITH CREDITS =====");
        List<User> creditUsers = dao.getUsersByPageWithCredits(0, 5);
        for (User u : creditUsers) {
            System.out.println(u.getUserId() + " | " + u.getUsername() + " | Credits: " + u.getCredits());
        }

        System.out.println("\n===== TEST UPDATE CREDITS =====");
        dao.updateCredits(1, 200); // set = 200
        System.out.println("Credits after update (user 1): " + dao.getCredits(1));

        System.out.println("\n===== TEST DECREASE CREDITS (trừ 5) =====");
        dao.decreaseCredits(1, 5);
        System.out.println("Credits after decrease: " + dao.getCredits(1));

        System.out.println("\n===== TEST SEARCH USERS =====");
        List<User> search = dao.searchUsersByPage("Tân", 0, 10);
        for (User u : search) {
            System.out.println(u.getUserId() + " | " + u.getUsername() + " | Credits: " + u.getCredits());
        }

        System.out.println("\n===== TEST TOTAL USERS SEARCH =====");
        System.out.println("Total search results: " + dao.getTotalUsersSearch("Tân"));
    }

}
