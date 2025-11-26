<%-- 
    Document   : artistVerification
    Created on : Nov 26, 2025, 6:33:41 PM
    Author     : VU MINH TAN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Artist Verification</title>
        <!-- DashMin CSS -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    </head>

    <body>
        <header>
            <div class="container">
                <div class="nav">
                    <a href="${pageContext.request.contextPath}/home" class="brand">
                        <div class="brand-badge">SU</div>
                        <div>ShowUp</div>
                    </a>
                    <a href="manageCredits">
                        <div class="row">Quản lý điểm</div>
                    </a>
                    <a href="artistVerification">
                        <div class="row">Kiểm duyệt nghệ sĩ</div>
                    </a>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <div class="row">
                                <span>Xin chào, ${sessionScope.account.username}</span>
                                <a href="logout" class="pill">Đăng xuất</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <a href="login" class="cta">Đăng nhập</a>
                                <a href="register" class="pill">Đăng ký</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </header>

        <div class="container-fluid pt-4 px-4">
            <div class="bg-light rounded p-4">
                <h4 class="mb-4">Danh sách nghệ sĩ chờ kiểm duyệt</h4>
                <form method="get" action="artistVerification" class="mb-3" style="display:flex; gap:10px; max-width:400px;">
                    <input 
                        type="text" 
                        name="keyword" 
                        class="form-control"
                        placeholder="Tìm theo stage name, username, email..."
                        value="${param.keyword}"
                        />
                    <button type="submit" class="btn btn-primary">Tìm</button>
                </form>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped align-middle">
                        <thead>
                            <tr>
                                <th>Artist ID</th>
                                <th>Stage Name</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Genres</th>
                                <th>Created At</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="a" items="${artistList}">
                                <tr>
                                    <td>${a.artistId}</td>
                                    <td>${a.stageName}</td>
                                    <td>${a.username}</td>
                                    <td>${a.email}</td>
                                    <td>${a.phone}</td>

                                    <!-- Genres (list -> string) -->
                                    <td>
                                        <c:forEach var="g" items="${a.genres}">
                                            <span class="badge bg-primary">${g}</span>
                                        </c:forEach>
                                    </td>

                                    <td>${a.verified_at}</td>

                                    <td>
                                        <span class="badge
                                              <c:choose>
                                                  <c:when test="${a.verificationStatus == 'pending'}">bg-warning</c:when>
                                                  <c:when test="${a.verificationStatus == 'approved'}">bg-success</c:when>
                                                  <c:otherwise>bg-danger</c:otherwise>
                                              </c:choose>
                                              ">
                                            ${a.verificationStatus}
                                        </span>
                                    </td>

                                    <td>

                                        <!-- Button: Xem hồ sơ -->
                                        <a href="artistDetails?id=${a.artistId}" 
                                           class="btn btn-sm btn-info mb-1">
                                            Chi tiết
                                        </a>

                                        <!-- Approve -->
                                        <form action="approveArtist" method="post" style="display:inline-block">
                                            <input type="hidden" name="artistId" value="${a.artistId}">
                                            <button type="submit" class="btn btn-sm btn-success">
                                                ✔ Approve
                                            </button>
                                        </form>

                                        <!-- Reject -->
                                        <form action="rejectArtist" method="post" style="display:inline-block">
                                            <input type="hidden" name="artistId" value="${a.artistId}">
                                            <button type="submit" class="btn btn-sm btn-danger">
                                                ✖ Reject
                                            </button>
                                        </form>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>

    </body>
</html>
