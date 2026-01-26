<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order List</title>
</head>
<body>
<h1>Order List</h1>
<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Consignee</th>
        <th>Phone</th>
        <th>Date</th>
        <th>Total</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td>${o.consigneeName}</td>
            <td>${o.phone}</td>
            <td>${o.orderDate}</td>
            <td>${o.totalAmount}</td>
            <td>${o.orderStatus}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>