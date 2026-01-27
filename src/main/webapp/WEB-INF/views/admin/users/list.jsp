<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</html>
</body>
</table>
    </tbody>
    </c:forEach>
        </tr>
            <td>${u.createdAt}</td>
            <td>${u.email}</td>
            <td>${u.username}</td>
            <td>${u.id}</td>
        <tr>
    <c:forEach var="u" items="${users}">
    <tbody>
    </thead>
    </tr>
        <th>Created At</th>
        <th>Email</th>
        <th>Username</th>
        <th>ID</th>
    <tr>
    <thead>
<table border="1" cellpadding="6">
<h1>User list</h1>
<body>
</head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/admin.css" />
    <title>User list</title>
    <meta charset="UTF-8" />
<head>
<html>
<!DOCTYPE html>
