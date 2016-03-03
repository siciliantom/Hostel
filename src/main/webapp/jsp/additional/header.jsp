<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 24.02.2016
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<%--session!!!--%>
<fmt:setBundle basename="pagecontent"/>
<html>
<head><title>Header</title>
    <link href="<c:url value="/css/header.css" />" rel="stylesheet">
</head>
<body>
<div id="header">
    <br>
    <span style="align:left">
        <select name="locale">
        <option value="rus">Ru</option>
        <option value="en">En</option>
    </select>
        </span>
    <span class="hostel_name">Stranger's Sight</span><br>
</div>
</body>
</html>