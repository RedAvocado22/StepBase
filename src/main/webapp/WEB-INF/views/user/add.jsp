<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Staff</title>
</head>
<body>
<h1>Add Staff</h1>
<c:if test="${not empty error}">
    <div style="color: red">${error}</div>
</c:if>
<form method="post" action="/admin/users/add">
    <div>
        <label>Fullname: <input type="text" name="fullname" required/></label>
    </div>
    <div>
        <label>Email: <input type="email" name="email" required/></label>
    </div>
    <div>
        <label>Password: <input type="password" name="password" required/></label>
    </div>
    <div>
        <button type="submit">Create Staff</button>
    </div>
</form>
</body>
</html>