<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>User Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0">
                <i class="fa-solid fa-user"></i> User Form
            </h4>
        </div>

        <div class="card-body">
            <form:form action="${pageContext.request.contextPath}/users/save"
                       method="post" modelAttribute="user">

                <form:hidden path="id"/>

                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <form:input path="fullname" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <form:input path="email" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <form:password path="password" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone Number</label>
                    <form:input path="phoneNumber" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Gender</label><br/>
                    <form:radiobutton path="gender" value="1"/> Male
                    &nbsp;&nbsp;
                    <form:radiobutton path="gender" value="0"/> Female
                </div>

                <div class="mb-3">
                    <label class="form-label">Is Active</label><br/>
                    <form:radiobutton path="isActive" value="1"/> Yes
                    &nbsp;&nbsp;
                    <form:radiobutton path="isActive" value="0"/> No
                </div>

                <div class="form-check mb-3">
                    <form:checkbox path="admin" class="form-check-input"/>
                    <label class="form-check-label">Admin</label>
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-success">
                        <i class="fa-solid fa-save"></i> Save
                    </button>
                    <a href="<c:url value='/users' />" class="btn btn-secondary">
                        Cancel
                    </a>
                </div>

            </form:form>
        </div>
    </div>
</div>

</body>
</html>
