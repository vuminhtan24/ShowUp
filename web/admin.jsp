<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ShowUp — Trang quản trị</title>
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
                    <a href="#home" class="brand">
                        <div class="brand-badge">SU</div>
                        <div>ShowUp</div>
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

        <div class="container">
            <div class="row" style="margin-bottom:12px">
                <h2 style="margin:0">Bảng điều khiển quản trị</h2>
            </div>

            <div class="grid">
                <!-- Người dùng -->
                <div>
                    <h3>Người dùng</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th><th>Tên</th><th>Vai trò</th><th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${userList}">
                                <tr>
                                    <td>${u.userId}</td>
                                    <td>${u.username}</td>
                                    <td>${u.role}</td>
                                    <td>${u.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <!-- PHÂN TRANG USER -->
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalUserPages}">
                            <c:choose>
                                <c:when test="${i == userPage}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="admin?pageUser=${i}&pageBooking=${bookingPage}&pagePayment=${paymentPage}">
                                        ${i}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>

                </div>
                <!-- Event -->
                <div>
                    <h3>Event</h3>
                    <table >
                        <thead>
                            <tr>
                                <th>Mã</th><th>ID Khách hàng</th><th>Tên Sự kiện</th><th>Miêu tả</th><th>Ngày</th>
                                <th>Giờ</th><th>Địa điểm</th><th>Trạng thái</th><th>Ngày tạo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="e" items="${eventsList}">
                                <tr>
                                    <td>#Ev-${e.eventId}</td>
                                    <td>${e.customerId}</td>
                                    <td>${e.eventName}</td>
                                    <td>${e.description}</td>
                                    <td><fmt:formatDate value="${e.eventDate}" pattern="dd/MM/yyyy" /></td>
                                    <td>${e.eventTime}</td>
                                    <td>${e.location}</td>
                                    <td>
                                        <form action="admin" method="post">
                                            <input type="hidden" name="eventId" value="${e.eventId}" />
                                            <select name="status" onchange="this.form.submit()">
                                                <option value="Pending" ${e.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                                <option value="Approved" ${e.status == 'Approved' ? 'selected' : ''}>Approved</option> <!-- ở đây có vấn đề -->
                                                <option value="Closed" ${e.status == 'Closed' ? 'selected' : ''}>Closed</option>
                                                <option value="Cancelled" ${e.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                            </select>
                                        </form>
                                    </td>
                                    <td><fmt:formatDate value="${e.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>

                    <!-- PHÂN TRANG EVENT -->
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalEventsPages}">
                            <c:choose>
                                <c:when test="${i == eventsPage}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="admin?eventsPage=${i}&userPage=${userPage}&bookingPage=${bookingPage}&paymentPage=${paymentPage}">
                                        ${i}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>

                <!-- Đơn đặt -->
                <div>
                    <h3>Đơn đặt</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Mã</th><th>ID Nghệ sĩ</th><th>Ngày tạo</th><th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${bookingList}">
                                <tr>
                                    <td>#BK-${b.bookingId}</td>
                                    <td>${b.artistId}</td>
                                    <td><fmt:formatDate value="${b.createdAt}" pattern="dd/MM/yyyy"/></td>
                                    <td>${b.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- PHÂN TRANG BOOKING -->
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalBookingPages}">
                            <c:choose>
                                <c:when test="${i == bookingPage}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="admin?pageBooking=${i}&pagePayment=${paymentPage}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>

                <!-- Thanh toán -->
                <div>
                    <h3>Thanh toán</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Đơn</th><th>Số tiền</th><th>Phương thức</th><th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${paymentList}">
                                <tr>
                                    <td>#BK-${p.bookingId}</td>
                                    <td><fmt:formatNumber value="${p.amount}" type="currency" currencySymbol="₫"/></td>
                                    <td>${p.paymentMethod}</td>
                                    <td>${p.paymentStatus}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- PHÂN TRANG PAYMENT -->
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPaymentPages}">
                            <c:choose>
                                <c:when test="${i == paymentPage}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="admin?pagePayment=${i}&pageBooking=${bookingPage}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>
