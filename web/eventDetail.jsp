<%-- 
    Document   : eventDetail
    Created on : Nov 15, 2025, 1:38:24 PM
    Author     : VU MINH TAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sự kiện</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 40px auto;
            background-color: #fff;
            padding: 25px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            text-align: left;
            padding: 12px 15px;
        }

        th {
            background-color: #4CAF50;
            color: white;
            width: 30%;
            border-radius: 6px 0 0 6px;
        }

        td {
            background-color: #f9f9f9;
            border-radius: 0 6px 6px 0;
        }

        tr + tr td, tr + tr th {
            border-top: 1px solid #ddd;
        }

        /* Responsive */
        @media (max-width: 600px) {
            th, td {
                display: block;
                width: 100%;
            }

            th {
                border-radius: 10px 10px 0 0;
            }

            td {
                border-radius: 0 0 10px 10px;
                margin-bottom: 15px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="container">
        <h1>Chi tiết sự kiện</h1>
        <table>
            <tr>
                <th>Mã sự kiện</th>
                <td>#Ev-${event.eventId}</td>
            </tr>
            <tr>
                <th>ID Khách hàng</th>
                <td>${event.customerId}</td>
            </tr>
            <tr>
                <th>Tên sự kiện</th>
                <td>${event.name}</td>
            </tr>
            <tr>
                <th>Miêu tả</th>
                <td>${event.description}</td>
            </tr>
            <tr>
                <th>Ngày</th>
                <td>${event.date}</td>
            </tr>
            <tr>
                <th>Giờ</th>
                <td>${event.time}</td>
            </tr>
            <tr>
                <th>Địa điểm</th>
                <td>${event.location}</td>
            </tr>
            <tr>
                <th>Trạng thái</th>
                <td>${event.status}</td>
            </tr>
            <tr>
                <th>Ngày tạo</th>
                <td>${event.createdAt}</td>
            </tr>
        </table>
    </div>
</body>
</html>
