package controller;

import model.User;
import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.ArtistMedia;
import dal.MediaDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private MediaDAO mediaDAO = new MediaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        // Input validation (removed redundant check for spaces)
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("messLogin", "Please enter username and password.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (email.contains(" ") || password.contains(" ")) {
            request.setAttribute("messLogin", "Username and password must not contain spaces.");
            request.setAttribute("email", email); // Retain username
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        User user = userDAO.login(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", user);
            // ====== Lấy avatar từ DB và lưu vào session ======
            ArtistMedia avatar = mediaDAO.getPrimaryMedia(user.getUserId());
            session.setAttribute("avatar", avatar);
            if ("admin".equals(user.getRole())) {
                response.sendRedirect("./admin");
            } else {
                response.sendRedirect("./home");
            }

        } else {
            request.setAttribute("messLogin", "Sai email hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        }

    }
}
