<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="context_path" value="${pageContext.request.contextPath}"/>
<c:set var="isNewMeal" value="${meal.id == null}"/>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="${context_path}/resources/css/style.css">
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href="${context_path}"><spring:message code="app.home"/></a></h3>
    <hr>
    <c:set var="mealMessage" value="${isNewMeal ? 'meal.createMeal' : 'meal.editMeal'}"/>
    <h2><spring:message code="${mealMessage}"/></h2>
    <%--    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
    <form:form action="${isNewMeal ? 'create' : 'update'}" method="post" modelAttribute="meal">
        <form:hidden path="id"/>
        <c:if test="${!isNewMeal}">
            <input type="hidden" name="_method" value="PATCH">
        </c:if>

        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
                <%--            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>--%>
            <dd><form:input path="dateTime" type="datetime-local"/></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
                <%--            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>--%>
            <dd><form:input path="description" type="text"/></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>::</dt>
                <%--            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>--%>
            <dd><form:input path="calories" type="number"/></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"/></button>
    </form:form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
