<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" scope="session"/>
<%--from session!!!--%>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.order"/></title>
    <%--<link href="<c:url value="/css/order_application.css" />" rel="stylesheet">--%>
    <link href="<c:url value="/css/login.css" />" rel="stylesheet">
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section" align="center">
    order
    Name: ${client.name}
    Surname${client.surname}
    <br>
    <br>
    <br>
    <table border="0">
        <h3>Clients list:</h3>
        <c:forEach var="client" items="${requestScope.List}">
            <tr>
                <td><span style="color:black; font-size: 20pt">Id</span></td>
                <td colspan="2" align="left"><span style="color:red; font-size: 18pt">${client.id} </span> <span
                        style="color:black; font-size: 18pt"> - </span> <span
                        style="color:red; font-size: 18pt"> ${returnModel.lastCity}</span></td>
            </tr>
            <tr>
                <td><span style="color:black; font-size: 20pt">Name</span></td>
                <td><span style="color:red; font-size: 18pt">${client.name}</span></td>
            </tr>
            <tr>
                <td><span style="color:black; font-size: 20pt">Surname:</span></td>
                <td><span style="color:red; font-size: 18pt">${client.surname}</span></td>
            </tr>
            <%--<tr>--%>
            <%--<td><span style="color:black; font-size: 20pt">DepartureCity:</span></td>--%>
            <%--<td><span style="color:red; font-size: 18pt">${returnModel.departureCity}</span></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td><span style="color:black; font-size: 20pt">DepartureTime:</span></td>--%>
            <%--<td> <span style="color:red; font-size: 18pt">${returnModel.departureTime}</span></td>--%>
            <%--</tr>--%>
        </c:forEach>
    </table>
    <%--<c:forEach var="route" items="${returnModel}">--%>
    <%--<c:out value="${route.getFirstCity}"/> -  <c:out value="${route.LastCity}"/><p>--%>

    <%--//</c:forEach>--%>

</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>