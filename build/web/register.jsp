<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng ky</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container" style="max-width:420px; margin-top:60px;">
            <div class="card">
                <div class="card-body" style="padding:32px; gap:20px;">
                    <h2 style="text-align:center; margin:0;">Đăng ký</h2>

                    <form action="register" method="post" class="form">
                        <label class="label" for="username">Họ và tên</label>
                        <input type="text" id="username" name="username" class="control" placeholder="Nhập tên" required>
                        <label class="label" for="username">Email</label>
                        <input type="text" id="email" name="email" class="control" placeholder="Nhập Email" required>
                        <label class="label" for="username">Số điện thoại</label>
                        <input type="text" id="phone" name="phone" class="control" placeholder="Nhập Số điện thoại" required>

                        <label class="label" for="password">Mật khẩu</label>
                        <input type="password" id="password" name="password" class="control" placeholder="Nhập mật khẩu" required>
                        <label class="label" for="password">Mật khẩu xác nhận </label>
                        <input type="password" id="password" name="confirmPassword" class="control" placeholder="Nhập mật khẩu" required>
                        <h3 class="text-danger text-center">${messLogin}</h3>
                        <button type="submit" class="btn btn-primary">Đăng ký</button>
                    </form>

                    <p class="muted" style="text-align:center; font-size:14px;">
                        Đã có tài khoản? <a href="login" style="color:var(--secondary); font-weight:600;">Đăng nhập</a>
                    </p>
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
