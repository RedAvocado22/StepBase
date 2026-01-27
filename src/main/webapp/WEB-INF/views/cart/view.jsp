<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="/WEB-INF/views/general/navbar.jsp" />
    <div class="container">
        <h2 style="margin:10px auto;width:fit-content;">Giỏ hàng của bạn</h2>
        <%-- Kiểm tra biến 'cart' được gửi từ Model --%>
            <c:choose>
                <c:when test="${not empty cart and not cart.isEmpty()}">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Thành tiền</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td>${item.product.name}</td>
                                    <td>
                                        <fmt:formatNumber value="${item.product.basePrice}" type="currency" currencySymbol="₫"/>
                                    </td>
                                    <td>
                                        <%-- Form update số lượng --%>
                                        <form action="${pageContext.request.contextPath}/cart/update" method="post">
                                            <input type="hidden" name="productId" value="${item.product.id}">
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" style="width: 60px;">
                                            <button type="submit" class="btn btn-success btn-sm">Cập nhật</button>
                                        </form>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${item.subTotal}" type="currency" currencySymbol="₫"/>
                                    </td>
                                    <td>
                                        <%-- Form xóa item (Controller dùng PostMapping cho /remove) --%>
                                        <form action="${pageContext.request.contextPath}/cart/remove" method="post" onsubmit="return confirm('Xóa sản phẩm này?')">
                                            <input type="hidden" name="productId" value="${item.product.id}">
                                            <button type="submit" class="btn btn-danger">Xóa</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div style="text-align: right;">
                        <p>Tổng tiền thanh toán: <span class="fw-bold text-danger"><fmt:formatNumber value="${cart.totalAmount}" type="currency" currencySymbol="₫"/></span></p>

                        <form action="${pageContext.request.contextPath}/cart/clear" method="post" style="display: inline;">
                            <button type="submit" onclick="return confirm('Xóa sạch giỏ hàng?')">Xóa tất cả</button>
                        </form>

                        <a href="${pageContext.request.contextPath}/orders/checkout" style="padding: 10px 20px; background: orange; color: white; text-decoration: none; font-weight: bold;">THANH TOÁN</a>
                    </div>

                    <!-- Thanh phân trang thủ công -->
                    <c:if test="${totalPages >= 1}">
                        <nav>
                            <ul class="pagination pagination-sm">

                                <!-- Nút Trước -->
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage - 1}">Trước</a>
                                </li>

                                <!-- Hiển thị các số trang -->
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Nút Sau -->
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage + 1}">Sau</a>
                                </li>

                            </ul>
                        </nav>
                    </c:if>
                </c:when>

                <c:otherwise>
                    <div class="cart-empty">
                        <p>Giỏ hàng đang trống.</p>
                        <a href="${pageContext.request.contextPath}/product">Quay lại mua sắm</a>
                    </div>
                </c:otherwise>
            </c:choose>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>
</html>