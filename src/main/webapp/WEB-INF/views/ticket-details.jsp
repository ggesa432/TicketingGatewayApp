<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
<head>
    <title>Ticket Details</title>
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
        .screenshot-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 10px;
        }
        .screenshot-container img {
            max-width: 300px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h2>Ticket Details</h2>
<p><strong>ID:</strong> ${ticket.id}</p>
<p><strong>Department:</strong> ${ticket.department}</p>
<p><strong>Priority:</strong> ${ticket.priority}</p>
<p><strong>Type:</strong> ${ticket.type}</p>
<p><strong>Due Date:</strong> ${ticket.dueDate}</p>
<p><strong>Project:</strong> ${ticket.project}</p>
<p><strong>Description:</strong> ${ticket.description}</p>
<p><strong>Amount:</strong> ${ticket.amount}</p>

<h3>Ticket History</h3>
<table>
    <thead>
    <tr>
        <th>Changed By</th>
        <th>Changed At</th>
        <th>Field Changed</th>
        <th>Old Value</th>
        <th>New Value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="history" items="${ticketHistory}">
        <tr>
            <td>${history.changedBy}</td>
            <td>${history.changedAt}</td>
            <td>${history.fieldChanged}</td>
            <td>${history.oldValue}</td>
            <td>${history.newValue}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Uploaded Screenshots</h3>
<c:if test="${not empty ticket.imagePaths}">
    <div class="screenshot-container">
        <c:forEach var="imagePath" items="${ticket.imagePaths}">
            <img src="/tickets/${imagePath}" alt="Screenshot" style="max-width: 300px;">
            <a href="/tickets/download/${ticket.id}/${imagePath}" download>Download</a>
        </c:forEach>
    </div>
</c:if>
<c:if test="${empty ticket.imagePaths}">
    <p>No screenshots uploaded.</p>
</c:if>

<a href="/approve-tickets">Back to Approve Tickets</a>
</body>
</html>
