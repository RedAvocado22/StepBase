<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="products" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:choose><c:when test="${product.id != null}">Edit Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose> - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 600px;">
            <div class="page-header">
                <h1><c:choose><c:when test="${product.id != null}">Edit Product</c:when><c:otherwise>Add New Product</c:otherwise></c:choose></h1>
            </div>

            <div class="card">
                <c:if test="${not empty error}">
                    <div class="alert alert-error">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/products/admin/<c:choose><c:when test="${product.id != null}">edit/${product.id}</c:when><c:otherwise>add</c:otherwise></c:choose>">
                    <c:if test="${product.id != null}">
                        <input type="hidden" name="id" value="${product.id}"/>
                    </c:if>
                    
                    <div class="form-group">
                        <label for="name">Product Name</label>
                        <input type="text" id="name" name="name" class="form-control" value="${product.name}" required>
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" class="form-control" rows="4" required>${product.description}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="price">Price ($)</label>
                        <input type="number" id="price" name="price" class="form-control" step="0.01" min="0" value="${product.price}" required>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">Save Product</button>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

