<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">--%>

<html>
<head>
    <p1>welcome to simple message page</p1>
</head>
<body>
<form method="get" action="/message/send">
    <p>write your name and message, pl.)</p>
    <input type="textId" name="name">
    <input type="text" name="message">
    <input type="SUBMIT">
</form>
${result}
${error}
<form method="get" action="/message/filter">
    <p>try message filter</p>
    <input type="SUBMIT">
</form>
</body>
</html>