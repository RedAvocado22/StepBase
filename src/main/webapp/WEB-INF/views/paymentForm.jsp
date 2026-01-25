<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xử lý thanh toán</title>
</head>
<body>
    <h2>Nhập thông tin thanh toán</h2>
    <form action="${pageContext.request.contextPath}/orders/place-order" method="post">
        <h3>Thông tin giao hàng</h3>
        <div>
            <label>Người nhận:</label>
            <input type="text" name="consigneeName" required>
        </div>
        <div>
            <label>Số điện thoại:</label>
            <input type="text" name="phone" required>
        </div>
        <div>
            <label>Địa chỉ nhận hàng:</label>
            <textarea name="address" required></textarea>
        </div>

        <h4>Tổng tiền: <fmt:formatNumber value="${cart.totalAmount}" type="currency" currencySymbol="₫"/></h4>

        <button type="submit">XÁC NHẬN VÀ THANH TOÁN</button>
    </form>
</body>
</html>

