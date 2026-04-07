<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="bg-light">
<div class="container mt-4">
    <h1 class="mb-4">User Management</h1>

    <div class="d-flex align-items-center gap-2 mb-3">
        <a href="${pageContext.request.contextPath}/users?action=create" class="btn btn-primary">+ Add New User</a>
        <a href="${pageContext.request.contextPath}/users?action=sortByName" class="btn btn-secondary">Sort By Name</a>
        <form method="get" action="${pageContext.request.contextPath}/users" class="d-flex gap-2 ms-auto">
            <input type="hidden" name="action" value="findByCountry"/>
            <input type="text" name="country" placeholder="Enter country..." class="form-control"/>
            <button type="submit" class="btn btn-outline-primary">Find</button>
        </form>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <table class="table table-bordered table-hover bg-white">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.country}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}" class="btn btn-sm btn-warning">Edit</a>
                        <form method="post" action="${pageContext.request.contextPath}/users" style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="${user.id}"/>
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
