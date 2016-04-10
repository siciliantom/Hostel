package by.bsu.hostel.controller;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.command.factory.ActionFactory;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
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
    private final String START_PAGE = "path.page.login";
    private final int INACTIVE_TIME = 800;
    static Logger log = Logger.getLogger(MainController.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
        HttpSession session = request.getSession();
        String page = null;
        //session.setAttribute("language", "en_US");
        ActionCommand command = ActionFactory.defineCommand(request);
        log.debug("Controller process" + command);
        if (session.getAttribute("last_page") == null) {//
            session.setAttribute("last_page", ConfigurationManager.getProperty(START_PAGE));//
        }
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            log.error("Can't execute command!", e);
        }
//        log.debug("deb "+request.getRemoteUser() + " "+ request.getQueryString() +" "+request.getMethod() +"contextPath "+request.getContextPath());
        log.debug("page " + page);
        if (page != null) {
//            String referer = request.getHeader("Referer");
//            log.debug("pageKKKKKKKK " +session.getAttribute("last_page"));
            if(request.getSession(false) != null) {//
                session.setAttribute("last_page", page);
            }
            log.debug("request dispatch!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
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
