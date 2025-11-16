<%-- 
    Document   : userProfile
    Created on : Sep 29, 2025, 5:41:30 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thông tin cá nhân - ShowUp</title>
        <link href="${pageContext.request.contextPath}/css/userProfile.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <!-- Main Content -->
        <main class="main-content">
            <div class="container">
                <div class="profile-layout">
                    <!-- Sidebar -->
                    <aside class="sidebar">
                        <div class="profile-avatar">
                            <c:choose>
                                <c:when test="${not empty avatar}">
                                    <img src="${avatar.url}" alt="avatar" style="width:72px;height:72px;border-radius:50%">
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
                            <li><a href="#profile" class="active">Thông tin tài khoản</a></li>

                            <c:if test="${user.role eq 'artist'}">
                                <li><a href="#artist">Thông tin nghệ sĩ</a></li>
                                </c:if>

                            <c:if test="${not empty mediaList}">
                                <li><a href="#media">Ảnh & Media</a></li>
                                </c:if>
                            <!-- Nút đăng ký trở thành nghệ sĩ đối tác -->
                            <c:if test="${user.role ne 'artist'}">
                                <li>
                                    <a href="registerArtist" style="color:#2563eb; font-weight:600;">
                                        🎶 Trở thành nghệ sĩ ShowUp
                                    </a>
                                </li>
                            </c:if>
                            <li><a href="#danger" style="color:#ef4444">Vùng nguy hiểm</a></li>
                        </ul>
                    </aside>

                    <!-- Main Profile Content -->
                    <div class="profile-content">
                        <h1 class="section-title">Thông tin cá nhân</h1>

                        <!-- Avatar Upload Section -->
                        <div class="form-section">
                            <h3>Ảnh đại diện</h3>

                            <form class="avatar-form" method="POST" enctype="multipart/form-data" action="uploadAvatar" id="avatarForm">
                                <div class="avatar-upload">
                                    <div class="current-avatar">
                                        <c:choose>
                                            <c:when test="${not empty avatar}">
                                                <img id="previewImage"
                                                     src="${pageContext.request.contextPath}/${avatar.url}"
                                                     alt="avatar" style="width:72px;height:72px;border-radius:50%;object-fit:cover">
                                            </c:when>
                                            <c:otherwise>
                                                <div id="previewImage"
                                                     style="width:72px;height:72px;border-radius:50%;background:#ccc;
                                                     display:flex;align-items:center;justify-content:center;
                                                     font-weight:bold;color:white;">
                                                    ${fn:substring(user.username,0,2)}
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="avatar-actions" style="margin-top:10px;">
                                        <div class="file-input-wrapper">
                                            <input type="file" id="avatar" name="avatar" class="file-input" accept="image/*" hidden>
                                            <label for="avatar" class="btn btn-secondary">📷 Chọn ảnh mới</label>
                                        </div>

                                        <span id="fileName" style="margin-left:10px;color:#555;"></span>

                                        <button type="button" id="confirmBtn" class="btn btn-primary" style="display:none;">Xác nhận</button>
                                        <button type="submit" class="btn btn-secondary" name="action" value="delete">Xóa ảnh</button>
                                    </div>
                                </div>

                                <c:if test="${not empty sessionScope.message}">
                                    <div class="alert ${sessionScope.messageType eq 'success' ? 'alert-success' : 'alert-danger'}" 
                                         style="margin-top: 15px; padding: 10px; border-radius: 5px;">
                                        ${sessionScope.message}
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                    <c:remove var="messageType" scope="session"/>
                                </c:if>
                            </form>
                        </div>
                        <!-- Personal Information Form -->
                        <div class="form-section" id="profile">
                            <h3>Thông tin cơ bản</h3>
                            <form class="profile-form" method="POST" action="updateProfile">
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label class="form-label" for="username">Họ và tên</label>
                                        <input type="text" id="username" name="username" class="form-input" value="${user.username}" required>
                                    </div>

                                    <div class="form-group">
                                        <label class="form-label" for="email">Email</label>
                                        <input type="email" id="email" name="email" class="form-input" value="${user.email}" required>
                                        <div class="form-help">Email này sẽ được sử dụng để đăng nhập và nhận thông báo</div>
                                    </div>

                                    <div class="form-group">
                                        <label class="form-label" for="phone">Số điện thoại</label>
                                        <input type="tel" id="phone" name="phone" class="form-input" value="${user.phone}">
                                    </div>
                                </div>

                                <div class="form-actions">
                                    <button type="reset" class="btn btn-secondary">Hủy</button>
                                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                </div>
                            </form>
                        </div>

                        <!-- Artist Information -->
                        <c:if test="${user.role eq 'artist'}">
                            <div class="form-section" id="artist">
                                <h3>Thông tin nghệ sĩ</h3>
                                <form class="artist-form" method="POST" action="updateArtist">
                                    <div class="form-grid">
                                        <div class="form-group">
                                            <label class="form-label" for="stageName">Tên nghệ danh</label>
                                            <input type="text" id="stageName" name="stageName" class="form-input" value="${artist.stageName}">
                                        </div>

                                        <div class="form-group full-width">
                                            <label class="form-label" for="bio">Giới thiệu bản thân</label>
                                            <textarea id="bio" name="bio" class="form-textarea">${artist.bio}</textarea>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <button type="reset" class="btn btn-secondary">Hủy</button>
                                        <button type="submit" class="btn btn-primary">Cập nhật thông tin nghệ sĩ</button>
                                    </div>
                                </form>
                            </div>
                        </c:if>

                        <!-- Danger Zone -->
                        <div class="form-section" id="danger">
                            <h3 style="color: #ef4444;">Vùng nguy hiểm</h3>
                            <form class="danger-form" method="POST" action="accountDanger">
                                <p style="color: #666; margin-bottom: 1.5rem;">
                                    Các hành động dưới đây không thể hoàn tác. Vui lòng cân nhắc kỹ trước khi thực hiện.
                                </p>
                                <div class="form-actions">
                                    <button type="submit" name="action" value="deactivate" class="btn btn-secondary">Vô hiệu hóa tài khoản</button>
                                    <button type="submit" name="action" value="delete" class="btn btn-danger">Xóa tài khoản vĩnh viễn</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script>
            document.getElementById("avatar").addEventListener("change", function (e) {
                const file = e.target.files[0];
                const preview = document.getElementById("previewImage");
                const fileName = document.getElementById("fileName");
                const confirmBtn = document.getElementById("confirmBtn");

                if (file) {
                    fileName.textContent = file.name; // hiển thị tên file
                    confirmBtn.style.display = "inline-block"; // hiện nút xác nhận

// Hiển thị preview ảnh
                    const reader = new FileReader();
                    reader.onload = function (ev) {
                        if (preview.tagName === "IMG") {
                            preview.src = ev.target.result;
                        } else {
// nếu preview là <div> (chưa có ảnh)
                            const img = document.createElement("img");
                            img.src = ev.target.result;
                            img.style.width = "72px";
                            img.style.height = "72px";
                            img.style.borderRadius = "50%";
                            img.style.objectFit = "cover";
                            preview.replaceWith(img);
                            img.id = "previewImage";
                        }
                    };
                    reader.readAsDataURL(file);
                } else {
                    fileName.textContent = "";
                    confirmBtn.style.display = "none";
                }
            });

            document.getElementById("confirmBtn").addEventListener("click", function () {
                document.getElementById("avatarForm").submit();
            });
        </script>
    </body>
</html>
