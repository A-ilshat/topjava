<%--
  Created by IntelliJ IDEA.
  User: MR_AI
  Date: 13.10.2024
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <p>
        <label style="font-weight: bolder" for="calories">Норма калории</label>
        <input type="number" id="calories">
    </p>

    <p>
        <a href="meals?action=new">Добавить</a>
        <a href="meals?action=clear">Очистить список</a>
    </p>
    <table border="1">
        <thead>
            <tr bgcolor="#bdb76b">
                <th scope="col">Дата/Время</th>
                <th scope="col">Описание</th>
                <th scope="col">Калорий</th>
                <th scope="col">Действия</th>
            </tr>
        </thead>
        <tbody style="font-weight: bold">
        <c:forEach var="meal" items="${meals}">
            <tr style="${meal.excess ? 'background-color: red' : 'background-color: green'}">
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
                </td>
                <td>
                    <c:out value="${meal.description}"/>
                </td>
                <td>
                    <c:out value="${meal.calories}"/>
                </td>

                <td>
                    <a href="meals?&action=edit&id=${meals.indexOf(meal)}">Редактировать</a>
                    <a href="meals?&action=delete&id=${meals.indexOf(meal)}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</body>
</html>