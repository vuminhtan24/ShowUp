/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ArtistDAO;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.Artist;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB lưu tạm vào disk nếu vượt
        maxFileSize = -1L, // -1 = không giới hạn kích thước mỗi file
        maxRequestSize = -1L // -1 = không giới hạn tổng kích thước request
)
public class UploadAvatarServlet extends HttpServlet {

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
            out.println("<title>Servlet UploadAvatarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadAvatarServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        // 🔹 Chỉ cho phép nghệ sĩ upload avatar
        if (!"artist".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("userProfile?msg=NotArtist");
            return;
        }

        // Lấy file
        Part filePart = request.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect("userProfile?msg=NoFile");
            return;
        }

        // Lưu file vật lý
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadDir = getServletContext().getRealPath("/upload/avatar/");
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String savedFilePath = uploadDir + File.separator + fileName;
        filePart.write(savedFilePath);

        // Đường dẫn tương đối để lưu DB
        String relativePath = "upload/avatar/" + fileName;

        // 🔹 Lấy artist_id từ user_id
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = artistDAO.getArtistByUserId(user.getUserId());
        if (artist == null) {
            response.sendRedirect("userProfile?msg=NoArtistProfile");
            return;
        }

        int artistId = artist.getId();

        // 🔹 Lưu avatar (hoặc cập nhật)
        MediaDAO mediaDAO = new MediaDAO();
        mediaDAO.saveOrUpdateAvatar(artistId, relativePath);

        response.sendRedirect("userProfile");
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf('=') + 2, s.length() - 1);
            }
        }
        return "";
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
