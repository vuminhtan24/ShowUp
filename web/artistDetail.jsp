<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <title>ShowUp — Chi tiết nghệ sĩ</title>
        <style>
            .container.detail {
                max-width: 1100px;
                margin: 40px auto;
                display: grid;
                grid-template-columns: 2fr 1fr;
                gap: 30px;
            }
            .info-card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 1px 4px rgba(0,0,0,0.1);
                padding: 20px;
                margin-bottom: 20px;
            }
            .gallery {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
                gap: 12px;
            }
            .gallery img {
                width: 100%;
                height: 120px;
                object-fit: cover;
                border-radius: 8px;
            }
            .event-card {
                background: #f9f9f9;
                border-radius: 8px;
                padding: 12px;
                margin-bottom: 12px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            }
            .muted {
                color: #777;
                font-size: 14px;
            }
            .hero-photo {
                flex: 1;
                min-width: 260px;
                max-width: 360px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f9f9f9;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }
            .hero-photo img,
            .hero-photo svg {
                width: 100%;
                height: auto;
                object-fit: cover;
                border-radius: 12px;
            }
            /* Phần review và video */
            .performance-video {
                display: flex;
                flex-wrap: wrap;
                gap: 16px;
            }
            .performance-video iframe {
                border-radius: 8px;
                width: 320px;
                height: 180px;
            }
            .review {
                border-bottom: 1px solid #eee;
                padding: 8px 0;
            }
            .review:last-child {
                border-bottom: none;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="header.jsp" />

        <div class="container detail">
            <div>
                <!-- Thông tin cơ bản -->
                <div style="display:flex; align-items:center; gap:30px; margin:40px 0; flex-wrap:wrap;">
                    <!-- Hero ảnh đại diện -->
                    <div class="hero-photo">
                        <c:choose>
                            <c:when test="${not empty avatar}">
                                <img src="${avatar.url}" alt="${artist.stageName}" />
                            </c:when>
                            <c:otherwise>
                                <svg viewBox="0 0 1200 400" xmlns="http://www.w3.org/2000/svg">
                                <defs>
                                <linearGradient id="g1" x1="0" y1="0" x2="1" y2="1">
                                <stop offset="0%" stop-color="#a78bfa"/>
                                <stop offset="50%" stop-color="#6366f1"/>
                                <stop offset="100%" stop-color="#22d3ee"/>
                                </linearGradient>
                                </defs>
                                <rect width="1200" height="400" fill="url(#g1)"/>
                                <text x="40" y="220" font-size="48" fill="#fff" font-weight="800">${artist.stageName}</text>
                                </svg>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Thông tin nghệ sĩ -->
                    <div class="info-card">
                        <h2 style="margin:0 0 6px; font-size:28px">${artist.stageName}</h2>
                        <p class="muted">${artist.location}</p>
                        <p style="margin-top:10px;">${artist.bio}</p>
                        <p style="margin-top:10px;">
                            ⭐ <b>${artist.ratingAvg}</b> (${artist.totalReviews} lượt đánh giá)
                        </p>
                    </div>
                </div>

                <!-- Giới thiệu chi tiết -->
                <div class="info-card">
                    <h3 style="margin-bottom:8px;">Giới thiệu chi tiết</h3>
                    <c:if test="${empty artist.bio}">
                        <p class="muted">Nghệ sĩ chưa cập nhật phần giới thiệu.</p>
                    </c:if>
                    <c:if test="${not empty artist.bio}">
                        <p>${artist.bio}</p>
                    </c:if>
                </div>

                <!-- Hình ảnh -->
                <div class="info-card">
                    <h3 style="margin:0 0 10px">Hình ảnh của nghệ sĩ</h3>
                    <div class="gallery">
                        <c:forEach var="img" items="${mediaList}">
                            <img src="${img.url}" alt="media" />
                        </c:forEach>
                        <c:if test="${empty mediaList}">
                            <div class="muted">Chưa có hình ảnh</div>
                        </c:if>
                    </div>
                </div>

                <!-- Video / Tiết mục biểu diễn -->
                <div class="info-card">
                    <h3 style="margin-bottom:10px;">Tiết mục biểu diễn</h3>
                    <c:if test="${empty performanceList}">
                        <p class="muted">Chưa có tiết mục nào được đăng tải.</p>
                    </c:if>
                    <div class="performance-video">
                        <c:forEach var="p" items="${performanceList}">
                            <div>
                                <iframe src="${p.videoUrl}" frameborder="0" allowfullscreen></iframe>
                                <p><strong>${p.title}</strong></p>
                                <p class="muted">${p.description}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Show diễn -->
                <div class="info-card">
                    <h3 style="margin:0 0 10px">Show diễn đã tham gia</h3>
                    <c:if test="${empty eventList}">
                        <p class="muted">Chưa có show diễn nào.</p>
                    </c:if>
                    <c:forEach var="e" items="${eventList}">
                        <div class="event-card">
                            <h4>${e.eventName}</h4>
                            <p><strong>Ngày:</strong> ${e.eventDate}</p>
                            <p><strong>Thời gian:</strong> ${e.eventTime}</p>
                            <p><strong>Địa điểm:</strong> ${e.location}</p>
                            <p><strong>Số khách dự kiến:</strong> ${e.expectedGuests}</p>
                        </div>
                    </c:forEach>
                </div>

                <!-- Đánh giá -->
                <div class="info-card">
                    <h3 style="margin-bottom:10px;">Đánh giá từ người xem</h3>
                    <c:if test="${empty reviewList}">
                        <p class="muted">Chưa có đánh giá nào.</p>
                    </c:if>
                    <c:forEach var="r" items="${reviewList}">
                        <div class="review">
                            <p><b>⭐ ${r.rating}</b> — ${r.comment}</p>
                            <p class="muted">Ngày: ${r.createdAt}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Cột phải -->
            <aside class="info-card" style="align-self:start">
                <h3>Thông tin liên hệ</h3>
                <p class="muted">Liên hệ để đặt show hoặc xem thêm dịch vụ của nghệ sĩ này.</p>

                <!-- Hiển thị số điện thoại -->
                <div style="margin-top:15px;">
                    <strong>Số điện thoại:</strong>
                    <br>

                    <c:choose>
                        <!-- Nếu đã mua quyền xem -->
                        <c:when test="${phoneVisible == true}">
                            <span style="font-size:18px; font-weight:bold;">
                                ${artist.phone}
                            </span>
                        </c:when>

                        <!-- Nếu chưa mua -->
                        <c:otherwise>
                            <span style="font-size:18px; font-weight:bold; letter-spacing:1px;">
                                ${maskedPhone}
                            </span>
                            <br><br>

                            <!-- Nút mở khóa -->
                            <form action="unlockPhone" method="post">
                                <input type="hidden" name="artistId" value="${artist.artistId}">
                                <button type="submit"
                                        style="
                                        padding:8px 14px;
                                        background:#6366f1;
                                        color:white;
                                        border:none;
                                        border-radius:6px;
                                        cursor:pointer;
                                        ">
                                    Mở khóa số điện thoại (5 điểm)
                                </button>
                            </form>

                            <p class="muted" style="margin-top:6px;">
                                Điểm hiện tại: <b>${credits}</b>
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <hr style="margin:16px 0;">

                <a class="btn btn-primary" href="home">Quay lại danh sách</a>
            </aside>

        </div>
    </body>
</html>
