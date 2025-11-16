<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang cá nhân nghệ sĩ</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #fafafa;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 90%;
                max-width: 1000px;
                margin: 40px auto;
                background: #fff;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .profile-header {
                display: flex;
                align-items: center;
                gap: 20px;
                margin-bottom: 30px;
                border-bottom: 1px solid #eee;
                padding-bottom: 15px;
            }
            .profile-header img {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #ddd;
            }
            .profile-header .info h2 {
                margin: 0;
                color: #333;
            }
            .profile-header .info p {
                color: #666;
                margin-top: 5px;
            }
            .upload-section {
                margin-bottom: 30px;
            }
            .upload-section h3 {
                color: #333;
                margin-bottom: 10px;
            }
            .upload-section form {
                display: flex;
                gap: 10px;
                align-items: center;
            }
            .upload-section input[type="file"] {
                border: 1px solid #ccc;
                padding: 6px;
                border-radius: 6px;
            }
            .upload-section button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 6px;
                cursor: pointer;
                transition: 0.3s;
            }
            .upload-section button:hover {
                background-color: #0056b3;
            }
            .gallery {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                gap: 15px;
            }
            .gallery img {
                width: 100%;
                border-radius: 10px;
                object-fit: cover;
                box-shadow: 0 0 6px rgba(0,0,0,0.1);
                transition: transform 0.2s;
            }
            .gallery img:hover {
                transform: scale(1.03);
            }
            .no-media {
                color: #888;
                text-align: center;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <!-- Header -->
            <div class="profile-header">
                <c:if test="${not empty mediaList}">
                    <img src="${mediaList[0].url}" alt="Avatar">
                </c:if>
                <c:if test="${empty mediaList}">
                    <img src="images/default-avatar.png" alt="Avatar">
                </c:if>

                <div class="info">
                    <h2>${artist.stageName}</h2>
                    <p>${artist.bio}</p>
                    <p><strong>Đánh giá:</strong> ${artist.ratingAvg} ⭐ (${artist.totalReviews} đánh giá)</p>
                </div>
            </div>

            <!-- Upload section -->
            <div class="upload-section">
                <c:if test="${sessionScope.account != null && sessionScope.account.userId == artist.id}">
                    <!-- Chỉ chủ sở hữu mới thấy form này -->
                    <h3>Đăng ảnh biểu diễn mới</h3>
                    <form action="personalPage" method="post" enctype="multipart/form-data">
                        <h4>Đăng ảnh biểu diễn mới</h4>
                        <input type="file" name="image" accept="image/*" required>
                        <button type="submit">Tải lên</button>
                    </form>
                </c:if>


                <c:if test="${not empty param.error}">
                    <p style="color:red;">Vui lòng chọn file để tải lên!</p>
                </c:if>
            </div>

            <!-- Gallery -->
            <h3>Thư viện ảnh biểu diễn</h3>
            <c:choose>
                <c:when test="${not empty mediaList}">
                    <div class="gallery">
                        <c:forEach var="m" items="${mediaList}">
                            <img src="${m.url}" alt="Performance image">
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-media">
                        <p>Chưa có ảnh biểu diễn nào được đăng.</p>
                    </div>
                </c:otherwise>
            </c:choose>
            <!-- ========================= GIỚI THIỆU CHI TIẾT ========================= -->
            <div class="upload-section">
                <h3>Giới thiệu chi tiết</h3>
                <c:if test="${not empty artist.bio}">
                    <p style="white-space:pre-line; color:#333;">${artist.bio}</p>
                </c:if>
                <c:if test="${empty artist.bio}">
                    <p class="no-media">Chưa có phần giới thiệu.</p>
                </c:if>

                <c:if test="${sessionScope.account != null && sessionScope.account.userId == artist.id}">
                    <form action="updateArtistBio" method="post" style="margin-top:15px;">
                        <textarea name="bio" rows="5" style="width:100%;padding:10px;border-radius:8px;border:1px solid #ccc;"
                                  placeholder="Viết giới thiệu chi tiết về bạn...">${artist.bio}</textarea>
                        <button type="submit" style="margin-top:10px;">Cập nhật giới thiệu</button>
                    </form>
                </c:if>
            </div>

            <!-- ========================= TIẾT MỤC BIỂU DIỄN ========================= -->
            <div class="upload-section">
                <h3>Tiết mục biểu diễn</h3>


                <!-- Danh sách tiết mục -->
                <c:choose>
                    <c:when test="${not empty performanceList}">
                        <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(250px,1fr));gap:20px;margin-top:20px;">
                            <c:forEach var="p" items="${performanceList}">
                                <div style="background:#f8f8f8;padding:15px;border-radius:10px;box-shadow:0 1px 3px rgba(0,0,0,0.1);">
                                    <c:if test="${not empty p.thumbnailUrl}">
                                        <img src="${p.thumbnailUrl}" alt="thumbnail"
                                             style="width:100%;height:160px;object-fit:cover;border-radius:8px;">
                                    </c:if>
                                    <h4 style="margin:10px 0 5px;">${p.title}</h4>
                                    <p style="color:#666;">${p.description}</p>
                                    <p><strong>Thời lượng:</strong> ${p.durationMinutes} phút</p>
                                    <p><strong>Giá:</strong> ${p.price} VNĐ</p>
                                    <c:if test="${not empty p.videoUrl}">
                                        <video controls style="width:100%;border-radius:6px;">
                                            <source src="${p.videoUrl}" type="video/mp4">
                                        </video>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="no-media">Chưa có tiết mục nào được đăng.</p>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>

    </body>
</html>
