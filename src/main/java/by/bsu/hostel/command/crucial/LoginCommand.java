package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.ClientService;
import by.bsu.hostel.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 */
public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    private static final String PREVIOUS_PAGE_ATTRIBUTE = "previous_page";
    private static final String ROLE_ATTRIBUTE = "role";
    private MessageManager messageManager = null;
    static Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (("en_US").equals(session.getAttribute("locale"))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD));//here?
        Authentication authentication = new Authentication();
        authentication.setLogin(login);
        authentication.setPassword(password);
        log.debug("In loginCommand");
        ClientService clientService = ClientService.getInstance();
        ApplicationService applicationService = ApplicationService.getInstance();
        List<Application> applicationListAdmin = null;
        List<Application> bannedApplications = new ArrayList<>();
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        List<Application> applicationListUser = null;
        Client client = null;
        try {
            log.debug("CLIENT BEFORE");
            client = clientService.logIn(authentication);
            log.debug("CLIENT " + client + authentication);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page = null;
//        log.debug("PAGE TAHAT "+ request.getAttribute("javax.servlet.forward.request_uri"));
        if (client != null) {
            log.debug("Client is set");
            session.setAttribute(ROLE_ATTRIBUTE, client.getAuthentication().getRole().name().toLowerCase());
            try {
                if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am user!");
                    applicationListUser = applicationService.currentApplicationsById(client.getId());
//                    session.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, "path.page.index");
                    session.setAttribute("before_page", "path.page.login");
                    page = ConfigurationManager.getProperty("path.page.main_user");
                }
                if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am admin!");
                    applicationListAdmin = applicationService.currentApplications();
                    session.setAttribute("before_page", "path.page.login");
                    page = ConfigurationManager.getProperty("path.page.main_admin");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (applicationListAdmin != null) {//
                Iterator<Application> iterator = applicationListAdmin.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if (currentApplication.getConfirmed().equals(ConfirmationEnum.YES)) {
                        applicationConfirmedListAdmin.add(currentApplication);
                        iterator.remove();
                    } else {
                        try {
                            boolean banned = clientService.checkBan(String.valueOf(currentApplication.getClientId()));
                            log.debug("isbanned "+banned + currentApplication.getClientId());
                            if (banned) {
                                bannedApplications.add(currentApplication);
                                iterator.remove();
                            }
                        } catch (ServiceException e) {
                            throw new CommandException(e);
                        }
                    }
                }
                session.setAttribute("applicationListAdmin", applicationListAdmin);
                session.setAttribute("bannedApplications", bannedApplications);
                session.setAttribute("applicationConfirmedListAdmin", applicationConfirmedListAdmin);
            }
            if (applicationListUser != null) {
                session.setAttribute("applicationListUser", applicationListUser);
            }
            session.setAttribute("client", client);
            session.setAttribute("name", client.getName());
        } else {
            log.debug("No such client");
            request.setAttribute("errorLoginPassMessage", messageManager.getMessage("message.login_error"));
//            session.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, "path.page.index");
//            session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.login");
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
