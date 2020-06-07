<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meal management</title>
</head>
<body>




    <c:if test="${meal != null}">
        <form action="meals" method="post">
    </c:if>

    <c:if test="${meal == null}">
        <form action="meals" method="post">
    </c:if>

            <table>
                <caption>
                    <h2>
                        <c:if test="${meal != null}">
                            Edit Book
                        </c:if>
                        <c:if test="${meal == null}">
                            Add New Book
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${meal != null}">
                    <input type="hidden" name="id" value="<c:out value='${meal.id}' />" />
                </c:if>
                <tr>
                    <th>Date\Time: </th>
                    <td>
                        <input type="datetime-local" name="date" size="45"
                               value="${meal.dateTime}"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Description: </th>
                    <td>
                        <input type="text" name="desc" size="45"
                               value="<c:out value='${meal.description}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Calories: </th>
                    <td>
                        <input type="text" name="calories" size="5"
                               value="<c:out value='${meal.calories}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save" />
                    </td>
                </tr>
            </table>
        </form>



</body>
</html>
