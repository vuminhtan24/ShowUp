/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
import dal.PaidInfoAccessDAO;
import dal.TransactionHistoryDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
public class ViewPhoneServlet extends HttpServlet {

    private static final int PHONE_COST = 5;

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
            out.println("<title>Servlet ViewPhoneServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewPhoneServlet at " + request.getContextPath() + "</h1>");
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
        doPost(request, response);
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
        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("account");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        int userId = user.getUserId();

        // Lấy artistId từ form
        int artistId = Integer.parseInt(request.getParameter("artistId"));
        String infoType = "phone";

        UserDAO userDAO = new UserDAO();
        PaidInfoAccessDAO accessDAO = new PaidInfoAccessDAO();
        TransactionHistoryDAO transDAO = new TransactionHistoryDAO();
        ArtistDAO artistDAO = new ArtistDAO();

        try {
            // Kiểm tra đã mua quyền xem chưa
            boolean purchased = accessDAO.hasAccess(userId, artistId, infoType);

            if (!purchased) {
                int credits = userDAO.getCredits(userId);

                if (credits < PHONE_COST) {
                    request.getSession().setAttribute("phoneError", "Bạn không đủ điểm để xem số điện thoại!");
                    response.sendRedirect("artistDetail?artistId=" + artistId);
                    return;
                }

                // Trừ điểm
                userDAO.decreaseCredits(userId, PHONE_COST);

                // Ghi lịch sử giao dịch
                transDAO.addTransaction(userId, -PHONE_COST,
                        "Mua thông tin phone của nghệ sĩ ID = " + artistId);

                // Lưu quyền truy cập
                accessDAO.insertAccess(userId, artistId, infoType);
            }

            // Lấy số điện thoại thực
            String realPhone = artistDAO.getPhoneByArtistId(artistId);

            Map<Integer, String> phoneVisibleMap = (Map<Integer, String>) session.getAttribute("phoneVisibleMap");
            if (phoneVisibleMap == null) {
                phoneVisibleMap = new HashMap<>();
            }
            phoneVisibleMap.put(artistId, realPhone);
            session.setAttribute("phoneVisibleMap", phoneVisibleMap);
            request.getSession().setAttribute("phoneVisible_" + artistId, realPhone);

            response.sendRedirect("artistDetail?artistId=" + artistId);

        } finally {
            // Đảm bảo luôn đóng connection
            userDAO.closeConnection();
            accessDAO.closeConnection();
            transDAO.closeConnection();
            artistDAO.closeConnection();
        }

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
