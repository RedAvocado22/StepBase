<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <style>
        :root {
            --bg: #0b1220;
            --panel: #111a2e;
            --panel-2: #0f1730;
            --text: #e6eaf2;
            --muted: #9aa6c3;
            --border: rgba(255,255,255,0.08);
            --accent: #5b9dff;
            --success: #4ade80;
            --danger: #f87171;
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
            align-items: center;
            justify-content: space-between;
            border: 1px solid var(--border);
            background: linear-gradient(180deg, rgba(17,26,46,0.9), rgba(17,26,46,0.6));
            border-radius: 14px;
            padding: 18px 24px;
            margin-bottom: 18px;
        }

        .title-section h1 {
            margin: 0;
            font-weight: 700;
            font-size: 22px;
        }

        .subtitle {
            color: var(--muted);
            font-size: 13px;
            margin-top: 4px;
        }

        .btn-add {
            background: var(--accent);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            transition: all 0.2s ease;
        }

        .btn-add:hover {
            background: #3d8aff;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(91, 157, 255, 0.25);
        }

        .btn-add::before {
            content: "+";
            font-size: 18px;
        }

        .panel {
            border: 1px solid var(--border);
            background: linear-gradient(180deg, rgba(17,26,46,0.9), rgba(15,23,48,0.92));
            border-radius: 14px;
            overflow: hidden;
        }

        .panel-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 16px 20px;
            border-bottom: 1px solid var(--border);
        }

        .panel-title {
            font-weight: 650;
            font-size: 16px;
        }

        .stats {
            display: flex;
            gap: 20px;
        }

        .stat-item {
            text-align: right;
        }

        .stat-label {
            color: var(--muted);
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .stat-value {
            font-size: 18px;
            font-weight: 700;
            margin-top: 2px;
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
            padding: 12px 16px;
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

        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
            letter-spacing: 0.3px;
        }

        .badge-admin {
            background: rgba(91, 157, 255, 0.15);
            color: var(--accent);
            border: 1px solid rgba(91, 157, 255, 0.3);
        }

        .badge-user {
            background: rgba(154, 166, 195, 0.1);
            color: var(--muted);
            border: 1px solid var(--border);
        }

        .badge-active {
            background: rgba(74, 222, 128, 0.15);
            color: var(--success);
            border: 1px solid rgba(74, 222, 128, 0.3);
        }

        .badge-inactive {
            background: rgba(248, 113, 113, 0.15);
            color: var(--danger);
            border: 1px solid rgba(248, 113, 113, 0.3);
        }

        .email-cell {
            color: var(--accent);
            font-weight: 500;
        }

        @media (max-width: 768px) {
            .container {
                padding: 16px;
            }

            .header {
                flex-direction: column;
                align-items: flex-start;
                gap: 16px;
                padding: 16px;
            }

            .panel-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 12px;
                padding: 14px;
            }

            .stats {
                width: 100%;
                justify-content: space-between;
            }

            .table th,
            .table td {
                padding: 10px 12px;
                font-size: 12px;
            }

            .btn-add {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="title-section">
            <h1>User Management</h1>
            <div class="subtitle">Manage staff and user accounts</div>
        </div>
        <a href="/admin/users/add" class="btn-add">Add Staff</a>
    </div>

    <div class="panel">
        <div class="panel-header">
            <div class="panel-title">User List</div>
            <div class="stats">
                <div class="stat-item">
                    <div class="stat-label">Total Users</div>
                    <div class="stat-value">${users.size()}</div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">Admins</div>
                    <div class="stat-value">
                        <c:set var="adminCount" value="0" />
                        <c:forEach var="u" items="${users}">
                            <c:if test="${u.admin}">
                                <c:set var="adminCount" value="${adminCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${adminCount}
                    </div>
                </div>
            </div>
        </div>

        <div class="table-wrap">
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>#${u.id}</td>
                        <td>
                            <div style="font-weight: 600;">${u.fullname}</div>
                            <div class="subtitle" style="font-size: 11px; margin-top: 2px;">User ID: ${u.id}</div>
                        </td>
                        <td class="email-cell">${u.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.admin}">
                                    <span class="badge badge-admin">Administrator</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-user">Staff</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.isActive}">
                                    <span class="badge badge-active">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-inactive">Inactive</span>
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