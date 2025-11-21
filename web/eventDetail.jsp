<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <div class="username">${event.phone}</div>
                    <div class="username">${event.gmail}</div>

                    <div class="date-text">Đăng ngày: ${event.createdAt}</div>
                </div>
            </div>

        </div>

    </div>

</body>
</html>
