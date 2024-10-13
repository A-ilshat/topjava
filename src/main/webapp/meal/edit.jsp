<%--
  Created by IntelliJ IDEA.
  User: MR_AI
  Date: 14.10.2024
  Time: 0:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Редактирование</title>
</head>
<body>
  <h3><a href="index.html">Home</a></h3>
  <hr>

  <h3>Редактирование еды</h3>

  <form method="POST" action="meals?action=edit" name="formEditMeals">
    <p>
      <input name="id" id="id" type="hidden" value="${param.id}">
    </p>

    <p>
      <label style="font-weight: bolder" for="dateTime">Дата/Время</label>
      <input name="date" id="dateTime" type="datetime-local" value="${meal.dateTime}">
    </p>

    <p>
      <label style="font-weight: bolder" for="description">Описание</label>
      <input name="description" id="description" type="text" value="${meal.description}">
    </p>

    <p>
      <label style="font-weight: bolder" for="calories">Калории</label>
      <input name="calories" id="calories" type="number" value="${meal.calories}">
    </p>

    <p>
      <input type="submit" value="Изменить">
    </p>
  </form>
  <p><a href="meals">Назад</a> </p>
</body>
</html>
