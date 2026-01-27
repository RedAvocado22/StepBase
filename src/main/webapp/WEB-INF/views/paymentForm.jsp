<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xử lý thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body class="vh-100 d-flex bg-light">
<jsp:include page="/WEB-INF/views/general/navbar.jsp" />
    <div class="container m-auto mt-5">
            <p class="h2 text-center">Nhập thông tin thanh toán</p>
            <p class="lead text-center">Thông tin giao hàng</p>
            <form action="${pageContext.request.contextPath}/orders/place-order" method="post">
                <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Người nhận:</label>
                    <div class="col-sm-10">
                        <input type="text" name="consigneeName" required class="form-control" id="inputEmail3">
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="inputPassword3" class="col-sm-2 col-form-label">Số điện thoại:</label>
                    <div class="col-sm-10">
                        <input type="text" name="phone" required class="form-control" id="inputPassword3">
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="exampleFormControlTextarea1" class="col-sm-2 col-form-label">Địa chỉ nhận hàng:</label>
                    <div class="col-sm-10">
                        <textarea name="address" required class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                </div>
                <div class="row mb-3">
                    <p class="h3 text-success">Tổng tiền: <fmt:formatNumber value="${cart.totalAmount}" type="currency" currencySymbol="₫"/></p>
                </div>
                <button type="submit" class="btn btn-success btn-lg">Xác nhận và thanh toán</button>
            </form>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
            crossorigin="anonymous"></script>
</body>
</html>

