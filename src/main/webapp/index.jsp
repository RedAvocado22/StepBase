<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="home" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HSF Shop - Home</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container">
            <div class="page-header text-center">
                <h1>Welcome to HSF Shop</h1>
                <p>Your one-stop destination for quality products</p>
            </div>

            <div class="card text-center">
                <div class="card-header">
                    <h3>Get Started</h3>
                </div>
                <p class="mb-3">Browse our wide selection of products and start shopping today!</p>
                <div class="d-flex justify-between align-center gap-2" style="flex-wrap: wrap; justify-content: center;">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Browse Products</a>
                    <a href="${pageContext.request.contextPath}/auth/register" class="btn btn-outline">Create Account</a>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


