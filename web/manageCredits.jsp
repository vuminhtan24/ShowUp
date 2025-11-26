<%-- 
    Document   : manageCredits
    Created on : Nov 19, 2025, 7:59:48 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <title>Manage User Credits</title>
        <style>
            .pagination {
                display: flex;
                gap: 6px;
                margin-top: 8px;
            }
            .pagination a, .pagination span {
                padding: 4px 10px;
                border-radius: 4px;
                border: 1px solid #ccc;
                text-decoration: none;
            }
            .pagination .active {
                background-color: #333;
                color: #fff;
            }
        </style>
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
        <!-- SEARCH -->
        <form action="manageCredits" method="get">
            <input type="text" name="keyword"
                   placeholder="Search by ID or name"
                   value="${keyword}">
            <button type="submit">Search</button>
        </form>
        <br>

        <!-- TABLE -->
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Credits</th>
            </tr>

            <c:forEach var="u" items="${userList}">
                <tr>
                    <td>${u.userId}</td>
                    <td>${u.username}</td>
                    <td>
                        <form action="manageCredits" method="post" style="display:flex; gap:6px;">
                            <input type="hidden" name="userId" value="${u.userId}">
                            <input type="number" name="credits" value="${u.credits}" style="width:80px;">
                            <button type="submit">Save</button>
                        </form>
                    </td>

                </tr>
            </c:forEach>

            <c:if test="${empty userList}">
                <tr>
                    <td colspan="4" style="text-align:center;">No users found</td>
                </tr>
            </c:if>
        </table>

        <br>

        <!-- PAGINATION -->
        <c:if test="${totalPages > 1}">
            <div>

                <!-- Previous button -->
                <c:if test="${currentPage > 1}">
                    <a href="manageCredits?page=${currentPage - 1}&keyword=${keyword}">Prev</a>
                </c:if>

                <!-- Page numbers -->
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <b>[${i}]</b>
                        </c:when>
                        <c:otherwise>
                            <a href="manageCredits?page=${i}&keyword=${keyword}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                    &nbsp;
                </c:forEach>

                <!-- Next button -->
                <c:if test="${currentPage < totalPages}">
                    <a href="manageCredits?page=${currentPage + 1}&keyword=${keyword}">Next</a>
                </c:if>

            </div>
        </c:if>

    </body>
</html>

