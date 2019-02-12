<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Users</title>
</head>
<body>
<table border=1>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${meals}" var="meal">
                <c:if test="${meal.excess=='true'}">
                    <c:set var="bColor" value="#FF0000"/>
                </c:if>
                <c:if test="${meal.excess=='false'}">
                    <c:set var="bColor" value="#FFFF00"/>
                </c:if>
                <tr bgcolor="${bColor}">
                    <td><c:out value="${meal.id}"/></td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                    <td><fmt:formatDate pattern="yyyy-MMM-dd HH:mm" value="${parsedDate}" /></td>
                    <td><c:out value="${meal.description}" /></td>
                    <td><c:out value="${meal.calories}" /></td>
                    <td><a href="meals?action=upgrade&mealId=<c:out value="${meal.id}"/>">Upgrade</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="meals?action=create">Add Meal</a></p>
<h3><a href="index.html">Home</a></h3>
</body>
</html>