<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
<head>
    <title>Raise a Ticket</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding-top: 60px;
        }
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }
        .form-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 400px;
            padding: 20px;
            margin: 20px auto;
        }
        .form-container h2 {
            margin-bottom: 20px;
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .submit-button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            width: 100%;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }
        .submit-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Raise a Ticket</h2>
    <form action="/ticket" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="department">Department</label>
            <input type="text" id="department" name="department" value="FINANCE">
        </div>
        <div class="form-group">
            <label for="priority">Priority</label>
            <select id="priority" name="priority">
                <option value="low">LOW</option>
                <option value="medium">MEDIUM</option>
                <option value="high">HIGH</option>
            </select>
        </div>
        <div class="form-group">
            <label for="type">Type</label>
            <select id="type" name="type">
                <option value="purchase">PURCHASE</option>
                <option value="maintenance">MAINTENANCE</option>
                <option value="support">SUPPORT</option>
            </select>
        </div>
        <div class="form-group">
            <label for="due-date">Due date</label>
            <input type="date" id="due-date" name="due-date" value="2024-09-01">
        </div>
        <div class="form-group">
            <label for="project">Project</label>
            <select id="project" name="project">
                <option value="finance">Finance</option>
                <option value="marketing">Marketing</option>
                <option value="development">Development</option>
                <option value="software">Software</option>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <input type="text" id="description" name="description" value="Acme Accounting App">
        </div>
        <div class="form-group">
            <label for="amount">Amount</label>
            <input type="number" id="amount" name="amount" value="500">
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="images">Upload Screenshots</label>
            <input type="file" id="images" name="images" multiple accept="image/*">
        </div>
        <button type="submit" class="submit-button">SUBMIT</button>
    </form>
</div>
</body>
</html>
