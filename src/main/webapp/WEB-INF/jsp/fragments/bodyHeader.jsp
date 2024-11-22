<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context_path" value="${pageContext.request.contextPath}"/>

<header>
    <a href="${context_path}/meals"><spring:message code="app.title"/></a> | <a href="${context_path}/users"><spring:message code="user.title"/></a> | <a href="${context_path}"><spring:message code="app.home"/></a>
</header>