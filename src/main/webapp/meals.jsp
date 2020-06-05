<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/table_style.css"/>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>Meals</h2>

<table>

    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>

    <tbody>



    <jsp:useBean id="mealsList" scope="request" type="java.util.List"/>

    <c:forEach items="${mealsList}" var="meal">

        <c:set var="exceed" value="${meal.excess}"/>
        <c:set var="dateTime" value="${meal.dateTime}"/>
        <c:set var="description" value="${meal.description}"/>
        <c:set var="calories" value="${meal.calories}"/>

        <c:choose>
            <c:when test="${exceed eq false}">
                <style>
                    tr{
                        color: red;
                    }
                </style>
                <tr>
                    <td style="color: red"><javatime:format value="${dateTime}" style="MS"/></td>
                    <td style="color: red"><c:out value="${description}"/></td>
                    <td style="color: red"><c:out value="${calories}"/></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td style="color: green"><javatime:format value="${dateTime}" style="MS"/></td>
                    <td style="color: green"><c:out value="${description}"/></td>
                    <td style="color: green"><c:out value="${calories}"/></td>
                </tr>
            </c:otherwise>
        </c:choose>

    </c:forEach>

    </tbody>
</table>


</body>

</html>
