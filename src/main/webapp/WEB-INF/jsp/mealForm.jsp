<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="meal.title"/></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section>
    <h3><a href="${pageContext.request.contextPath}/"><spring:message code="meal.home"/></a></h3>
    <hr>
    <h2><spring:message code="${param.action == 'create' ? 'meal.add' : 'meal.update'}"/></h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="action" value="${param.action}">
        <input type="hidden" name="id" value="${meal.id}">
        <input type="hidden" name="excess" value="${meal.excess}">
        <dl>
            <dt><spring:message code="meal.date"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
