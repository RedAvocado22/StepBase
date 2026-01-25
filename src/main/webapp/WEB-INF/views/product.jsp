<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/views/general/navbar.jsp" />
<div class="container">
    <h2 style="margin:10px auto;width:fit-content;">Tất cả sản phẩm</h2>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Hình ảnh</th>
                <th>Tên sản phẩm</th>
                <th>Thương hiệu</th>
                <th>Danh mục</th>
                <th>Màu sắc</th>
                <th>Giá cơ bản</th>
                <th>Tùy chọn</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.id}</td>
                    <td>
                        <c:if test="${not empty p.imageUrl}">
                            <img src="${p.imageUrl}" alt="${p.name}">
                        </c:if>
                    </td>
                    <td>${p.name}</td>
                    <td>${p.brand.name}</td> <!-- Giả định Brand có field name -->
                    <td>${p.category.name}</td> <!-- Giả định Category có field name -->
                    <td>${p.color.name}</td> <!-- Giả định Color có field name -->
                    <td>
                        <fmt:formatNumber value="${p.basePrice}" type="currency" currencySymbol="₫" />
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin:0;">
                                <!-- productId khớp với @RequestParam trong Controller -->
                                <input type="hidden" name="productId" value="${p.id}">
                                <!-- quantity mặc định là 1 nếu bạn không cho chọn số lượng tại đây -->
                                <input type="hidden" name="quantity" value="1">

                                <button type="submit" style="background: none; border: none; color: blue; text-decoration: underline; cursor: pointer; padding: 0;">
                                    Thêm vào giỏ
                                </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>
</html>