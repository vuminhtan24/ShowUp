<%-- 
    Document   : changePassword
    Created on : Sep 29, 2025, 6:01:22 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/userProfile.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <!-- Change Password Section -->
        <div class="form-section">
            <h3>Đổi mật khẩu</h3>
            <form class="password-form" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label class="form-label" for="currentPassword">Mật khẩu hiện tại</label>
                        <input type="password" id="currentPassword" name="currentPassword" class="form-input" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="newPassword">Mật khẩu mới</label>
                        <input type="password" id="newPassword" name="newPassword" class="form-input" required>
                        <div class="form-help">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="confirmPassword">Xác nhận mật khẩu mới</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="button" class="btn btn-secondary">Hủy</button>
                    <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                </div>
            </form>
        </div>
    </body>
</html>
