<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="admin" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - HSF Shop</title>
    <jsp:include page="/WEB-INF/views/includes/styles.jsp"/>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>

    <main>
        <div class="container">
            <div class="page-header">
                <h1>Admin Dashboard</h1>
                <p>Manage your shop and view statistics</p>
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-card-label">Total Users</div>
                    <div class="stat-card-value">${userCount}</div>
                </div>
                <div class="stat-card">
                    <div class="stat-card-label">Total Orders</div>
                    <div class="stat-card-value">${orderCount}</div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h3>Quick Actions</h3>
                </div>
                <div class="d-flex gap-2" style="flex-wrap: wrap;">
                    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary">View All Orders</a>
                    <a href="${pageContext.request.contextPath}/products/admin/add" class="btn btn-primary">Add Product</a>
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">View Users</a>
                        <a href="${pageContext.request.contextPath}/admin/staff/add" class="btn btn-success">Add Staff</a>
                    </c:if>
                </div>
            </div>

                <c:if test="${not empty orderStats || not empty userStats}">
                    <div class="card">
                        <div class="card-header">
                            <h3>Statistics Charts</h3>
                        </div>
                        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem;">
                            <c:if test="${not empty orderStats}">
                                <div>
                                    <h4 style="margin-bottom: 1rem; color: var(--text-primary);">Orders by Month</h4>
                                    <div style="background: var(--background); padding: 1rem; border-radius: 8px;">
                                        <c:set var="maxOrderValue" value="0"/>
                                        <c:forEach items="${orderStats}" var="stat">
                                            <c:if test="${stat.value > maxOrderValue}">
                                                <c:set var="maxOrderValue" value="${stat.value}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach items="${orderStats}" var="stat">
                                            <div style="margin-bottom: 0.5rem;">
                                                <div style="display: flex; justify-content: space-between; margin-bottom: 0.25rem;">
                                                    <span style="font-size: 0.875rem; color: var(--text-secondary);">${stat.key}</span>
                                                    <span style="font-weight: 600; color: var(--primary-color);">${stat.value}</span>
                                                </div>
                                                <div style="background: var(--border-color); height: 8px; border-radius: 4px; overflow: hidden;">
                                                    <c:set var="orderPercent" value="${maxOrderValue > 0 ? (stat.value * 100 / maxOrderValue) : 0}"/>
                                                    <div style="background: var(--primary-color); height: 100%; width: ${orderPercent}%;"></div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty userStats}">
                                <div>
                                    <h4 style="margin-bottom: 1rem; color: var(--text-primary);">Users by Role</h4>
                                    <div style="background: var(--background); padding: 1rem; border-radius: 8px;">
                                        <c:set var="maxUserValue" value="0"/>
                                        <c:forEach items="${userStats}" var="stat">
                                            <c:if test="${stat.value > maxUserValue}">
                                                <c:set var="maxUserValue" value="${stat.value}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach items="${userStats}" var="stat">
                                            <div style="margin-bottom: 0.5rem;">
                                                <div style="display: flex; justify-content: space-between; margin-bottom: 0.25rem;">
                                                    <span style="font-size: 0.875rem; color: var(--text-secondary);">${stat.key}</span>
                                                    <span style="font-weight: 600; color: var(--primary-color);">${stat.value}</span>
                                                </div>
                                                <div style="background: var(--border-color); height: 8px; border-radius: 4px; overflow: hidden;">
                                                    <c:set var="userPercent" value="${maxUserValue > 0 ? (stat.value * 100 / maxUserValue) : 0}"/>
                                                    <div style="background: var(--primary-color); height: 100%; width: ${userPercent}%;"></div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>

            <div class="card">
                <div class="card-header">
                    <h3>Recent Orders</h3>
                </div>
                <c:if test="${empty orders || orders.size() == 0}">
                    <p style="color: var(--text-secondary); text-align: center; padding: 2rem;">
                        No orders yet.
                    </p>
                </c:if>
                <c:if test="${not empty orders && orders.size() > 0}">
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Date</th>
                                    <th class="text-right">Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orders}" var="o">
                                    <tr>
                                        <td><strong>#${o.id}</strong></td>
                                        <td>${o.customer.username}</td>
                                        <td>${o.createdAt}</td>
                                        <td class="text-right">
                                            <strong style="color: var(--primary-color);">$${o.totalAmount}</strong>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>


