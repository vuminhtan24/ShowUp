/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Artist;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
public class RegisterArtistController extends HttpServlet {

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
            out.println("<title>Servlet RegisterArtistController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterArtistController at " + request.getContextPath() + "</h1>");
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
        // Nếu chưa login thì redirect sang login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Nếu đã login -> show form
        request.getRequestDispatcher("registerArtist.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("account");

        // Lấy dữ liệu từ form
        String stageName = request.getParameter("stageName");
        String genre = request.getParameter("genre");
        String experience = request.getParameter("experience");
        String status = request.getParameter("status");
        String instruments = request.getParameter("instruments");
        String achievements = request.getParameter("achievements");
        String bio = request.getParameter("bio");

        ArtistDAO artistDAO = new ArtistDAO();
        UserDAO userDAO = new UserDAO();

        // ⚠️ Check đã đăng ký trước đó chưa
        if (artistDAO.existsArtist(user.getUserId())) {
            request.setAttribute("error", "Bạn đã đăng ký nghệ sĩ rồi, vui lòng chờ admin duyệt.");
            request.getRequestDispatcher("registerArtist.jsp").forward(request, response);
            return;
        }

        // Tạo Artist object
        Artist artist = new Artist();
        artist.setId(user.getUserId()); // userId = khóa ngoại
        artist.setStageName(stageName);
        artist.setGenre(genre);
        artist.setExperience(experience);
        artist.setStatus(status);
        artist.setInstruments(instruments);
        artist.setAchievements(achievements);
        artist.setBio(bio);

        boolean success = artistDAO.addArtist(artist);

        if (success) {
            // ✅ Update role user thành "artist" (dù trạng thái vẫn pending)
            userDAO.updateUserRole(user.getUserId(), "artist");
            user.setRole("artist"); // update trong session luôn
            session.setAttribute("account", user);

            request.setAttribute("message", "Đăng ký thành công! Tài khoản của bạn đang chờ admin duyệt.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký nghệ sĩ thất bại, vui lòng thử lại.");
            request.getRequestDispatcher("registerArtist.jsp").forward(request, response);
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
