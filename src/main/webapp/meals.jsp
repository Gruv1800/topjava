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
                </tr>
            </c:forEach>
        </tbody>
    </table>
<h3><a href="index.html">Home</a></h3>
</body>
</html>