<%--
  Created by IntelliJ IDEA.
  User: MR_AI
  Date: 13.10.2024
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h3>
        Добавление еды в список
    </h3>
    <form method="POST" action="meals?action=new" name="formNewMeals">
        <p>
            <label style="font-weight: bolder" for="dateTime">Дата/Время</label>
            <input name="date" id="dateTime" type="datetime-local">
        </p>

        <p>
            <label style="font-weight: bolder" for="description">Описание</label>
            <input name="description" id="description" type="text">
        </p>

        <p>
            <label style="font-weight: bolder" for="calories">Калории</label>
            <input name="calories" id="calories" type="number">
        </p>

        <p>
            <input type="submit" value="Добавить">
        </p>

        <p><a href="meals">Назад</a> </p>
    </form>
</body>
</html>
