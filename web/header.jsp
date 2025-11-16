<%-- 
    Document   : header
    Created on : Sep 28, 2025
    Author     : VU MINH TAN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">
        <style>
            .navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px 30px;
                background: #fff;
                border-bottom: 1px solid #eee;
            }

            .nav-container {
                display: flex;
                align-items: center;
                width: 100%;
                justify-content: space-between;
            }

            .logo {
                font-size: 22px;
                font-weight: bold;
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .logo-icon {
                background: #4a6cf7;
                color: #fff;
                font-weight: bold;
                border-radius: 8px;
                padding: 5px 8px;
            }

            .nav-links {
                list-style: none;
                display: flex;
                gap: 20px;
            }

            .nav-links a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
            }

            .user-menu {
                position: relative;
                display: inline-block;
            }

            .user-avatar {
                width: 36px;
                height: 36px;
                border-radius: 50%;
                background: #4a6cf7;
                color: #fff;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                cursor: pointer;
                overflow: hidden;
            }

            .user-avatar img {
                width: 100%;
                height: 100%;
                border-radius: 50%;
                object-fit: cover;
            }

            .user-dropdown {
                display: none;
                position: absolute;
                right: 0;
                top: 45px;
                background: #fff;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                z-index: 1000;
                min-width: 150px;
            }

            .user-dropdown a {
                display: block;
                padding: 10px 15px;
                color: #333;
                text-decoration: none;
            }

            .user-dropdown a:hover {
                background: #f4f4f4;
            }

            .auth-buttons a {
                text-decoration: none;
                padding: 8px 14px;
                border-radius: 6px;
                margin-left: 10px;
            }

            .btn-login {
                color: #4a6cf7;
                border: 1px solid #4a6cf7;
            }

            .btn-register {
                background: #4a6cf7;
                color: #fff;
            }
        </style>
        <nav class="navbar">
            <div class="nav-container">
                <div class="logo">
                    <div class="logo-icon">SU</div>
                    ShowUp
                </div>

                <ul class="nav-links">
                    <li><a href="home">Trang chủ</a></li>
                    <li><a href="home#artists">Nghệ sĩ</a></li>
                    <li><a href="addPerformance">Đăng tuyển nghệ sĩ</a></li>
                    <li><a href="events">Sự kiện</a></li>
                    <li><a href="home#about">Về chúng tôi</a></li>
                </ul>

                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <div class="user-menu">
                            <div class="user-avatar" id="avatarBtn">
                                <c:choose>
                                    <c:when test="${not empty avatar}">
                                        <img src="${avatar.url}" alt="avatar">
                                    </c:when>
                                    <c:otherwise>
                                        ${fn:substring(sessionScope.account.username, 0, 2)}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="user-dropdown" id="dropdownMenu">
                                <a href="personalPage?userId=${sessionScope.account.userId}">Trang cá nhân</a>
                                <a href="userProfile?userId=${sessionScope.account.userId}">Cài đặt hồ sơ</a>
                                <a href="MyEvents?userId=${sessionScope.account.userId}">Danh sách bài đăng</a>
                                <a href="logout">Đăng xuất</a>
                            </div>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="auth-buttons">
                            <a href="login" class="btn-login">Đăng nhập</a>
                            <a href="register" class="btn-register">Đăng ký</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>

        <script>
            const avatarBtn = document.getElementById("avatarBtn");
            const dropdown = document.getElementById("dropdownMenu");

            if (avatarBtn) {
                avatarBtn.addEventListener("click", () => {
                    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
                });

                // Ẩn menu khi click ra ngoài
                document.addEventListener("click", (e) => {
                    if (!avatarBtn.contains(e.target) && !dropdown.contains(e.target)) {
                        dropdown.style.display = "none";
                    }
                });
            }
        </script>
