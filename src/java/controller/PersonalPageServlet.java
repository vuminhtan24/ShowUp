/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
import dal.ArtistMediaDAO;
import dal.MediaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.Artist;
import model.ArtistMedia;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class PersonalPageServlet extends HttpServlet {

    private ArtistDAO artistDAO = new ArtistDAO();
    private ArtistMediaDAO mediaDAO = new ArtistMediaDAO();

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
            out.println("<title>Servlet PersonalPageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PersonalPageServlet at " + request.getContextPath() + "</h1>");
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

        // ✅ Luôn lấy session (tạo mới nếu chưa có)
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");

        // ✅ Xác định userId từ query hoặc từ session
        String userIdParam = request.getParameter("userId");
        int userId;

        if (userIdParam != null && !userIdParam.isEmpty()) {
            userId = Integer.parseInt(userIdParam);
        } else if (account != null) {
            userId = account.getUserId();
        } else {
            // ❗Không có ai đăng nhập và không chỉ định userId => buộc login
            response.sendRedirect("login");
            return;
        }

        // ✅ Lấy thông tin artist (của người đang xem)
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = artistDAO.getArtistByUserId(userId);
        request.setAttribute("artist", artist);
        
        // ✅ Lấy media tương ứng
        ArtistMediaDAO mediaDAO = new ArtistMediaDAO();
        List<ArtistMedia> mediaList = mediaDAO.getMediaByArtistId(userId);
        request.setAttribute("mediaList", mediaList);

        // ✅ Nếu người đang xem là chính mình
        boolean isOwner = (account != null && account.getUserId() == userId);
        request.setAttribute("isOwner", isOwner);

        // ✅ Gửi sang JSP
        request.getRequestDispatcher("personalPage.jsp").forward(request, response);
        
        // Take avatar
        MediaDAO mediaAvatar = new MediaDAO();
        ArtistMedia avatar = mediaAvatar.getPrimaryMedia(userId);
        request.setAttribute("avatar", avatar);
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
        if (account == null) {
            response.sendRedirect("login");
            return;
        }
        int userId = account.getUserId();

        // Thư mục upload thực tế trong server
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Nhận file ảnh từ form
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = new File(filePart.getSubmittedFileName()).getName();
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Lưu đường dẫn ảnh vào DB
            String dbUrl = "uploads/" + fileName;
            mediaDAO.insertMedia(userId, dbUrl, "image");
        }

        response.sendRedirect("personalPage"+"?userId="+userId); // load lại trang cá nhân
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
