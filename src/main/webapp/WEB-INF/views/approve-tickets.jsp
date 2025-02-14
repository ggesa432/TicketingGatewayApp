<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
<head>
    <title>Approve Tickets</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        .action-buttons form {
            display: inline;
        }
    </style>
</head>
<body>
<h2>Approve Tickets</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Department</th>
        <th>Priority</th>
        <th>Type</th>
        <th>Status</th>
        <th>Progress</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ticket" items="${allTickets}">
        <tr>
            <td>${ticket.id}</td>
            <td>${ticket.department}</td>
            <td>${ticket.priority}</td>
            <td>${ticket.type}</td>
            <td>${ticket.status}</td>
            <td>${ticket.progress}</td>
            <td class="action-buttons">
                <a href="/ticket-details/${ticket.id}">Details</a>
                <form action="/approve-ticket/${ticket.id}" method="post">
                    <button type="submit">Approve</button>
                </form>
                <form action="/decline-ticket/${ticket.id}" method="post">
                    <button type="submit">Decline</button>
                </form>
                <form action="/delete-ticket/${ticket.id}" method="post">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

