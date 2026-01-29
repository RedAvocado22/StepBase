<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="products" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container">
            <div class="page-header">
                <h1>Our Products</h1>
                <p>Discover our wide selection of quality products</p>
                <c:if test="${sessionScope.currentUser != null && (sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF')}">
                    <div class="mt-2">
                        <a href="${pageContext.request.contextPath}/products/admin/add" class="btn btn-primary">Add New Product</a>
                    </div>
                </c:if>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <c:if test="${empty products}">
                <div class="card text-center">
                    <p style="color: var(--text-secondary);">No products available at the moment.</p>
                </div>
            </c:if>

            <c:if test="${not empty products}">
                <div class="product-grid">
                    <c:forEach items="${products}" var="p">
                        <div class="product-card">
                            <div class="product-card-body">
                                <h3 class="product-card-title">
                                    <a href="${pageContext.request.contextPath}/products/${p.id}">${p.name}</a>
                                </h3>
                                <p class="product-card-description">${p.description}</p>
                                <div class="product-card-price">$${p.price}</div>
                                <div class="product-card-actions">
                                    <form method="post" action="${pageContext.request.contextPath}/cart/add" style="display: flex; gap: 0.5rem; align-items: center; flex: 1;">
                                        <input type="hidden" name="productId" value="${p.id}"/>
                                        <input type="number" name="quantity" value="1" min="1" class="form-control" style="width: 80px;"/>
                                        <button type="submit" class="btn btn-primary btn-sm">Add to Cart</button>
                                    </form>
                                    <c:if test="${sessionScope.currentUser != null && (sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF')}">
                                        <a href="${pageContext.request.contextPath}/products/admin/edit/${p.id}" class="btn btn-secondary btn-sm">Edit</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


