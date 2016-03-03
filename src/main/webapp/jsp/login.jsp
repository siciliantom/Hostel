<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>--%>
<fmt:setLocale value="${language}" scope="session"/>
<%--session!!!--%>
<fmt:setBundle basename="pagecontent"/>
<html>

<head>
    <title><fmt:message key="title.login"/></title>
    <link href="<c:url value="/css/login.css"/>" rel="stylesheet">
</head>
<%--<jsp:include page="/jsp/additional/header.jsp"/>--%>
<%--<c:set var="admin" value="Kin" scope="request"/>--%>

<body>
<c:import url="/jsp/additional/header.jsp"/>

<div id="section">
    <span class="welcome"><fmt:message key="label.welcome"/></span>
    <span class="hostel">&nbsp<fmt:message key="label.hostel"/></span>
    <br><br><br><br><br><br>
    <form name="loginForm" method="POST" action="controller">
        <span class="please"><fmt:message key="label.please"/></span><br>
        <br><br>
        <table border="0" align="center">
            <tr>
                <td><span class="logpass"><fmt:message key="label.login"/></span></td>
                <td><input type="text" name="login" value=""/></td>
            </tr>
            <tr>
                <td><span class="logpass"><fmt:message key="label.password"/></span></td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td align="center"><a href="/controller?command=show_reg_form" name="command" value="showRegForm">
                    <fmt:message key="label.can_register"/></a></td>
                <td align="center"><input type="hidden" name="command" value="login"/>
                    <fmt:message key="button.log_in" var="nam"/>
                    <input type="submit" name="login" value=${nam}></td>
            </tr>
        </table>
        <%--<form method=post action=controller>--%>
        <%--<input type=hidden name=command value=register>--%>
        <%--<input type="submit" value="Register"/>--%>
        <%--</form></td>--%>

        <br/>
        ${errorLoginPassMessage}
        <br/>
        ${wrongAction}
        <br/>
        ${nullPage}
        <br/>
    </form>

</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>

</html>