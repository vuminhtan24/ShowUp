<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết sự kiện</title>
        <link href="${pageContext.request.contextPath}/css/eventDetail.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="header.jsp"/>

        <div class="page-wrapper">

            <!-- LEFT SIDE -->
            <div class="left-content">

                <div class="event-title">${event.eventName}</div>

                <div class="event-description">
                    ${event.description}
                </div>

                <div class="info-box">
                    <div class="info-row"><span class="info-label">Mã sự kiện:</span> #Ev-${event.eventId}</div>
                    <div class="info-row"><span class="info-label"> Ngày diễn ra:</span> ${event.eventDate} - ${event.eventTime}</div>
                    <div class="info-row"><span class="info-label">Địa điểm:</span> ${event.location}</div>
                    <div class="info-row"><span class="info-label">Trạng thái:</span> ${event.status}</div>
                    <div class="info-row"><span class="info-label">Ngày tạo:</span> ${event.createdAt}</div>
                </div>

            </div>

            <!-- RIGHT SIDEBAR -->
            <div class="right-sidebar">

                <div class="sidebar-box">
                    <div class="section-title">Thông tin người đăng</div>

                    <div class="user-info">
                        <div class="user-avatar"></div>
                        <div class="username">${event.customerName}</div>
                        <c:if test="${not empty sessionScope['phoneError']}">
                            <p class="muted" style="color:red;">${sessionScope['phoneError']}</p>
                            <c:remove var="phoneError" scope="session"/>
                        </c:if>
                        <!-- Lấy số điện thoại thực từ Map trong session -->
                        <c:set var="realPhone" value="${sessionScope.phoneVisibleMap[event.customerId]}" />
                        <c:set var="realGmail" value="${sessionScope.gmailVisibleMap[event.customerId]}" />
                        <c:choose>
                            <c:when test="${not empty realPhone}">
                                <div class="username">📞 ${realPhone}</div>
                                <div class="username">✉️ ${realGmail}</div>
                            </c:when>
                            <c:otherwise>
                                <div class="username blurred">${maskedPhone}</div>
                                <div class="username blurred">${maskedGmail}</div>
                                <form action="ViewContact" method="post" style="margin-top:8px;">
                                    <input type="hidden" name="eventId" value="${event.eventId}">
                                    <button type="submit" class="unlock-btn">
                                        Liên hệ ngay (5 điểm)
                                    </button>
                                </form>
                                <p class="muted" style="margin-top:6px;">
                                    Điểm hiện tại: <b>${credits}</b>
                                </p>
                            </c:otherwise>
                        </c:choose>

                        <div class="date-text">Đăng ngày: ${event.createdAt}</div>
                    </div>
                </div>


            </div>

        </div>

    </body>
</html>
