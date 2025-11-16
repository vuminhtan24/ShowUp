<%-- 
    Document   : registerArtist
    Created on : Sep 29, 2025
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký Nghệ sĩ - ShowUp</title>
        <link href="${pageContext.request.contextPath}/css/userProfile.css" rel="stylesheet">
    </head>
    <body>
        <!-- Navigation -->
        <nav class="navbar">
            <div class="nav-container">
                <a href="${pageContext.request.contextPath}/home" class="logo">
                    <div class="logo-icon">SU</div>
                    ShowUp
                </a>
                <ul class="nav-links">
                    <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                    <li><a href="${pageContext.request.contextPath}/artists">Nghệ sĩ</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">Về chúng tôi</a></li>
                </ul>
                <div class="user-menu">
                    <div class="user-avatar">
                        <c:out value="${fn:substring(account.username,0,1)}" />
                    </div>
                    <span><c:out value="${account.username}" /></span>
                </div>
            </div>
        </nav>

        <!-- Main Content -->
        <main class="main-content">
            <div class="container profile-layout">
                <!-- Sidebar -->
                <aside class="sidebar">
                    <div class="profile-avatar">
                        <c:choose>
                            <c:when test="${not empty mediaList}">
                                <img src="${mediaList[0].url}" alt="avatar" style="width:72px;height:72px;border-radius:50%">
                            </c:when>
                            <c:otherwise>
                                ${fn:substring(user.username,0,2)}
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h2 class="profile-name">${user.username}</h2>
                    <p class="profile-email">${user.email}</p>
                    <p class="profile-phone">${user.phone}</p>

                    <ul class="sidebar-menu">
                        <li><a href="userProfile">Thông tin tài khoản</a></li>

                        <c:if test="${user.role eq 'artist'}">
                            <li><a href="#artist">Thông tin nghệ sĩ</a></li>
                            </c:if>

                        <c:if test="${not empty mediaList}">
                            <li><a href="#media">Ảnh & Media</a></li>
                            </c:if>

                        <!-- Nút đăng ký trở thành nghệ sĩ đối tác -->
                        <c:if test="${user.role ne 'artist'}">
                            <li>
                                <a href="registerArtist" class="active" style="color:white; font-weight:600;">
                                    🎶 Trở thành nghệ sĩ ShowUp
                                </a>
                            </li>
                        </c:if>

                        <li><a href="#danger" style="color:#ef4444">Vùng nguy hiểm</a></li>
                    </ul>
                </aside>

                <!-- Form Content -->
                <div class="profile-content">
                    <h1 class="section-title">Đăng ký trở thành Nghệ sĩ ShowUp</h1>
                    <p class="section-subtitle">Điền thông tin dưới đây để tham gia cộng đồng nghệ sĩ của chúng tôi 🎶</p>

                    <form class="artist-form" action="registerArtist" method="POST">
                        <div class="form-grid">
                            <div class="form-group">
                                <label class="form-label" for="stageName">Tên nghệ danh</label>
                                <input type="text" id="stageName" name="stageName" class="form-input" required>
                            </div>

                            <div class="form-group">
                                <label class="form-label" for="genre">Thể loại chính</label>
                                <select id="genre" name="genre" class="form-select" required>
                                    <option value="">Chọn thể loại</option>
                                    <option value="pop">Pop</option>
                                    <option value="rock">Rock</option>
                                    <option value="jazz">Jazz</option>
                                    <option value="classical">Classical</option>
                                    <option value="folk">Folk</option>
                                    <option value="electronic">Electronic</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="form-label" for="experience">Kinh nghiệm</label>
                                <select id="experience" name="experience" class="form-select">
                                    <option value="">Chọn mức độ</option>
                                    <option value="new">Mới (&lt; 2 năm)</option>
                                    <option value="experienced">Có kinh nghiệm (2-5 năm)</option>
                                    <option value="veteran">Lão làng (&gt; 5 năm)</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="form-label" for="status">Trạng thái hoạt động</label>
                                <select id="status" name="status" class="form-select">
                                    <option value="available">Đang hoạt động</option>
                                    <option value="booking">Nhận booking</option>
                                    <option value="tour">Đang tour</option>
                                    <option value="inactive">Tạm nghỉ</option>
                                </select>
                            </div>

                            <div class="form-group full-width">
                                <label class="form-label" for="instruments">Nhạc cụ/Kỹ năng</label>
                                <input type="text" id="instruments" name="instruments" class="form-input" placeholder="Ví dụ: Vocal, Guitar, Piano">
                            </div>

                            <div class="form-group full-width">
                                <label class="form-label" for="achievements">Thành tích nổi bật</label>
                                <textarea id="achievements" name="achievements" class="form-textarea" placeholder="Chia sẻ những thành tích, giải thưởng hoặc dự án âm nhạc đáng chú ý..."></textarea>
                            </div>

                            <div class="form-group full-width">
                                <label class="form-label" for="bio">Giới thiệu bản thân</label>
                                <textarea id="bio" name="bio" class="form-textarea" placeholder="Viết vài dòng về bản thân bạn..."></textarea>
                            </div>
                        </div>

                        <div class="form-actions">
                            <a href="${pageContext.request.contextPath}/userProfile" class="btn btn-secondary">Hủy</a>
                            <button type="submit" class="btn btn-primary">Gửi đăng ký</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>

    </body>
</html>
