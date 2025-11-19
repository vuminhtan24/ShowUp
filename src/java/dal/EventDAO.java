package dal;

import java.sql.*;
import java.util.*;
import java.util.Date;
import model.Event;

public class EventDAO extends DBContext {

    public int getArtistIdByUserId(int userId) {
        String sql = "SELECT artist_id FROM artist_profiles WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("artist_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Event> getEventsByPage(int offset, int limit) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events ORDER BY event_id DESC LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event p = new Event();
                    p.setEventId(rs.getInt("event_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setEventName(rs.getString("event_name"));
                    p.setDescription(rs.getString("description"));
                    p.setEventDate(rs.getTimestamp("event_date"));
                    p.setEventTime(rs.getTime("event_time"));
                    p.setLocation(rs.getString("location"));
                    p.setStatus(rs.getString("status"));
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalEvents() {
        String sql = "SELECT COUNT(*) FROM events";
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
    // Tìm kiếm event theo keyword, phân trang

    public List<Event> searchEventsByKeyword(String keyword, int offset, int limit) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_name LIKE ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setCustomerId(rs.getInt("customer_id"));
                e.setEventName(rs.getString("event_name"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setLocation(rs.getString("location"));
                e.setDescription(rs.getString("description"));
                e.setStatus(rs.getString("status"));
                e.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

// Đếm tổng event theo keyword
    public int countEventsByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM events WHERE event_name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean insertEvent(Event event) {
        String sql = "INSERT INTO events (customer_id, event_name,description, event_date, event_time, location, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, event.getCustomerId());
            st.setString(2, event.getEventName());
            st.setString(3, event.getDescription());
            st.setDate(4, new java.sql.Date(event.getEventDate().getTime()));
            st.setTime(5, event.getEventTime());
            st.setString(6, event.getLocation());
            st.setString(7, event.getStatus() != null ? event.getStatus() : "Pending");
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // lấy sự kiện theo cus id
    public List<Event> getEventsByCustomerId(int customerId) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE customer_id = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setCustomerId(rs.getInt("customer_id"));
                e.setEventName(rs.getString("event_name"));
                e.setDescription(rs.getString("description"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setLocation(rs.getString("location"));
                e.setStatus(rs.getString("status"));
                e.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy toàn bộ sự kiện
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = """
            SELECT e.event_id, e.customer_id, e.event_name, e.event_date, e.event_time,
                   e.location, e.description, e.created_at,
                   u.username AS customer_name
            FROM events e
            LEFT JOIN users u ON e.customer_id = u.user_id
            ORDER BY e.created_at DESC
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setCustomerId(rs.getInt("customer_id"));
                e.setEventName(rs.getString("event_name"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setLocation(rs.getString("location"));
                e.setDescription(rs.getString("description "));
                e.setCreatedAt(rs.getTimestamp("created_at"));
                e.setCustomerName(rs.getString("customer_name"));
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Lấy sự kiện theo ID
    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setCustomerId(rs.getInt("customer_id"));
                e.setEventName(rs.getString("event_name"));
                e.setEventDate(rs.getDate("event_date"));
                e.setEventTime(rs.getTime("event_time"));
                e.setLocation(rs.getString("location"));
                e.setDescription(rs.getString("description "));
                e.setCreatedAt(rs.getTimestamp("created_at"));
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Thêm sự kiện mới
    public void addEvent(Event e) {
        String sql = """
            INSERT INTO events (customer_id, event_name, event_date, event_time, location, description, created_at)
            VALUES (?, ?, ?, ?, ?, ?, NOW())
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, e.getCustomerId());
            ps.setString(2, e.getEventName());
            ps.setDate(3, new java.sql.Date(e.getEventDate().getTime()));
            ps.setTime(4, e.getEventTime());
            ps.setString(5, e.getLocation());
            ps.setString(6, e.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Cập nhật sự kiện
    public void updateEvent(Event e) {
        String sql = """
            UPDATE events
            SET customer_id = ?, event_name = ?, event_date = ?, event_time = ?, 
                location = ?, description  = ?, status = ?
            WHERE event_id = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, e.getCustomerId());
            ps.setString(2, e.getEventName());
            ps.setDate(3, new java.sql.Date(e.getEventDate().getTime()));
            ps.setTime(4, e.getEventTime());
            ps.setString(5, e.getLocation());
            ps.setString(6, e.getDescription());
            ps.setString(7, e.getStatus());
            ps.setInt(8, e.getEventId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Xóa sự kiện
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            int rowsAffected = ps.executeUpdate(); // số dòng bị xóa
            return rowsAffected > 0; // true nếu xóa thành công
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // false nếu có lỗi hoặc không xóa dòng nào
    }

    //cập nhật status
    public boolean updateEventStatus(int eventId, String status) {
        String sql = "UPDATE events SET status = ? WHERE event_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, eventId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return false;
    }

    public static void main(String[] args) {
        EventDAO dao = new EventDAO();
        boolean a = dao.updateEventStatus(3, "Closed");
        if (a == true) {
            System.out.println("ok");
        } else {
            System.out.println("ko");
        }
    }
}
