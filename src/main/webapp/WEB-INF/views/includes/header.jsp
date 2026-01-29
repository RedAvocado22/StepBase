<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <nav>
        <div class="container">
            <a href="${pageContext.request.contextPath}/" class="logo">HSF Shop</a>
            <ul class="nav-links">
                <li><a href="${pageContext.request.contextPath}/products" 
                       <c:if test="${currentPage == 'products'}">class="active"</c:if>>Products</a></li>
                <li><a href="${pageContext.request.contextPath}/cart" 
                       <c:if test="${currentPage == 'cart'}">class="active"</c:if>>Cart</a></li>
                <li><a href="${pageContext.request.contextPath}/orders" 
                       <c:if test="${currentPage == 'orders'}">class="active"</c:if>>Order History</a></li>
                <c:choose>
                    <c:when test="${sessionScope.currentUser != null}">
                        <li><a href="${pageContext.request.contextPath}/auth/profile" 
                               <c:if test="${currentPage == 'profile'}">class="active"</c:if>>Profile</a></li>
                        <c:if test="${sessionScope.currentUser.role == 'ADMIN' || sessionScope.currentUser.role == 'STAFF'}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard" 
                                   <c:if test="${currentPage == 'admin'}">class="active"</c:if>>Admin</a></li>
                        </c:if>
                        <li><a href="${pageContext.request.contextPath}/auth/logout">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/auth/login" 
                               <c:if test="${currentPage == 'login'}">class="active"</c:if>>Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth/register" 
                               <c:if test="${currentPage == 'register'}">class="active"</c:if>>Register</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth/reset-password" 
                               <c:if test="${currentPage == 'reset-password'}">class="active"</c:if>>Reset Password</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>
</header>

