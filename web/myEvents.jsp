<%-- 
    Document   : myEvents
    Created on : Nov 11, 2025, 4:07:15 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Event" %>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    if (events == null) events = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Recruitment Posts</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/myEvents.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/fontawesome/css/all.min.css" rel="stylesheet">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    </head>

    <body id="page-top">
        <jsp:include page="header.jsp"/>
        <div class="container-fluid mt-4">
            <h3 class="mb-4 text-primary"><i class="fa fa-clipboard-list me-2"></i>My Recruitment Posts</h3>

            <!-- Thông báo -->
            <%
                String message = (String) session.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= message %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%
                    session.removeAttribute("message");
                }
            %>

            <div class="card shadow">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle text-center">
                            <thead class="table-primary">
                                <tr>
                                    <th>ID</th>
                                    <th>Event Name</th>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Location</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (events.isEmpty()) {
                                %>
                                <tr>
                                    <td colspan="8" class="text-muted">No recruitment posts yet.</td>
                                </tr>
                                <%
                                    } else {
                                        for (Event e : events) {
                                %>
                                <tr>
                                    <td><%= e.getEventId() %></td>
                                    <td><%= e.getEventName() %></td>
                                    <td><%= e.getEventDate() %></td>
                                    <td><%= e.getEventTime() %></td>
                                    <td><%= e.getLocation() %></td>
                                    <td>
                                        <span class="badge
                                              <%= "Approved".equals(e.getStatus()) ? "bg-success" :
                                                  "Pending".equals(e.getStatus()) ? "bg-warning text-dark" :
                                                  "Closed".equals(e.getStatus()) ? "bg-secondary" :
                                                  "Cancelled".equals(e.getStatus()) ? "bg-danger" : "bg-light text-dark" %>">
                                            <%= e.getStatus() %>
                                        </span>
                                    </td>
                                    <td><%= e.getCreatedAt() %></td>
                                    <td>
                                        <form action="MyEvents" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${e.eventId}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Are you sure you want to delete this event?');">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </form>
                                    </td>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" role="alert">
                                    ${error}
                                </div>
                            </c:if>

                            </tr>
                            <%
                                    }
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                    <div class="mt-3">
                        <a href="addPerformance" class="btn btn-primary"><i class="fa fa-plus-circle me-2"></i>Post New Recruitment</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
