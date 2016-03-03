package by.bsu.hostel.controller;

import by.bsu.hostel.command.ActionCommand;
import by.bsu.hostel.command.ActionFactory;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//
////package by.bsu.hostel.controller;
////
/////**
//// * Created by Kate on 05.02.2016.
//// */
@WebServlet("/controller")
public class MainController extends HttpServlet {
    static Logger log = Logger.getLogger(MainController.class);

    @Override
    public void init() throws ServletException {
        super.init();
        //try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
//        } catch (PoolException e) {
//            log.fatal("Can't create connection pool!");
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        log.debug("In GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        log.debug("Controller process.");
        //log.debug("Apache MD5: " + MD5Util.md5Apache(request.getParameter("password")));

        HttpSession session = request.getSession();
        String page = null;
        ActionCommand command = ActionFactory.defineCommand(request);
        log.debug("Controller process" + command);
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            log.error("Can't execute command!", e);
        }

        session.setAttribute("language", "en_US");
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
//// установка страницы c cообщением об ошибке
////                page = ConfigurationManager.getProperty("path.page.index");
//                request.getSession().setAttribute("nullPage",
//                        MessageManager.getProperty("message.nullpage"));
//                response.sendRedirect(request.getContextPath());// + page);
        } else {
            log.debug("No page to move!");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        log.debug("Pool is closed ");
        ConnectionPool.closePool();
    }
}
