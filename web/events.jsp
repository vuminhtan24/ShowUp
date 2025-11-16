<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách sự kiện</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/events.css" rel="stylesheet">
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <div class="container">
            <h1>Danh sách sự kiện</h1>

            <div class="search-bar">
                <form action="events" method="get">
                    <input type="text" name="keyword" placeholder="Tìm kiếm sự kiện..." value="${keyword != null ? keyword : ''}">
                    <button type="submit">Tìm</button>
                </form>
            </div>

            <div class="event-grid">
                <c:forEach var="event" items="${events}">
                    <div class="event-card">
                        <div class="event-content">
                            <div class="event-title">${event.eventName}</div>
                            <div class="event-info">📅 ${event.eventDate} | 🕓 ${event.eventTime}</div>
                            <div class="event-info">📍 ${event.location}</div>
                            <div class="event-description">${event.description}</div>

                            <div class="event-footer">
                                <span class="status
                                      <c:choose>
                                          <c:when test="${event.status eq 'Active'}">active</c:when>
                                          <c:when test="${event.status eq 'Pending'}">pending</c:when>
                                          <c:otherwise>cancelled</c:otherwise>
                                      </c:choose>">
                                    ${event.status}
                                </span>
                                <form action="eventDetail" method="get">
                                    <input type="hidden" name="id" value="${event.eventId}">
                                    <button class="btn-view" type="submit">Xem chi tiết</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- Pagination -->
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="events?page=${currentPage - 1}&keyword=${param.keyword}">&laquo; Trước</a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="page">
                    <a href="events?page=${page}&keyword=${param.keyword}"
                       class="${page == currentPage ? 'active' : ''}">
                        ${page}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="events?page=${currentPage + 1}&keyword=${param.keyword}">Sau &raquo;</a>
                </c:if>
            </div>
        </div>
    </body>
</html>
