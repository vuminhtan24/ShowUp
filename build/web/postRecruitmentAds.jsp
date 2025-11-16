<%-- 
    Document   : postRecruitmentAds
    Created on : Nov 6, 2025
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng tiết mục biểu diễn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <style>
            body {
                background: #f5f7fa;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .container {
                max-width: 700px;
                margin-top: 40px;
                background: #fff;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }

            h3 {
                text-align: center;
                color: #343a40;
                margin-bottom: 25px;
                font-weight: 600;
            }

            .form-control {
                border-radius: 8px;
                padding: 10px 12px;
            }

            textarea {
                resize: none;
            }

            .btn-primary {
                width: 100%;
                padding: 10px;
                font-weight: 600;
                background: #007bff;
                border: none;
                border-radius: 8px;
                transition: 0.3s;
            }

            .btn-primary:hover {
                background: #0056b3;
                transform: translateY(-1px);
            }

            .form-label {
                font-weight: 500;
                color: #555;
            }
        </style>
    </head>

    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <h3>Đăng tiết mục biểu diễn</h3>
            <form action="addPerformance" method="post" ">
                <input type="hidden" name="artistId" value="${artist.id}">

                <div class="mb-3">
                    <label for="title" class="form-label">Tên tiết mục</label>
                    <input type="text" class="form-control" id="title" name="eventName" placeholder="Nhập tên tiết mục..." required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" name="description" rows="3" placeholder="Mô tả nội dung tiết mục..." required></textarea>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="eventDate" class="form-label">Ngày biểu diễn</label>
                        <input type="date" class="form-control" id="eventDate" name="eventDate" required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label for="eventTime" class="form-label">Giờ biểu diễn</label>
                        <input type="time" class="form-control" id="eventTime" name="eventTime" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="location" class="form-label">Địa điểm</label>
                    <input type="text" class="form-control" id="location" name="location" placeholder="Nhập địa điểm tổ chức..." required>
                </div>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                    <c:remove var="error" scope="session"/>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success" role="alert">
                        ${message}
                    </div>
                    <c:remove var="message" scope="session"/>
                </c:if>
                

                <button type="submit" class="btn btn-primary">Đăng tiết mục</button>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
