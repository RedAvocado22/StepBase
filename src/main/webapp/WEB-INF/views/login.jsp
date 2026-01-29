<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="login" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 500px;">
            <div class="page-header text-center">
                <h1>Login</h1>
                <p>Welcome back! Please sign in to your account.</p>
            </div>

            <div class="card">
                <c:if test="${not empty error}">
                    <div class="alert alert-error">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/auth/login">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" class="form-control" required autofocus>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%;">Login</button>
                </form>

                <div class="text-center mt-3">
                    <p style="color: var(--text-secondary);">
                        No account? <a href="${pageContext.request.contextPath}/auth/register" style="color: var(--primary-color); text-decoration: none; font-weight: 500;">Register here</a>
                    </p>
                    <p style="color: var(--text-secondary); margin-top: 0.5rem;">
                        <a href="${pageContext.request.contextPath}/auth/reset-password" style="color: var(--primary-color); text-decoration: none; font-weight: 500;">Forgot Password?</a>
                    </p>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


