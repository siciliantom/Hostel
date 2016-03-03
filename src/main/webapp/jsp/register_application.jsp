<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 18.02.2016
  Time: 7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${language}" scope="session"/>
<%--from session!!!--%>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.register"/></title>
    <%--<link href="<c:url value="/css/register_application.css"/>" rel="stylesheet">--%>
    <link href="<c:url value="/css/login.css" />" rel="stylesheet">
    <%--<link href="<c:url value="/css/register_application.css" />" rel="stylesheet">--%>
</head>
<body>
<c:import url="/jsp/additional/header.jsp"/>
<div id="section" align="center">
    <br><br>
    <span class="please"><fmt:message key="label.fill_register"/></span><br><br><br><br><br>
    <form name="registerForm" method="POST" action="controller">
        <table border="0" align="center">
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"> <fmt:message key="label.name"/>*</span></td>
                <td><input type="text" name="firstname"><br></td>
            </tr>
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.surname"/>*</span></td>
                <td><input type="text" name="lastname"><br></td>
            </tr>
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.country"/>*</span></td>
                <td align="center"><input type="text" name="country"><br/></td>
            </tr>
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.login"/>*</span></td>
                <td align="center"><input type="text" name="login"><br/></td>
            </tr>
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.password"/>*</span></td>
                <td align="center"><input type="password" name="password"><br/></td>
            </tr>
            <tr>
                <td><span style="color:whitesmoke; font-size: 20pt"><fmt:message key="label.role"/>*</span></td>
                <td align="center"><input type="text" name="role"><br/></td>
            </tr>
            <tr>
                <td></td>
                <td align="right"><br><input type="hidden" name="command" value="register"/>
                    <input type="submit" name="register" value=<fmt:message key="button.register"/>></td>
            </tr>
        </table>
    </form>
    <br>
    ${errorClientExists}
    ${wrongRegistration}
    <br>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
