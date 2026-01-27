<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xử lý thanh toán</title>
</head>
<body>
    <h2>Nhập thông tin thanh toán</h2>

    <!-- Form gửi dữ liệu đến POST mapping: /payment/process -->
    <form action="${pageContext.request.contextPath}/payment/process" method="POST">

        <label for="orderId">Order ID:</label>
        <!-- Tên "orderId" phải khớp với @RequestParam("orderId") trong Controller -->
        <input type="number" id="orderId" name="orderId" required><br><br>

        <label for="paymentSuccess">Thanh toán thành công:</label>
        <!-- Tên "paymentSuccess" phải khớp với @RequestParam("paymentSuccess") trong Controller -->
        <select id="paymentSuccess" name="paymentSuccess">
            <option value="true">Có</option>
            <option value="false">Không</option>
        </select><br><br>

        <input type="submit" value="Xác nhận thanh toán">
    </form>
</body>
</html>

