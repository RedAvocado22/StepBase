<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/admin.css" />
</head>
<body>
<div class="container">
    <header class="header">
        <div class="title">StepBase Admin</div>
        <div class="subtitle">Dashboard</div>
    </header>

    <section class="cards">
        <div class="card">
            <div class="card-label">Users</div>
            <div class="card-value"><c:out value="${stats.user_count}" /></div>
        </div>
        <div class="card">
            <div class="card-label">Products</div>
            <div class="card-value"><c:out value="${stats.product_count}" /></div>
        </div>
        <div class="card">
            <div class="card-label">Orders</div>
            <div class="card-value"><c:out value="${stats.order_count}" /></div>
        </div>
        <div class="card">
            <div class="card-label">Paid revenue</div>
            <div class="card-value">
                <fmt:formatNumber value="${stats.paid_revenue}" type="number" maxFractionDigits="2" />
            </div>
        </div>
    </section>

    <section class="grid">
        <div class="panel">
            <div class="panel-title">Recent orders</div>
            <div class="table-wrap">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Customer</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Paid</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${recentOrders}" var="o">
                        <tr>
                            <td>#<c:out value="${o.id}" /></td>
                            <td><c:out value="${o.order_date}" /></td>
                            <td>
                                <div class="cell-strong"><c:out value="${o.customer_name}" /></div>
                                <div class="cell-muted"><c:out value="${o.customer_email}" /></div>
                            </td>
                            <td><fmt:formatNumber value="${o.total_amount}" type="number" maxFractionDigits="2" /></td>
                            <td><c:out value="${o.order_status}" /></td>
                            <td><c:out value="${o.is_paid}" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel">
            <div class="panel-title">Low stock variants</div>
            <div class="table-wrap">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Variant</th>
                        <th>Product</th>
                        <th>Size</th>
                        <th>Stock</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lowStock}" var="v">
                        <tr>
                            <td><c:out value="${v.variant_id}" /></td>
                            <td>
                                <div class="cell-strong">#<c:out value="${v.product_id}" /> - <c:out value="${v.product_name}" /></div>
                            </td>
                            <td><c:out value="${v.size_eu}" /></td>
                            <td><c:out value="${v.stock_quantity}" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>
</body>
</html>
