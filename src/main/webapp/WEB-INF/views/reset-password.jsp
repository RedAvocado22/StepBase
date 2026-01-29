<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="reset-password" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container" style="max-width: 500px;">
            <div class="page-header text-center">
                <h1>Reset Password</h1>
                <p>Enter your username and email to reset your password</p>
            </div>

            <div class="card">
                <c:if test="${not empty error}">
                    <div class="alert alert-error">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/auth/reset-password">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" class="form-control" required autofocus>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%;">Reset Password</button>
                </form>

                <div class="text-center mt-3">
                    <p style="color: var(--text-secondary);">
                        Remember your password? <a href="${pageContext.request.contextPath}/auth/login" style="color: var(--primary-color); text-decoration: none; font-weight: 500;">Login here</a>
                    </p>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

