package dal;

import java.sql.*;
import java.util.*;
import model.Payment;

public class PaymentDAO extends DBContext {

    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setBookingId(rs.getInt("booking_id"));
                p.setAmount(rs.getBigDecimal("amount"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setPaymentStatus(rs.getString("payment_status"));
                p.setPaidAt(rs.getTimestamp("paid_at"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 🔹 Lấy danh sách payments theo trang

    public List<Payment> getPaymentsByPage(int offset, int limit) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY paid_at DESC LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payment p = new Payment();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setBookingId(rs.getInt("booking_id"));
                    p.setAmount(rs.getBigDecimal("amount"));
                    p.setPaymentMethod(rs.getString("payment_method"));
                    p.setPaymentStatus(rs.getString("payment_status"));
                    p.setPaidAt(rs.getTimestamp("paid_at"));
                    list.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 Đếm tổng số payment để tính số trang
    public int getTotalPayments() {
        String sql = "SELECT COUNT(*) FROM payments";
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

    public static void main(String[] args) {
        PaymentDAO dao = new PaymentDAO();

        // --- Test getPaymentsByPage() ---
        int page = 5;
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        List<Payment> payments = dao.getPaymentsByPage(offset, pageSize);
        System.out.println("=== DANH SÁCH PAYMENT TRANG " + page + " ===");
        for (Payment p : payments) {
            System.out.println(
                    "ID: " + p.getPaymentId()
                    + " | BookingID: " + p.getBookingId()
                    + " | Amount: " + p.getAmount()
                    + " | Method: " + p.getPaymentMethod()
                    + " | Status: " + p.getPaymentStatus()
                    + " | Paid At: " + p.getPaidAt()
            );
        }

        // --- Test getTotalPayments() ---
        int totalPayments = dao.getTotalPayments();
        System.out.println("\nTổng số payment: " + totalPayments);

        // Tính tổng số trang (nếu cần)
        int totalPages = (int) Math.ceil((double) totalPayments / pageSize);
        System.out.println("Tổng số trang: " + totalPages);
    }
}
