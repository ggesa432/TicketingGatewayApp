<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <style>
        .content {
            padding: 20px;
        }
    </style>
</head>
<body>
<div class="content">
    <h1>Welcome to the Ticketing Gateway</h1>
    <p>Use the navigation bar to manage your tickets.</p>
    <p> Current User: ${pageContext.request.userPrincipal.name}</p>

    <sec:authorize access="hasRole('ROLE_USER')">
        <a>Role: User</a>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <a>Role: Admin</a>
    </sec:authorize>
</div>
</body>
</html>
