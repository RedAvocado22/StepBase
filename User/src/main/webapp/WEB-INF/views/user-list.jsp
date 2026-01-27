<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>User List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h4 class="mb-0">
                <i class="fa-solid fa-users"></i> User List
            </h4>
            <a href="<c:url value='/users/new' />" class="btn btn-light btn-sm">
                <i class="fa-solid fa-plus"></i> Add User
            </a>
        </div>

        <div class="card-body">
            <table class="table table-bordered table-hover align-middle text-center">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>Active</th>
                    <th>Admin</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.fullname}</td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>
                            <span class="badge ${user.gender == 1 ? 'bg-info' : 'bg-warning'}">
                                    ${user.gender == 1 ? 'Nam' : 'Nữ'}
                            </span>
                        </td>
                        <td>
                            <span class="badge ${user.isActive == 1 ? 'bg-success' : 'bg-secondary'}">
                                    ${user.isActive == 1 ? 'Có' : 'Không'}
                            </span>
                        </td>
                        <td>
                            <span class="badge ${user.admin ? 'bg-danger' : 'bg-secondary'}">
                                    ${user.admin ? 'Admin' : 'User'}
                            </span>
                        </td>
                        <td>
                            <a href="<c:url value='/users/edit/${user.id}' />"
                               class="btn btn-warning btn-sm">
                                <i class="fa-solid fa-pen"></i>
                            </a>

                            <a href="<c:url value='/users/delete/${user.id}' />"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure?');">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="<c:url value='/' />" class="btn btn-secondary">
                <i class="fa-solid fa-arrow-left"></i> Back
            </a>
        </div>
    </div>
</div>

</body>
</html>
