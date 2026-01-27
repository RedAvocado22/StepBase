<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4 text-center">
        <h2 class="mb-4 text-primary">
            <i class="fa-solid fa-users"></i> User Management
        </h2>

        <a href="<c:url value='/users' />" class="btn btn-outline-primary btn-lg mb-3">
            <i class="fa-solid fa-list"></i> View User List
        </a>

        <br/>

        <a href="<c:url value='/users/new' />" class="btn btn-success btn-lg">
            <i class="fa-solid fa-user-plus"></i> Add New User
        </a>
    </div>
</div>

</body>
</html>
