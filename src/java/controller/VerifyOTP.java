/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAOAccount;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Random;
import model.User;

/**
 *
 * @author VU MINH TAN
 */
@WebServlet("/verifyOTP")
public class VerifyOTP extends HttpServlet {

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
        // Phần này dành cho việc hiển thị ban đầu của biểu mẫu OTP
        // Bạn nên chuyển tiếp đến JSP ở đây, chứ không phải in HTML thô
        request.getRequestDispatcher("./verifyOTP.jsp").forward(request, response);
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
        processRequest(request, response); // Bây giờ sẽ chuyển tiếp đến verifyOTP.jsp
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
        String enteredOtp = request.getParameter("otp");

        Integer sessionOtp = (Integer) session.getAttribute("otp");
        User tempUser = (User) session.getAttribute("tempUser");

        if (sessionOtp == null || tempUser == null) {
            request.setAttribute("error", "Session expired or invalid!");
            request.getRequestDispatcher("/verifyOTP.jsp").forward(request, response);
            return;
        }

        try {
            int enteredOtpInt = Integer.parseInt(enteredOtp);
            if (enteredOtpInt != sessionOtp) {
                request.setAttribute("error", "Incorrect OTP! Please try again.");
                request.getRequestDispatcher("/verifyOTP.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid OTP format! Please enter a number.");
            request.getRequestDispatcher("/verifyOTP.jsp").forward(request, response);
            return;
        }

        DAOAccount dao = new DAOAccount();
        boolean success = dao.createAccount(tempUser);

        if (success) {

            session.removeAttribute("otp");
            session.removeAttribute("tempUser");
            session.setAttribute("currentAcc", tempUser);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Registration failed! Please try again.");
            request.getRequestDispatcher("/verifyOTP.jsp").forward(request, response);
        }
    }


// ✅ HÀM TẠO MÃ KHÁCH HÀNG
    private String generateCustomerCode() {
        String prefix = "CUST";
        int randomNum = new Random().nextInt(10000); // 0 đến 9999
        return prefix + String.format("%04d", randomNum); // CUST0001, CUST2345, ...
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
