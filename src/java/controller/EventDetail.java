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
import model.Event;

/**
 *
 * @author VU MINH TAN
 */
public class EventDetail extends HttpServlet {
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
            out.println("<title>Servlet EventDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventDetail at " + request.getContextPath() + "</h1>");
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

        // Lấy eventId từ request
        String idParam = request.getParameter("eventId");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("events"); // Nếu không có id, quay về trang danh sách
            return;
        }

        try {
            int eventId = Integer.parseInt(idParam);

            // Lấy chi tiết event từ database
            Event event = eventDAO.getEventById(eventId);

            if (event == null) {
                // Nếu không tìm thấy event, quay về danh sách hoặc hiển thị thông báo
                request.setAttribute("errorMessage", "Sự kiện không tồn tại!");
                request.getRequestDispatcher("events.jsp").forward(request, response);
                return;
            }

            // Đặt event vào request
            request.setAttribute("event", event);

            // Chuyển sang JSP hiển thị chi tiết
            request.getRequestDispatcher("eventDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Nếu eventId không hợp lệ
            response.sendRedirect("events.jsp");
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
        processRequest(request, response);
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
