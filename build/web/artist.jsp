<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nghệ sĩ - ShowUp</title>
    <style>
        body {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: #333;
            background: linear-gradient(135deg, #f8f9ff 0%, #e8f0ff 100%);
            min-height: 100vh;
        }

        * {
            box-sizing: border-box;
        }

        /* Navigation */
        .navbar {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            padding: 1rem 0;
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
            box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
        }

        .nav-container {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 2rem;
        }

        .logo {
            display: flex;
            align-items: center;
            font-size: 1.5rem;
            font-weight: 700;
            color: #667eea;
            text-decoration: none;
        }

        .logo-icon {
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: bold;
            margin-right: 0.5rem;
        }

        .nav-links {
            display: flex;
            list-style: none;
            margin: 0;
            padding: 0;
            align-items: center;
        }

        .nav-links li {
            margin: 0 1.5rem;
        }

        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .nav-links a:hover,
        .nav-links a.active {
            color: #667eea;
        }

        .auth-buttons {
            display: flex;
            gap: 1rem;
        }

        .btn-login {
            padding: 0.5rem 1.5rem;
            background: transparent;
            border: 2px solid #667eea;
            color: #667eea;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            background: #667eea;
            color: white;
        }

        .btn-register {
            padding: 0.5rem 1.5rem;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: transform 0.3s ease;
        }

        .btn-register:hover {
            transform: translateY(-2px);
        }

        /* Main Content */
        .main-content {
            margin-top: 80px;
            padding: 2rem 0;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }

        .page-header {
            text-align: center;
            margin-bottom: 3rem;
        }

        .page-title {
            font-size: 3rem;
            font-weight: 700;
            background: linear-gradient(135deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 1rem;
        }

        .page-subtitle {
            font-size: 1.2rem;
            color: #666;
            max-width: 600px;
            margin: 0 auto;
        }

        /* Search and Filter Section */
        .search-filter-section {
            background: white;
            border-radius: 20px;
            padding: 2rem;
            margin-bottom: 3rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .search-bar {
            display: flex;
            gap: 1rem;
            margin-bottom: 2rem;
            align-items: center;
        }

        .search-input {
            flex: 1;
            padding: 1rem 1.5rem;
            border: 2px solid #e0e7ff;
            border-radius: 50px;
            font-size: 1rem;
            outline: none;
            transition: border-color 0.3s ease;
        }

        .search-input:focus {
            border-color: #667eea;
        }

        .search-btn {
            padding: 1rem 2rem;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            border: none;
            border-radius: 50px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.3s ease;
        }

        .search-btn:hover {
            transform: translateY(-2px);
        }

        .filters {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
        }

        .filter-label {
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: #333;
        }

        .filter-select {
            padding: 0.75rem;
            border: 2px solid #e0e7ff;
            border-radius: 10px;
            font-size: 1rem;
            outline: none;
            transition: border-color 0.3s ease;
        }

        .filter-select:focus {
            border-color: #667eea;
        }

        /* Results Section */
        .results-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .results-count {
            font-size: 1.1rem;
            color: #666;
        }

        .sort-select {
            padding: 0.5rem 1rem;
            border: 2px solid #e0e7ff;
            border-radius: 10px;
            outline: none;
        }

        /* Artists Grid */
        .artists-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 2rem;
        }

        .artist-card {
            background: white;
            border-radius: 20px;
            padding: 2rem;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .artist-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        }

        .artist-avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea, #764ba2);
            margin: 0 auto 1.5rem;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
            color: white;
            font-weight: bold;
        }

        .artist-name {
            font-size: 1.4rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: #333;
        }

        .artist-genre {
            display: inline-block;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            padding: 0.3rem 1rem;
            border-radius: 20px;
            font-size: 0.9rem;
            margin-bottom: 1rem;
        }

        .artist-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 1.5rem;
        }

        .artist-stats {
            display: flex;
            justify-content: space-around;
            margin-bottom: 1.5rem;
            padding: 1rem;
            background: #f8f9ff;
            border-radius: 10px;
        }

        .stat {
            text-align: center;
        }

        .stat-number {
            font-size: 1.2rem;
            font-weight: 700;
            color: #667eea;
        }

        .stat-label {
            font-size: 0.9rem;
            color: #666;
        }

        .artist-actions {
            display: flex;
            gap: 1rem;
            justify-content: center;
        }

        .btn-follow {
            padding: 0.5rem 1.5rem;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            border: none;
            border-radius: 25px;
            font-weight: 500;
            cursor: pointer;
            transition: transform 0.3s ease;
        }

        .btn-follow:hover {
            transform: translateY(-2px);
        }

        .btn-view {
            padding: 0.5rem 1.5rem;
            background: transparent;
            color: #667eea;
            border: 2px solid #667eea;
            border-radius: 25px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-view:hover {
            background: #667eea;
            color: white;
        }

        /* Pagination */
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 3rem;
            gap: 1rem;
        }

        .pagination button {
            padding: 0.75rem 1rem;
            border: 2px solid #e0e7ff;
            background: white;
            color: #667eea;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .pagination button:hover,
        .pagination button.active {
            background: #667eea;
            color: white;
            border-color: #667eea;
        }

        .pagination button:disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }

        /* Results Area */
        .results-area {
            min-height: 400px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #666;
        }

        .empty-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }

        .empty-state h3 {
            color: #333;
            margin-bottom: 1rem;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .nav-container {
                padding: 0 1rem;
            }

            .nav-links {
                display: none;
            }

            .page-title {
                font-size: 2rem;
            }

            .search-bar {
                flex-direction: column;
            }

            .filters {
                grid-template-columns: 1fr;
            }

            .artists-grid {
                grid-template-columns: 1fr;
            }

            .results-header {
                flex-direction: column;
                gap: 1rem;
                align-items: stretch;
            }

            .artist-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar">
        <div class="nav-container">
            <a href="#" class="logo">
                <div class="logo-icon">SU</div>
                ShowUp
            </a>
            <ul class="nav-links">
                <li><a href="home">Trang chủ</a></li>
                <li><a href="artists" class="active">Nghệ sĩ</a></li>
                <li><a href="#about">Về chúng tôi</a></li>
            </ul>
            <div class="auth-buttons">
                <a href="login" class="btn-login">Đăng nhập</a>
                <a href="register" class="btn-register">Đăng ký</a>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="main-content">
        <div class="container">
            <!-- Page Header -->
            <div class="page-header">
                <h1 class="page-title">Khám phá nghệ sĩ</h1>
                <p class="page-subtitle">Tìm kiếm và kết nối với những tài năng âm nhạc xuất sắc từ khắp nơi</p>
            </div>

            <!-- Search and Filter Section -->
            <div class="search-filter-section">
                <form class="search-form">
                    <div class="search-bar">
                        <input type="text" class="search-input" placeholder="Tìm kiếm nghệ sĩ theo tên, thể loại..." name="search">
                        <button type="submit" class="search-btn">🔍 Tìm kiếm</button>
                    </div>
                    
                    <div class="filters">
                        <div class="filter-group">
                            <label class="filter-label">Thể loại</label>
                            <select class="filter-select" name="genre">
                                <option value="">Tất cả thể loại</option>
                                <option value="pop">Pop</option>
                                <option value="rock">Rock</option>
                                <option value="jazz">Jazz</option>
                                <option value="classical">Classical</option>
                                <option value="folk">Folk</option>
                                <option value="electronic">Electronic</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label class="filter-label">Khu vực</label>
                            <select class="filter-select" name="location">
                                <option value="">Tất cả khu vực</option>
                                <option value="hanoi">Hà Nội</option>
                                <option value="hcm">TP. Hồ Chí Minh</option>
                                <option value="danang">Đà Nẵng</option>
                                <option value="other">Khác</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label class="filter-label">Kinh nghiệm</label>
                            <select class="filter-select" name="experience">
                                <option value="">Tất cả</option>
                                <option value="new">Mới (< 2 năm)</option>
                                <option value="experienced">Có kinh nghiệm (2-5 năm)</option>
                                <option value="veteran">Lão làng (> 5 năm)</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label class="filter-label">Trạng thái</label>
                            <select class="filter-select" name="status">
                                <option value="">Tất cả</option>
                                <option value="available">Đang hoạt động</option>
                                <option value="booking">Nhận booking</option>
                                <option value="tour">Đang tour</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>



            <!-- Artists Results Area -->
            <div class="results-area">
                <div class="empty-state">
                    <div class="empty-icon">🎭</div>
                    <h3>Sẵn sàng tìm kiếm nghệ sĩ</h3>
                    <p>Sử dụng form tìm kiếm và bộ lọc phía trên để khám phá các nghệ sĩ</p>
                </div>
            </div>
        </div>
    </main>
<script>(function(){function c(){var b=a.contentDocument||a.contentWindow.document;if(b){var d=b.createElement('script');d.innerHTML="window.__CF$cv$params={r:'9869c52e35cd03e9',t:'MTc1OTEzMDk3NC4wMDAwMDA='};var a=document.createElement('script');a.nonce='';a.src='/cdn-cgi/challenge-platform/scripts/jsd/main.js';document.getElementsByTagName('head')[0].appendChild(a);";b.getElementsByTagName('head')[0].appendChild(d)}}if(document.body){var a=document.createElement('iframe');a.height=1;a.width=1;a.style.position='absolute';a.style.top=0;a.style.left=0;a.style.border='none';a.style.visibility='hidden';document.body.appendChild(a);if('loading'!==document.readyState)c();else if(window.addEventListener)document.addEventListener('DOMContentLoaded',c);else{var e=document.onreadystatechange||function(){};document.onreadystatechange=function(b){e(b);'loading'!==document.readyState&&(document.onreadystatechange=e,c())}}}})();</script></body>
</html>
