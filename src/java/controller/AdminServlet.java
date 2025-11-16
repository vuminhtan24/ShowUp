/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BookingDAO;
import dal.DAOAccount;
import dal.EventDAO;
import dal.PaymentDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Booking;
import model.Payment;
import model.User;
import model.Event;

/**
 *
 * @author VU MINH TAN
 */
public class AdminServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        BookingDAO bookingDAO = new BookingDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        EventDAO eventDAO = new EventDAO();

        // Lấy số trang hiện tại (nếu không có thì mặc định là 1)
        int userPage = parsePage(request.getParameter("userPage"));
        int bookingPage = parsePage(request.getParameter("bookingPage"));
        int paymentPage = parsePage(request.getParameter("paymentPage"));
        int eventsPage = parsePage(request.getParameter("eventsPage"));

        // Tính offset
        int userOffset = (userPage - 1) * PAGE_SIZE;
        int bookingOffset = (bookingPage - 1) * PAGE_SIZE;
        int paymentOffset = (paymentPage - 1) * PAGE_SIZE;
        int eventsOffset = (eventsPage - 1) * PAGE_SIZE;

        // Lấy dữ liệu phân trang
        List<User> userList = userDAO.getUsersByPage(userOffset, PAGE_SIZE);
        List<Booking> bookingList = bookingDAO.getBookingsByPage(bookingOffset, PAGE_SIZE);
        List<Payment> paymentList = paymentDAO.getPaymentsByPage(paymentOffset, PAGE_SIZE);
        List<Event> eventsList = eventDAO.getEventsByPage(eventsOffset, PAGE_SIZE);

        // Tính tổng số trang
        int totalUserPages = (int) Math.ceil((double) userDAO.getTotalUsers() / PAGE_SIZE);
        int totalBookingPages = (int) Math.ceil((double) bookingDAO.getTotalBookings() / PAGE_SIZE);
        int totalPaymentPages = (int) Math.ceil((double) paymentDAO.getTotalPayments() / PAGE_SIZE);
        int totalEventsPages = (int) Math.ceil((double) eventDAO.getTotalEvents() / PAGE_SIZE);

        // Gửi về JSP
        request.setAttribute("userList", userList);
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("paymentList", paymentList);
        request.setAttribute("eventsList", eventsList);

        request.setAttribute("userPage", userPage);
        request.setAttribute("bookingPage", bookingPage);
        request.setAttribute("paymentPage", paymentPage);
        request.setAttribute("eventsPage", eventsPage);

        request.setAttribute("totalUserPages", totalUserPages);
        request.setAttribute("totalBookingPages", totalBookingPages);
        request.setAttribute("totalPaymentPages", totalPaymentPages);
        request.setAttribute("totalEventsPages", totalEventsPages);

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    private int parsePage(String param) {
        try {
            int p = Integer.parseInt(param);
            return p > 0 ? p : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventIdStr = request.getParameter("eventId");
        String status = request.getParameter("status");

        if (eventIdStr != null && status != null) {
            try {
                int eventId = Integer.parseInt(eventIdStr);
                EventDAO eventDAO = new EventDAO();
                boolean updated = eventDAO.updateEventStatus(eventId, status);
                if (!updated) {
                    System.out.println("Failed to update status for eventId: " + eventId);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid eventId: " + eventIdStr);
            }
        }

        // Load lại danh sách events để JSP hiển thị mới
        EventDAO eventDAO = new EventDAO();
        List<Event> eventsList = eventDAO.getEventsByPage(0, PAGE_SIZE); // page 1, hoặc lấy param nếu muốn giữ trang hiện tại
        request.setAttribute("eventsList", eventsList);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
