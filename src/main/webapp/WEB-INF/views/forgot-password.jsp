<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
</head>
<body>
<h1>Forgot Password</h1>
<form action="/forgot-password" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>
    <button type="submit">Reset Password</button>
</form>
<p style="color:red">${error}</p>
<p style="color:green">${message}</p>
</body>
</html>
