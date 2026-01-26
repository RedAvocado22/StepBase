<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/admin.css" />
    <style>
        .form { display: grid; gap: 12px; max-width: 520px; }
        .field label { display:block; font-size: 12px; color: rgba(255,255,255,0.72); margin-bottom: 6px; }
        .field input, .field select { width: 100%; padding: 10px 10px; border-radius: 10px; border: 1px solid rgba(255,255,255,0.12); background: rgba(15,23,48,0.8); color: #e6eaf2; }
        .btn { display:inline-block; padding: 10px 12px; border-radius: 10px; border: 1px solid rgba(255,255,255,0.14); background: rgba(91,157,255,0.22); color: #e6eaf2; cursor:pointer; }
        .alert { padding: 10px 12px; border-radius: 12px; border: 1px solid rgba(255,255,255,0.12); margin-bottom: 12px; }
        .alert-success { background: rgba(120,255,214,0.12); }
        .alert-error { background: rgba(255,120,120,0.12); }
    </style>
</head>
<body>
<div class="container">
    <header class="header">
        <div class="title">User Profile</div>
        <div class="subtitle">Update your basic information</div>
    </header>

    <c:if test="${not empty success}">
        <div class="alert alert-success"><c:out value="${success}" /></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}" /></div>
    </c:if>

    <div class="panel" style="padding: 14px;">
        <form class="form" action="${pageContext.request.contextPath}/profile" method="post">
            <div class="field">
                <label>Full name</label>
                <input type="text" name="fullname" value="${user.fullname}" required />
            </div>

            <div class="field">
                <label>Phone number</label>
                <input type="text" name="phoneNumber" value="${user.phoneNumber}" />
            </div>

            <div class="field">
                <label>Gender</label>
                <select name="gender">
                    <option value="0" ${user.gender == 0 ? 'selected' : ''}>Unknown</option>
                    <option value="1" ${user.gender == 1 ? 'selected' : ''}>Male</option>
                    <option value="2" ${user.gender == 2 ? 'selected' : ''}>Female</option>
                </select>
            </div>

            <div>
                <button class="btn" type="submit">Save changes</button>
            </div>
        </form>

        <div style="margin-top: 12px; color: rgba(255,255,255,0.65); font-size: 12px;">
            Note: this page reads current user from HttpSession attribute <code>userId</code>.
        </div>
    </div>
</div>
</body>
</html>
