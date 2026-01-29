<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="orders" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 900px;">
            <div class="page-header">
                <h1>Your Order History</h1>
                <p>View all your past orders</p>
            </div>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">← Back to Products</a>
            </div>

            <c:if test="${empty orders || orders.size() == 0}">
                <div class="card text-center">
                    <p style="color: var(--text-secondary); font-size: 1.125rem; margin-bottom: 1rem;">
                        You haven't placed any orders yet.
                    </p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Start Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty orders && orders.size() > 0}">
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Date</th>
                                <th class="text-right">Total Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="o">
                                <tr>
                                    <td><strong>#${o.id}</strong></td>
                                    <td>${o.createdAt}</td>
                                    <td class="text-right">
                                        <strong style="color: var(--primary-color); font-size: 1.125rem;">$${o.totalAmount}</strong>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


