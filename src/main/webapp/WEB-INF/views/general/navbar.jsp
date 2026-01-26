<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="container">
        <nav class="navbar navbar-expand-lg bg-light border-bottom">
            <div class="container-fluid">
                <a class="navbar-brand" href="#"></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/product">Sản Phẩm</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/orders/history">Lịch Sử Đơn Hàng</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ Hàng</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>