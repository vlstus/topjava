<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>Meals</h2>


<table>
    <jsp:useBean id="mealsList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsList}" var="meal">
        <c:set var="exceed" value="${meal.excess}"/>
        <c:choose>
            <c:when test="${exceed eq false}">
                <tr>
                    <td style="color: green"><c:out value="${meal}"/></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td style="color: red"><c:out value="${meal}"/></td>
                </tr>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</table>

</body>

</html>
