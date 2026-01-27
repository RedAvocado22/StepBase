<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order List</title>
    <style>
        :root {
            --bg: #0b1220;
            --panel: #111a2e;
            --panel-2: #0f1730;
            --text: #e6eaf2;
            --muted: #9aa6c3;
            --border: rgba(255,255,255,0.08);
            --accent: #5b9dff;
        }

        * { box-sizing: border-box; }

        body {
            margin: 0;
            font-family: ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Arial, "Apple Color Emoji", "Segoe UI Emoji";
            background: radial-gradient(1200px 600px at 20% 10%, rgba(91,157,255,0.18), transparent 60%),
            radial-gradient(1200px 600px at 80% 10%, rgba(120,255,214,0.12), transparent 60%),
            var(--bg);
            color: var(--text);
            min-height: 100vh;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
            padding: 24px;
        }

        .header {
            display: flex;
            align-items: baseline;
            justify-content: space-between;
            border: 1px solid var(--border);
            background: linear-gradient(180deg, rgba(17,26,46,0.9), rgba(17,26,46,0.6));
            border-radius: 14px;
            padding: 18px 18px;
            margin-bottom: 18px;
        }

        .title {
            font-weight: 700;
            font-size: 18px;
        }

        .subtitle {
            color: var(--muted);
            font-size: 13px;
        }

        .panel {
            border: 1px solid var(--border);
            background: linear-gradient(180deg, rgba(17,26,46,0.9), rgba(15,23,48,0.92));
            border-radius: 14px;
            overflow: hidden;
        }

        .panel-title {
            padding: 12px 14px;
            border-bottom: 1px solid var(--border);
            font-weight: 650;
        }

        .table-wrap {
            padding: 8px 10px 12px 10px;
            overflow-x: auto;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
        }

        .table th,
        .table td {
            text-align: left;
            padding: 10px 8px;
            border-bottom: 1px solid var(--border);
            vertical-align: middle;
        }

        .table th {
            color: var(--muted);
            font-weight: 600;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .table tbody tr:hover {
            background: rgba(255, 255, 255, 0.03);
        }

        .status-active {
            color: #4ade80;
            font-weight: 600;
        }

        .status-pending {
            color: #fbbf24;
            font-weight: 600;
        }

        .status-cancelled {
            color: #f87171;
            font-weight: 600;
        }

        .amount {
            font-weight: 650;
            color: var(--accent);
        }

        @media (max-width: 768px) {
            .container {
                padding: 16px;
            }

            .table-wrap {
                padding: 4px 6px 8px 6px;
            }

            .table th,
            .table td {
                padding: 8px 6px;
                font-size: 12px;
            }

            .header {
                padding: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div>
            <h1 class="title">Order List</h1>
            <div class="subtitle">Total Orders: ${orders.size()}</div>
        </div>
        <div class="subtitle">Admin Panel</div>
    </div>

    <div class="panel">
        <div class="panel-title">Order Details</div>
        <div class="table-wrap">
            <table class="table">
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
                        <td>#${o.id}</td>
                        <td>${o.consigneeName}</td>
                        <td>${o.phone}</td>
                        <td>${o.orderDate}</td>
                        <td class="amount">$${o.totalAmount}</td>
                        <td>
                            <c:choose>
                                <c:when test="${o.orderStatus == 'COMPLETED' || o.orderStatus == 'DELIVERED'}">
                                    <span class="status-active">${o.orderStatus}</span>
                                </c:when>
                                <c:when test="${o.orderStatus == 'PENDING' || o.orderStatus == 'PROCESSING'}">
                                    <span class="status-pending">${o.orderStatus}</span>
                                </c:when>
                                <c:when test="${o.orderStatus == 'CANCELLED' || o.orderStatus == 'REFUNDED'}">
                                    <span class="status-cancelled">${o.orderStatus}</span>
                                </c:when>
                                <c:otherwise>
                                    ${o.orderStatus}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>