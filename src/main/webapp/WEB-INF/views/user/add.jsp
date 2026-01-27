<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Staff</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            width: 100%;
            max-width: 500px;
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            color: var(--muted);
            text-decoration: none;
            font-size: 14px;
            margin-bottom: 20px;
            padding: 8px 12px;
            border-radius: 8px;
            transition: all 0.2s ease;
        }

        .back-link:hover {
            color: var(--accent);
            background: rgba(91, 157, 255, 0.1);
        }

        .back-link::before {
            content: "←";
        }

        .panel {
            border: 1px solid var(--border);
            background: linear-gradient(180deg, rgba(17,26,46,0.9), rgba(15,23,48,0.92));
            border-radius: 14px;
            overflow: hidden;
        }

        .panel-header {
            padding: 24px 30px;
            border-bottom: 1px solid var(--border);
        }

        .panel-title {
            font-weight: 700;
            font-size: 24px;
            margin: 0;
        }

        .panel-subtitle {
            color: var(--muted);
            font-size: 14px;
            margin-top: 8px;
        }

        .panel-body {
            padding: 30px;
        }

        .alert {
            padding: 14px 16px;
            border-radius: 8px;
            margin-bottom: 24px;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .alert-error {
            background: rgba(248, 113, 113, 0.15);
            color: var(--danger);
            border: 1px solid rgba(248, 113, 113, 0.3);
        }

        .alert-error::before {
            content: "⚠";
            font-size: 16px;
        }

        .form-group {
            margin-bottom: 24px;
        }

        .form-label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            margin-bottom: 10px;
            color: var(--text);
        }

        .form-hint {
            display: block;
            font-size: 12px;
            color: var(--muted);
            margin-top: 6px;
            margin-bottom: 10px;
        }

        .form-input {
            width: 100%;
            padding: 14px 16px;
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid var(--border);
            border-radius: 8px;
            color: var(--text);
            font-size: 14px;
            transition: all 0.2s ease;
        }

        .form-input:focus {
            outline: none;
            border-color: var(--accent);
            background: rgba(255, 255, 255, 0.08);
            box-shadow: 0 0 0 3px rgba(91, 157, 255, 0.1);
        }

        .form-input::placeholder {
            color: var(--muted);
        }

        .btn-submit {
            width: 100%;
            padding: 16px;
            background: var(--accent);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s ease;
            margin-top: 10px;
        }

        .btn-submit:hover {
            background: #3d8aff;
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(91, 157, 255, 0.25);
        }

        .btn-submit:active {
            transform: translateY(0);
        }

        .login-link {
            text-align: center;
            margin-top: 24px;
            font-size: 14px;
            color: var(--muted);
        }

        .login-link a {
            color: var(--accent);
            text-decoration: none;
            font-weight: 600;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
        }

        @media (max-width: 768px) {
            body {
                padding: 16px;
                align-items: flex-start;
                padding-top: 40px;
            }

            .panel-header {
                padding: 20px;
            }

            .panel-body {
                padding: 20px;
            }

            .panel-title {
                font-size: 20px;
            }

            .form-row {
                grid-template-columns: 1fr;
                gap: 24px;
            }

            .form-input {
                padding: 12px 14px;
            }

            .btn-submit {
                padding: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/admin/users" class="back-link">Back to Users</a>

    <div class="panel">
        <div class="panel-header">
            <h1 class="panel-title">Add New Staff</h1>
            <div class="panel-subtitle">Create a new staff account for the admin panel</div>
        </div>

        <div class="panel-body">
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/admin/users/add">
                <div class="form-group">
                    <label class="form-label">Full Name</label>
                    <div class="form-hint">Enter the full name of the staff member</div>
                    <input type="text" name="fullname" class="form-input" placeholder="John Doe" required />
                </div>

                <div class="form-group">
                    <label class="form-label">Email Address</label>
                    <div class="form-hint">Use a professional email address</div>
                    <input type="email" name="email" class="form-input" placeholder="john.doe@example.com" required />
                </div>

                <div class="form-group">
                    <label class="form-label">Password</label>
                    <div class="form-hint">Set a strong password for the account</div>
                    <input type="password" name="password" class="form-input" placeholder="••••••••" required />
                </div>

                <button type="submit" class="btn-submit">Create Staff Account</button>
            </form>

            <div class="login-link">
                Need to edit existing users? <a href="${pageContext.request.contextPath}/admin/users">View all users</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>