package controller;

import model.User;
import dal.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Random;
import static org.apache.tomcat.jakartaee.commons.lang3.StringUtils.isBlank;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        //String role = request.getParameter("role"); // customer/artist
        String messRegister;

        if (username.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {
            messRegister = "Please fill all of field!";
            returnInputValue(request, response, email, "", phone, messRegister);
            return;
        }
        if (isBlank(username) || isBlank(email)
                || isBlank(phone)
                || isBlank(password) || isBlank(confirmPassword)) {
            messRegister = "Fields must not be empty or contain only spaces";
            returnInputValue(request, response, email, "", phone, messRegister);
            return;
        }
        if (!password.equals(confirmPassword)) {
            messRegister = "Password and Confirm Password do not match!";
            returnInputValue(request, response, email, username, phone, messRegister);
            return;
        }
        if (password.length() < 6) {
            messRegister = "Password must be at least 6 characters!";
            returnInputValue(request, response, email, username, phone, messRegister);
            return;
        }
        User u = new User(0, username, password, email, phone, "customer", "active");
        // Sinh OTP
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000);

        try {
            SendMail.send(email, "OTP Verification", "Your OTP code is: " + otp);
        } catch (Exception e) {
            e.printStackTrace();
            messRegister = "Can't send OTP. Please try again later.";
            returnInputValue(request, response, email, username, phone, messRegister);
            return;
        }
        // Lưu vào session
        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", u);
        response.sendRedirect(request.getContextPath() + "/verifyOTP");
    }

    public void returnInputValue(HttpServletRequest request,
            HttpServletResponse response, String email, String username,
            String phone, String messRegister)
            throws ServletException, IOException {
        request.setAttribute("email", email);
        request.setAttribute("username", username);
        request.setAttribute("phone", phone);
        request.setAttribute("messRegister", messRegister);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
