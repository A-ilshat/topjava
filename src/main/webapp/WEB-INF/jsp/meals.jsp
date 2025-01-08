<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script src="resources/js/topjava.common.js" defer></script>
<script src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <section>
            <h3><spring:message code="meal.title"/></h3>
            <div class="accordion" id="accordion2">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                           href="#collapseOne">
                            <button class="btn btn-info">Фильтр</button>
                            <hr>
                        </a>
                    </div>
                    <div id="collapseOne" class="accordion-body collapse in">
                        <div class="accordion-inner">
                            <div class="container border border-secondary rounded">
                                <form method="get" action="meals/filter">
                                    <div class="row">
                                        <div class="col">
                                            <label for="startDate" class="form-label"><spring:message code="meal.startDate"/>:</label>
                                            <input type="date" name="startDate" class="form-control" id="startDate" value="${param.startDate}">
                                        </div>

                                        <div class="col">
                                            <label for="endDate" class="form-label"><spring:message code="meal.startDate"/>:</label>
                                            <input type="date" name="endDate" class="form-control" id="endDate" value="${param.endDate}">
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label for="startTime" class="form-label"><spring:message code="meal.startTime"/>:</label>
                                            <input type="time" name="startTime" class="form-control" id="startTime" value="${param.startTime}">
                                        </div>

                                        <div class="col">
                                            <label for="endTime" class="form-label"><spring:message code="meal.endTime"/>:</label>
                                            <input type="time" name="endTime" class="form-control" id="endTime" value="${param.endTime}">
                                        </div>
                                    </div>
                                    <br>
                                    <button type="submit" class="btn btn-success btn-sm pull-right"><spring:message code="meal.filter"/></button>
                                </form><br>
                            </div>
                        </div>
                    </div>
                </div>
            </div><br>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="meal.add"/>
            </button>
            <hr>
            <table class="table" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${requestScope.meals}" var="meal">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>

                    <tr id="${meal.id}" class="${meal.excess ? 'table table-danger' : 'table table-success'}">
                            <%--                <tr data-meal-excess="${meal.excess}">--%>
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a class="edit"><span class="fa fa-edit"></span></a></td>
                        <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" name="dateTime"
                               id="dateTime" placeholder="<spring:message code="meal.dateTime"/>" required>
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" size=40 name="description"
                               id="description" placeholder="<spring:message code="meal.description"/>" required>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" name="calories"
                               id="calories" placeholder="<spring:message code="meal.calories"/>" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>