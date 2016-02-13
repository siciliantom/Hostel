<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>--%>
<fmt:setLocale value="ru_RU" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>

<head>
    <title>Login</title>
    <style type="text/css">
        body {
            background: url(/pictures/york.jpg) no-repeat;
            background-size: cover;}
    </style>

</head>
<body>
<br><br>
<div align="center">
    <span style="color:whitesmoke; font-size: 25pt"><fmt:message key="label.welcome"/></span><br><br>
<form name="loginForm" method="POST" action="controller" >
    <input type="hidden" name="command" value="login" />
    <br/>
   <span style="color:whitesmoke; font-size: 20pt"> <fmt:message key="label.login"/></span>
    <input type="text" name="login" value=""/>
    <br/><br/>
    <span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.password"/></span>
    <input type="password" name="password" value=""/><br/><br/>
    <%--<br/>--%>
    <%--${errorLoginPassMessage}--%>
    <%--<br/>--%>
    <%--${wrongAction}--%>
    <%--<br/>--%>
    <%--${nullPage}--%>
    <%--<br/>--%>
    <input type="submit" value="Log in"/>
</form>
</div>
</body></html>