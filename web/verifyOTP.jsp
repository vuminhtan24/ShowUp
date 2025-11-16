<%-- 
    Document   : verifyOTP
    Created on : Sep 25, 2025, 12:41:59 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xac nhan otp</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    </head>
    <body>
        <header>
            <div class="container nav">
                <div class="brand">
                    <div class="brand-badge">SU</div>
                    <span>ShowUp</span>
                </div>
                <a href="home" class="cta">Trang chủ</a>
            </div>
        </header>
        <div class="container" style="max-width:420px; margin-top:60px;">
            <div class="card">
                <div class="card-body" style="padding:32px; gap:20px;">
                    <h2 style="text-align:center; margin:0;">Đăng nhập</h2>

                    <form action="verifyOTP" method="post" class="form">
                        <label class="label" for="username">OTP</label>
                        <input type="text" name="otp" placeholder="Nhap OTP Code" required />
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" style="margin-top:10px;">${error}</div>
                        </c:if>
                        <button type="submit" class="btn btn-primary">Verify</button>
                    </form>
                </div>
            </div>
        </div>

        <footer>
            <div class="container">
                © 2025 ShowUp. All rights reserved.
            </div>
        </footer>
    </body>
</html>
