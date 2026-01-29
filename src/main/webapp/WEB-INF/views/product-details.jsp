<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="products" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 800px;">
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">← Back to Products</a>
            </div>

            <c:if test="${not empty product}">
                <div class="card">
                    <div class="card-header">
                        <h2>${product.name}</h2>
                    </div>
                    <div style="margin-bottom: 1.5rem;">
                        <p style="color: var(--text-secondary); font-size: 1.125rem; line-height: 1.8;">
                            ${product.description}
                        </p>
                    </div>
                    <div style="margin-bottom: 2rem;">
                        <div class="product-card-price">$${product.price}</div>
                    </div>
                    <form method="post" action="${pageContext.request.contextPath}/cart/add" class="d-flex align-center gap-2" style="flex-wrap: wrap;">
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <div class="form-group" style="margin-bottom: 0;">
                            <label for="quantity">Quantity</label>
                            <input type="number" id="quantity" name="quantity" value="1" min="1" class="form-control" style="width: 120px;"/>
                        </div>
                        <div style="margin-top: 1.5rem;">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </div>
                    </form>
                </div>
            </c:if>

            <c:if test="${empty product}">
                <div class="card text-center">
                    <p style="color: var(--text-secondary);">Product not found.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary mt-2">Browse Products</a>
                </div>
            </c:if>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


