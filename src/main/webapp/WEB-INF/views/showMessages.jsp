<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p>Messages ---></p>

<c:if test="${not empty messages}">
    <c:forEach items="${messages}" var="message">
        <p> ${message} </p>
    </c:forEach>
</c:if>