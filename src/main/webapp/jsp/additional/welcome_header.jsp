<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 28.02.2016
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<%--session!!!--%>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title></title>
    <link href="<c:url value="/css/header.css" />" rel="stylesheet">
</head>
</head>
    <body>
    <%--from session--%>
        <div id="header">
            <span class="welcome"><fmt:message key="label.welcome"/>, ${name}</span><br>
            <span class="role"><fmt:message key="label.role"/> ${role}</span><br>
        </div>
    </body>
</html>
