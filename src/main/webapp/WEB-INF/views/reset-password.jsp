<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
</head>
<body>
<h1>Reset Your Password</h1>
<form action="/reset-password" method="post">
    <input type="hidden" name="token" value="${token}">
    <label for="newPassword">New Password:</label>
    <input type="password" id="newPassword" name="newPassword" required><br>
    <button type="submit">Reset Password</button>
</form>
</body>
</html>
