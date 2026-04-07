<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="bg-light">
<div class="container mt-4">
    <a href="${pageContext.request.contextPath}/users" class="btn btn-secondary mb-3">&#8592; Back to List</a>
    <div class="card" style="max-width: 500px;">
        <div class="card-header">
            <h4 class="mb-0">Add New User</h4>
        </div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/users">
                <input type="hidden" name="action" value="create"/>
                <div class="mb-3">
                    <label for="name" class="form-label">User Name</label>
                    <input type="text" name="name" id="name" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" name="email" id="email" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label for="country" class="form-label">Country</label>
                    <input type="text" name="country" id="country" class="form-control"/>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
