<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="isNewMeal" value="${meal.id == null}"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <hr>
    <c:set var="mealMessage" value="${isNewMeal ? 'meal.createMeal' : 'meal.editMeal'}"/>
    <h2><spring:message code="${mealMessage}"/></h2>

    <form:form action="${isNewMeal ? 'create' : 'update'}" method="POST" modelAttribute="meal">
        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
            <dd><form:input path="dateTime" type="datetime-local"/></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><form:input path="description" type="text"/></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>::</dt>
            <dd><form:input path="calories" type="number"/></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"/></button>
    </form:form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
