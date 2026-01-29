<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="cart" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 900px;">
            <div class="page-header">
                <h1>Your Shopping Cart</h1>
                <p>Review your items before checkout</p>
            </div>

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">← Continue Shopping</a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>

            <c:if test="${empty items || items.size() == 0}">
                <div class="card text-center">
                    <p style="color: var(--text-secondary); font-size: 1.125rem; margin-bottom: 1rem;">Your cart is empty.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Start Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty items && items.size() > 0}">
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th class="text-right">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="grandTotal" value="0"/>
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td>
                                        <strong style="color: var(--text-primary);">${item.product.name}</strong>
                                    </td>
                                    <td>${item.quantity}</td>
                                    <td>$${item.product.price}</td>
                                    <td class="text-right">
                                        <strong style="color: var(--primary-color);">$${item.totalPrice}</strong>
                                    </td>
                                </tr>
                                <c:set var="grandTotal" value="${grandTotal + item.totalPrice}"/>
                            </c:forEach>
                            <tr style="background-color: var(--background);">
                                <td colspan="3" class="text-right">
                                    <strong style="font-size: 1.125rem;">Grand Total:</strong>
                                </td>
                                <td class="text-right">
                                    <strong style="font-size: 1.25rem; color: var(--primary-color);">$${grandTotal}</strong>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="card mt-3">
                    <form method="post" action="${pageContext.request.contextPath}/cart/checkout">
                        <button type="submit" class="btn btn-success" style="width: 100%; padding: 1rem; font-size: 1.125rem;">
                            Proceed to Checkout
                        </button>
                    </form>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


