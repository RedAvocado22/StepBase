<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="admin" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User List - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container">
            <div class="page-header">
                <h1>User List</h1>
                <p>Manage all users in the system</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <div class="d-flex justify-between align-center mb-3">
                <div></div>
                <a href="${pageContext.request.contextPath}/admin/staff/add" class="btn btn-primary">Add Staff</a>
            </div>

            <div class="card">
                <c:if test="${empty users || users.size() == 0}">
                    <p style="color: var(--text-secondary); text-align: center; padding: 2rem;">
                        No users found.
                    </p>
                </c:if>
                <c:if test="${not empty users && users.size() > 0}">
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${users}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td><strong>${user.username}</strong></td>
                                        <td>${user.email}</td>
                                        <td>
                                            <span style="padding: 0.25rem 0.5rem; border-radius: 4px; font-size: 0.875rem; font-weight: 500;
                                                <c:choose>
                                                    <c:when test="${user.role == 'ADMIN'}">background-color: #fef2f2; color: #991b1b;</c:when>
                                                    <c:when test="${user.role == 'STAFF'}">background-color: #eff6ff; color: #1e40af;</c:when>
                                                    <c:otherwise>background-color: #f0fdf4; color: #166534;</c:otherwise>
                                                </c:choose>">
                                                ${user.role}
                                            </span>
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

