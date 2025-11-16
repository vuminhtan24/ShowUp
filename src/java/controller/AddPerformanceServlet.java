/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.EventDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Event;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
public class AddPerformanceServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

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
            out.println("<title>Servlet AddPerformanceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPerformanceServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("postRecruitmentAds.jsp").forward(request, response);
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

        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");

        // Nếu chưa đăng nhập, quay lại trang đăng nhập
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        int userId = account.getUserId();

        // Lấy dữ liệu từ form
        String eventName = request.getParameter("eventName");
        String eventDescription = request.getParameter("description");
        String dateStr = request.getParameter("eventDate");
        String timeStr = request.getParameter("eventTime");
        String location = request.getParameter("location");

        // Kiểm tra đầu vào
        if (eventName == null || eventName.isEmpty()
                || eventDescription.isEmpty()
                || dateStr == null || dateStr.isEmpty()
                || timeStr == null || timeStr.isEmpty()) {

            session.setAttribute("error", "Please fill in all required fields!");
            response.sendRedirect("addPerformance");
            return;
        }

        try {
            // Tạo đối tượng Event
            Event event = new Event();
            event.setCustomerId(userId);
            event.setEventName(eventName);
            event.setDescription(eventDescription);
            event.setEventDate(java.sql.Date.valueOf(dateStr));
            event.setEventTime(java.sql.Time.valueOf(timeStr + ":00")); // Đảm bảo định dạng HH:mm:ss
            event.setLocation(location);

            boolean success = eventDAO.insertEvent(event);

            if (success) {
                session.setAttribute("message", "Event created successfully!");
            } else {
                session.setAttribute("error", "Failed to create event!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error while creating event: " + e.getMessage());
        }

        response.sendRedirect("addPerformance");
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
