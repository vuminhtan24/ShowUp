/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
import dal.MediaDAO;
import dal.EventDAO;
import dal.PerformanceDAO;
import dal.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Artist;

/**
 *
 * @author VU MINH TAN
 */
public class ArtistDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet ArtistDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ArtistDetailServlet at " + request.getContextPath() + "</h1>");
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

        String idStr = request.getParameter("artistId");
        if (idStr == null) {
            response.sendRedirect("home");
            return;
        }

        int artistId;
        try {
            artistId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("home");
            return;
        }

        ArtistDAO artistDao = new ArtistDAO();
        MediaDAO mediaDAO = new MediaDAO();
        EventDAO eventDAO = new EventDAO();
        PerformanceDAO performanceDAO = new PerformanceDAO();  // thêm
        ReviewDAO reviewDAO = new ReviewDAO();                  // thêm

        Artist artist = artistDao.getArtistById(artistId);

        if (artist == null) {
            response.sendRedirect("home");
            return;
        }

        // Gán các danh sách vào request
        request.setAttribute("artist", artist);
        request.setAttribute("mediaList", mediaDAO.getMediaByArtistId(artistId));
        request.setAttribute("avatar", mediaDAO.getPrimaryMedia(artistId));
        request.setAttribute("eventList", eventDAO.getEventById(artistId));
        request.setAttribute("performanceList", performanceDAO.getPerformancesByArtist(artistId)); // thêm
        request.setAttribute("reviewList", reviewDAO.getReviewsByArtist(artistId)); // thêm

        // Chuyển đến trang chi tiết
        request.getRequestDispatcher("artistDetail.jsp").forward(request, response);
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
