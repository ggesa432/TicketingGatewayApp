<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
<head>
    <title>Ticket History</title>
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
        .action-buttons form {
             display: inline;
         }
    </style>
</head>
<body>
<h2>All Tickets Raised</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Department</th>
        <th>Priority</th>
        <th>Type</th>
        <th>Due Date</th>
        <th>Project</th>
        <th>Description</th>
        <th>Amount</th>
        <th>Status</th>
        <th>Progress</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty message}">
        <div style="color: green;">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>

    <c:forEach var="ticket" items="${allTickets}">
        <tr>
            <td>${ticket.id}</td>
            <td>${ticket.department}</td>
            <td>${ticket.priority}</td>
            <td>${ticket.type}</td>
            <td>${ticket.dueDate}</td>
            <td>${ticket.project}</td>
            <td>${ticket.description}</td>
            <td>${ticket.amount}</td>
            <td>${ticket.status}</td>
            <td>${ticket.progress}</td>
            <td class="action-buttons">
                <form action="/mark-closed/${ticket.id}" method="post">
                    <button type="submit" ${ticket.progress == 'Closed' ? 'disabled' : ''}>Mark Closed</button>
                </form>
            </td>
            <td class="action-buttons">
                <form action="/tickets/reopen/${ticket.id}" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" ${ticket.reopenable ? '' : 'disabled'}>Reopen</button>
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>


