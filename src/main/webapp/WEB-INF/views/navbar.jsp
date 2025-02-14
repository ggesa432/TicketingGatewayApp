<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .navbar {
        overflow: hidden;
        background-color: #333;
        display: flex;
        justify-content: space-around;
        padding: 14px 20px;

    }

    .navbar a {
        color: white;
        text-decoration: none;
        padding: 14px 20px;
        font-weight: bold;
    }

    .navbar a:hover {
        background-color: #ddd;
        color: black;
    }
</style>
<div class="navbar">
    <a href="/home">Home</a>
    <a href="/ticket">Submit Ticket</a>
    <a href="/ticket-history">Ticket History</a>
    <a href="/approve-tickets">Approve Tickets</a>
    <a href="/login">Login</a>
    <a href="/logout">Logout</a>
</div>
