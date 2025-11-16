package dal;

import java.sql.*;
import java.util.*;
import model.Booking;

public class BookingDAO extends DBContext {

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setEventId(rs.getInt("event_id"));
                b.setArtistId(rs.getInt("artist_id"));
                b.setServiceId(rs.getInt("service_id"));
                b.setPriceEstimated(rs.getBigDecimal("price_estimated"));
                b.setPriceFinal(rs.getBigDecimal("price_final"));
                b.setStatus(rs.getString("status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } 
// 🔹 Lấy danh sách bookings theo trang
    public List<Booking> getBookingsByPage(int offset, int limit) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY created_at DESC LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setEventId(rs.getInt("event_id"));
                    b.setArtistId(rs.getInt("artist_id"));
                    b.setServiceId(rs.getInt("service_id"));
                    b.setPriceEstimated(rs.getBigDecimal("price_estimated"));
                    b.setPriceFinal(rs.getBigDecimal("price_final"));
                    b.setStatus(rs.getString("status"));
                    b.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 Đếm tổng số bookings để phân trang
    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) FROM bookings";
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
}
