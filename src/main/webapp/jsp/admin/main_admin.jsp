<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 23.02.2016
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Home_admin</title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet"/>
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <%--<form name="backForm" method="POST" action="controller">--%>
    <%--<input type="hidden" name="command" value="back"/>--%>
    <%--<input class="back_button" type="submit" value=<fmt:message key="label.back"/>>--%>
    <%--</form>--%>
    <c:if test="${not empty applicationListAdmin || not empty applicationConfirmedListAdmin || not empty bannedApplications}">
        <h3>
            <span class="price"><fmt:message key="label.current_applications"/></span>
        </h3>
        <table border="0" align="left">
            <tr class="content">
                <th></th>
                    <%--<th><fmt:message key="label.number"/></th>--%>
                <th><fmt:message key="label.places"/></th>
                    <%--<th><fmt:message key="label.payment"/></th>--%>
                <th><fmt:message key="label.arrival"/></th>
                <th><fmt:message key="label.departure"/></th>
                <th><fmt:message key="label.cost"/></th>

                    <%--<th><fmt:message key="label.user_name"/></th>--%>
                    <%--<th><fmt:message key="label.user_surname"/></th>--%>
                    <%--<th><fmt:message key="label.visits"/></th>--%>
                    <%--<th><fmt:message key="label.ban"/></th>--%>
                <th><fmt:message key="label.client"/></th>
                <th><fmt:message key="label.room_number"/></th>
                <th><fmt:message key="label.confirmed"/></th>
            </tr>
            <form name="roomForm" method="POST" action="controller">
                <c:forEach var="application" items="${applicationListAdmin}">
                    <tr>
                        <td><input type="radio" name="application_id" value="${application.id}"></td>
                        <td> ${application.placesAmount} </td>
                        <td> ${application.arrivalDate} </td>
                        <td> ${application.departureDate} </td>
                        <td> ${application.finalPrice}$</td>
                        <td> ${application.clientId} </td>
                        <td><c:if test="${application.room.id == 0}">
                            -
                        </c:if>
                            <c:if test="${application.room.id != 0}">
                                ${application.room.id}
                            </c:if>
                        </td>
                        <td> ${application.confirmed} </td>
                            <%--<td> ${client.name} </td>--%>
                            <%--<td> ${client.surname} </td>--%>
                            <%--<td> ${status.visits} </td>--%>
                            <%--<td> ${status.banned} </td>--%>


                    </tr>
                </c:forEach>
                <br>
                <c:forEach var="application" items="${applicationConfirmedListAdmin}">
                    <tr>
                        <td></td>
                        <td> ${application.placesAmount} </td>
                        <td> ${application.arrivalDate} </td>
                        <td> ${application.departureDate} </td>
                        <td> ${application.finalPrice}$</td>
                        <td> ${application.clientId} </td>
                        <td> ${application.room.id}</td>
                        <td> ${application.confirmed} </td>

                    </tr>
                </c:forEach>
                <c:forEach var="application" items="${bannedApplications}">
                    <tr bgcolor="#5f9ea0">
                        <td></td>
                        <td> ${application.placesAmount} </td>
                        <td> ${application.arrivalDate} </td>
                        <td> ${application.departureDate} </td>
                        <td> ${application.finalPrice}$</td>
                        <td> ${application.clientId} </td>
                        <td> ${application.room.id}</td>
                        <td> ${application.confirmed} </td>
                        <td><fmt:message key="label.freeze"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>
                            <%--<form name="cancelForm" method="POST" action="controller">--%>
                            <%--<input type="hidden" name="command" value="delete_application"/>--%>
                            <%--<fmt:message key="button.cancel" var="canсelButton"/>--%>
                            <%--<input class="button" type="submit" name="delete" value="${canсelButton}"/>--%>
                            <%--</form>--%>
                    </td>
                    <td>
                        <input type="hidden" name="command" value="retrieve_appropriate_rooms"/>
                        <fmt:message key="button.confirm" var="confirmButton"/>
                        <input class="button" type="submit" name="confirm" value="${confirmButton}"/>

                    </td>
            </form>
            <td><br>

                    <%--<form>--%>
                    <%--<input type="hidden" name="command" value="edit"/>--%>
                    <%--<fmt:message key="button.edit" var="editButton"/>--%>
                    <%--<input class="button" type="submit" name="edit" value="${editButton}"/>--%>
                    <%--</form>--%>
            </td>

            <td><br>

                <form>
                    <input type="hidden" name="command" value="retrieve_clients"/>
                    <fmt:message key="button.clients" var="clientsButton"/>
                    <input class="button" type="submit" name="edit" value="${clientsButton}"/>
                </form>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td>
                <br>

                <form name="historyForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="show_history"/>
                    <fmt:message key="button.history" var="historyButton"/>
                    <input class="button" type="submit" value="${historyButton}"/>
                </form>
            </td>
            </tr>


        </table>
    </c:if>
    <c:if test="${empty applicationListAdmin && empty applicationConfirmedListAdmin && empty bannedApplications}">
        <span class="please"><fmt:message key="label.no_applications"/></span>
        <br>
        <br>

        <%--<form name="orderForm" method="POST" action="controller">--%>
        <%--<input type="hidden" name="command" value="show_order_form"/>--%>
        <%--<input type="submit" name="order" value="Create new order"/>--%>
        <%--&lt;%&ndash;<fmt:message key="button.register"/>>&ndash;%&gt;--%>
        <%--</form>--%>
        <form name="historyForm" method="POST" action="controller">
            <input type="hidden" name="command" value="show_history"/>
            <fmt:message key="button.history" var="historyButton"/>
            <input class="button" type="submit" value="${historyButton}"/>
        </form>
    </c:if>
    <br><br><br><br>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
