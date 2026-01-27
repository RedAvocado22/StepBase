<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password - StepBase</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .auth-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 100%;
            max-width: 400px;
        }

        .auth-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .auth-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 8px;
        }

        .auth-header p {
            color: #666;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }

        .btn {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .btn:active {
            transform: translateY(0);
        }

        .alert {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-error {
            background-color: #fee;
            color: #c33;
            border: 1px solid #fcc;
        }

        .alert-success {
            background-color: #efe;
            color: #3c3;
            border: 1px solid #cfc;
        }

        .reset-code-box {
            background: #f5f5f5;
            border: 2px dashed #667eea;
            border-radius: 8px;
            padding: 15px;
            margin: 20px 0;
            text-align: center;
        }

        .reset-code-box code {
            font-size: 16px;
            font-weight: 600;
            color: #667eea;
            word-break: break-all;
        }

        .reset-link {
            display: block;
            margin-top: 15px;
            text-align: center;
        }

        .reset-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }

        .reset-link a:hover {
            text-decoration: underline;
        }

        .auth-footer {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #666;
        }

        .auth-footer a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }

        .auth-footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="auth-container">
        <div class="auth-header">
            <h1>Forgot Password</h1>
            <p>Enter your email to reset your password</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <c:if test="${not empty resetCode}">
            <div class="reset-code-box">
                <p style="margin-bottom: 10px; color: #666; font-size: 14px;">Your reset code:</p>
                <code>${resetCode}</code>
                <div class="reset-link">
                    <a href="${pageContext.request.contextPath}/reset-password?code=${resetCode}">Click here to reset your password</a>
                </div>
            </div>
        </c:if>

        <c:if test="${empty resetCode}">
            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${email}" required autofocus>
                </div>

                <button type="submit" class="btn">Send Reset Code</button>
            </form>
        </c:if>

        <div class="auth-footer">
            <a href="${pageContext.request.contextPath}/login">Back to Login</a>
        </div>
    </div>
</body>
</html>
