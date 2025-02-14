<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:include page="navbar.jsp" />--%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            font-weight: bold;
        }
        .form-group input {
            padding: 8px;
            width: 100%;
            max-width: 300px;
        }
        .submit-button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-weight: bold;
            margin-top: 10px;
        }
        .submit-button:hover {
            background-color: #0056b3;
        }
        .links {
            margin-top: 20px;
        }
        .links a {
            margin: 0 10px;
            text-decoration: none;
            color: #007bff;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-top: 10px;
        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>Login</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="submit-button">Login</button>
    </form>
    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>

    <c:if test="${not empty success}">
        <p class="success-message">${success}</p>
    </c:if>
    <div class="links">
        <a href="/register">Register</a> |
        <a href="/forgot-password">Forgot Password?</a>
    </div>
</div>
</body>
</html>
