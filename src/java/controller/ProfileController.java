/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
import dal.ArtistMediaDAO;
import dal.DAOAccount;
import dal.MediaDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import javax.print.attribute.standard.Media;
import model.Artist;
import model.ArtistMedia;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
public class ProfileController extends HttpServlet {

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
            out.println("<title>Servlet ProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileController at " + request.getContextPath() + "</h1>");
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

        // Lấy object User từ session
        User user = (User) session.getAttribute("account");
        int userId = user.getUserId();  // ✅ chuẩn

        // Lấy thông tin user từ DB (nếu cần cập nhật dữ liệu mới nhất)
        DAOAccount acc = new DAOAccount();
        user = acc.getAccountById(userId);
        request.setAttribute("user", user);
        MediaDAO mediaDAO = new MediaDAO();
        ArtistMedia avatar = mediaDAO.getPrimaryMedia(userId);
        request.setAttribute("avatar", avatar);
        List<ArtistMedia> mediaList = mediaDAO.getMediaByArtistId(userId);
        request.setAttribute("mediaList", mediaList);

        // Nếu role là "artist" thì load thông tin artist + media
        if ("artist".equalsIgnoreCase(user.getRole())) {
            ArtistDAO artistDAO = new ArtistDAO();
            Artist artist = artistDAO.getArtistByUserId(userId);
            request.setAttribute("artist", artist);
        }

        // Forward sang profile.jsp
        RequestDispatcher rd = request.getRequestDispatcher("userProfile.jsp");
        rd.forward(request, response);
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
