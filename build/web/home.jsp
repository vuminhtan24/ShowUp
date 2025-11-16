<!DOCTYPE html>
<html lang="vi">
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ShowUp - Kết nối nghệ sĩ và khán giả</title>
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <!-- Hero Section -->
        <section class="hero" id="home">
            <div class="hero-background"></div>
            <div class="stage-lights"></div>
            <div class="hero-content">
                <h1>Kết nối nghệ sĩ và khán giả</h1>
                <p>Nền tảng hàng đầu để khám phá tài năng và tạo ra những trải nghiệm âm nhạc đáng nhớ</p>
                <a href="#artists" class="cta-button">Khám phá ngay</a>
            </div>
            <div class="hero-image">
                <img src="upload/home/37a7f0f2f1afe68709caeca3864a54ca.jpg" alt="Artist Spotlight"/>
            </div>
        </section>

        <!-- Main Content -->
        <main class="main-content">
            <!-- Artists Section -->
            <section class="section" id="artists">
                <div class="container">
                    <h2 class="section-title">Nghệ sĩ nổi bật</h2>
                    <div class="artists-grid">
                        <c:forEach var="a" items="${artists}">
                            <a href="artistDetail?artistId=${a.id}" class="artist-card" style="text-decoration:none; color:inherit;">
                                <div class="artist-avatar">
                                    <c:choose>
                                        <c:when test="${not empty a.imageUrl}">
                                            <img src="${a.imageUrl}" alt="${a.stageName}" 
                                                 style="width:60px; height:60px; border-radius:50%; object-fit:cover;">
                                        </c:when>
                                        <c:otherwise>
                                            🎤
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <h3 class="artist-name">${a.stageName}</h3>
                                <p class="artist-description">${a.bio}</p>
                            </a>
                        </c:forEach>
                    </div>

                </div>
            </section>

            <!-- About Section -->
            <section class="section about-section" id="about">
                <div class="container">
                    <h2 class="section-title">Về ShowUp</h2>
                    <div class="about-content">
                        <div class="about-text">
                            <p>ShowUp là nền tảng kết nối hàng đầu giữa nghệ sĩ và khán giả, tạo ra một cộng đồng âm nhạc sôi động và đầy cảm hứng.</p>
                            <p>Chúng tôi tin rằng mỗi nghệ sĩ đều xứng đáng có một sân khấu để tỏa sáng, và mỗi khán giả đều có quyền được thưởng thức những trải nghiệm âm nhạc tuyệt vời nhất.</p>
                            <p>Với công nghệ hiện đại và giao diện thân thiện, ShowUp giúp bạn dễ dàng khám phá tài năng mới, đặt vé concert và kết nối với cộng đồng yêu nhạc.</p>
                        </div>
                        <div class="about-illustration">
                            🎭
                        </div>
                    </div>
                </div>
            </section>
        </main>

        <!-- Footer -->
        <footer class="footer">
            <div class="container">
                <div class="footer-content">
                    <div class="footer-section">
                        <h3>Liên hệ</h3>
                        <p>📧 contact@showup.vn</p>
                        <p>📞 +84 919 994 398</p>
                        <p>📍 94 Hoàng Công Chất, Bắc Từ Liêm, Hà Nội</p>
                    </div>
                    <div class="footer-section">
                        <h3>Dịch vụ</h3>
                        <a href="#">Đặt vé concert</a>
                        <a href="#">Quản lý sự kiện</a>
                        <a href="#">Hỗ trợ nghệ sĩ</a>
                        <a href="#">Streaming trực tiếp</a>
                    </div>
                    <div class="footer-section">
                        <h3>Theo dõi chúng tôi</h3>
                        <div class="social-links">
                            <a href="https://www.facebook.com/tan.york2501/" class="social-link">📘</a>
                            <a href="#" class="social-link">📷</a>
                            <a href="#" class="social-link">🐦</a>
                            <a href="#" class="social-link">🎵</a>
                        </div>
                    </div>
                </div>
                <div class="footer-bottom">
                    <p>&copy; 2024 ShowUp. Tất cả quyền được bảo lưu.</p>
                </div>
            </div>
        </footer>
        <script>(function(){function c(){var b = a.contentDocument || a.contentWindow.document; if (b){var d = b.createElement('script'); d.innerHTML = "window.__CF$cv$params={r:'98638699a208dd9a',t:'MTc1OTA2NTQ5Ni4wMDAwMDA='};var a=document.createElement('script');a.nonce='';a.src='/cdn-cgi/challenge-platform/scripts/jsd/main.js';document.getElementsByTagName('head')[0].appendChild(a);"; b.getElementsByTagName('head')[0].appendChild(d)}}if (document.body){var a = document.createElement('iframe'); a.height = 1; a.width = 1; a.style.position = 'absolute'; a.style.top = 0; a.style.left = 0; a.style.border = 'none'; a.style.visibility = 'hidden'; document.body.appendChild(a); if ('loading' !== document.readyState)c(); else if (window.addEventListener)document.addEventListener('DOMContentLoaded', c); else{var e = document.onreadystatechange || function(){}; document.onreadystatechange = function(b){e(b); 'loading' !== document.readyState && (document.onreadystatechange = e, c())}}}})();</script></body>
</html>
