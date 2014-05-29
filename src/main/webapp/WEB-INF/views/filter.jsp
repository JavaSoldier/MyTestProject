<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <p>welcome to filter message page</p>

    <p>shoose name and dates to filter messages</p>
</head>
<body>
<form method="post" action="/message/filter">
    <p>select name</p>
    <select id="nameId" name="name">
        <option value="All">ALL</option>
        <c:forEach items="${Users}" var="user">
            <option value="<c:out value="${user.userName}"/>"><c:out value="${user.userName}"/></option>
        </c:forEach>
    </select>

    <p> Fromdate: </p> <input type="date" name="fromDate">

    <p> toDate: </p> <input type="date" name="toDate">

    <input type="submit" value="filter">
</form>
${error}
</body>
</html>
