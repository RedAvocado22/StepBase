<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
<h1>User List</h1>
<p><a href="/admin/users/add">Add Staff</a></p>
<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Fullname</th>
        <th>Email</th>
        <th>Admin</th>
        <th>Active</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.fullname}</td>
            <td>${u.email}</td>
            <td><c:out value="${u.admin}"/></td>
            <td>${u.isActive}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>