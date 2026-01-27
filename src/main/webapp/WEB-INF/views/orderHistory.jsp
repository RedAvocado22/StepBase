<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Lịch sử đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="/WEB-INF/views/general/navbar.jsp" />
    <div class="container">
        <h2 style="margin:10px auto;width:fit-content;">Lịch sử đơn hàng</h2>
        <table class="table table-striped border border-secondary-subtle">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày đặt</th>
                        <th>Người nhận</th>
                        <th>Địa chỉ giao hàng</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Thanh toán</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>#${order.id}</td>
                            <td>
                                <%-- Định dạng LocalDateTime --%>
                                <fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>${order.consigneeName} <br> <small class="text-muted">${order.phone}</small></td>
                            <td>${order.shippingAddress}</td>
                            <td class="fw-bold text-danger">
                                <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" />
                            </td>
                            <td>
                                <span class="badge
                                    ${order.orderStatus == 'PAID' ? 'bg-success' :
                                      order.orderStatus == 'PENDING' ? 'bg-warning' : 'bg-secondary'}">
                                    ${order.orderStatus}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.paid}">
                                        <span class="text-success fw-bold">Đã thanh toán</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger fw-bold">Chưa thanh toán</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="order-detail?id=${order.id}" class="btn btn-sm btn-outline-primary">Chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty orders}">
                        <tr>
                            <td colspan="8" class="text-center">Bạn chưa có đơn hàng nào.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>
</html>