package by.bsu.hostel.controller;

import by.bsu.hostel.command.ActionCommand;
import by.bsu.hostel.command.ActionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//
////package by.bsu.hostel.controller;
////
/////**
//// * Created by Kate on 05.02.2016.
//// */
@WebServlet("/controller")
public class MainController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
//        String parseType = request.getParameter("command");
//
//        switch (parseType) {
//            case "change":
//                //request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
//                //request.setAttribute("lang", "ru_RU");
//                break;
//        }

            String page = null;
// определение команды, пришедшей из JSP
            ActionFactory client = new ActionFactory();
            ActionCommand command = client.defineCommand(request);
/*
* вызов реализованного метода execute() и передача параметров
* классу-обработчику конкретной команды
*/
        //тут сервис использовать и из реквеста
            page = command.execute(request);
// метод возвращает страницу ответа
// page = null; // поэксперементировать!
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                System.out.println("Page " + page);
// вызов страницы ответа на запрос
                dispatcher.forward(request, response);
                //request.getRequestDispatcher("/jsp/parsingResult.jsp").forward(request, response);
//            } else {
//// установка страницы c cообщением об ошибке
////                page = ConfigurationManager.getProperty("path.page.index");
//                request.getSession().setAttribute("nullPage",
//                        MessageManager.getProperty("message.nullpage"));
//                response.sendRedirect(request.getContextPath());// + page);
            }
    }
}
