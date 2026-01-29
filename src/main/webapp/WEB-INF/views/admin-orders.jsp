<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="currentPage" value="admin" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order List - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container">
            <div class="page-header">
                <h1>Order List</h1>
                <p>View all orders in the system</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <div class="card">
                <c:if test="${empty orders || orders.size() == 0}">
                    <p style="color: var(--text-secondary); text-align: center; padding: 2rem;">
                        No orders found.
                    </p>
                </c:if>
                <c:if test="${not empty orders && orders.size() > 0}">
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Date</th>
                                    <th>Items</th>
                                    <th class="text-right">Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orders}" var="order">
                                    <tr>
                                        <td><strong>#${order.id}</strong></td>
                                        <td>${order.customer.username}</td>
                                        <td>${order.createdAt}</td>
                                        <td>
                                            <c:forEach items="${order.items}" var="item" varStatus="status">
                                                ${item.product.name} (x${item.quantity})<c:if test="${!status.last}">, </c:if>
                                            </c:forEach>
                                        </td>
                                        <td class="text-right">
                                            <strong style="color: var(--primary-color);">$${order.totalAmount}</strong>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">Back to Dashboard</a>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

