package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.*;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.RegisterLogic;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.AuthenticationService;
import by.bsu.hostel.service.ClientService;
import by.bsu.hostel.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 18.02.2016.
 */
public class RegisterCommand implements ActionCommand {
    private static final String PARAM_NAME_FIRSTNAME = "name";
    private static final String PARAM_NAME_LASTNAME = "surname";
    private static final String PARAM_NAME_COUNTRY = "country";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "client_role";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    private MessageManager messageManager = null;
    static Logger log = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("In RegisterCommand");
        HttpSession session = request.getSession();
        if (("en_US").equals(request.getSession().getAttribute("locale"))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        Authentication authentication = new Authentication();
        authentication.setLogin(login);
        authentication.setPassword(password);
        List<Application> bannedApplications = new ArrayList<>();
        ClientService clientService = ClientService.getInstance();
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        ApplicationService applicationService = ApplicationService.getInstance();
        List<Application> applicationListAdmin = null;

        List<Application> applicationListUser = null;
        String clientExistMessage;
        try {
            clientExistMessage = authenticationService.checkExistence(authentication);
            log.debug("MESSAGE" + clientExistMessage);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page = null;
        Client client;
        if (clientExistMessage != null) {
            log.debug("Such client already exists!");
//            session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.register");
            request.setAttribute("warningClientExists", messageManager.getMessage(clientExistMessage));
            page = ConfigurationManager.getProperty("path.page.register");
        } else {
            client = new Client();
            client.setName(request.getParameter(PARAM_NAME_FIRSTNAME));
            client.setSurname(request.getParameter(PARAM_NAME_LASTNAME));
            client.setCountry(request.getParameter(PARAM_NAME_COUNTRY));
            client.getAuthentication().setLogin(login);
            client.getAuthentication().setPassword(password);
            client.getAuthentication().setRole(RoleEnum.valueOf(request.getParameter(PARAM_NAME_ROLE).toUpperCase()));

            log.debug(client.getAuthentication().getRole() + " Validation");
            String message = RegisterLogic.checkRegistration(client);
            if (message == null) {//2ой раз не работает
                client.getAuthentication().setPassword(MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD)));
                try {
                    clientService.register(client);
                    session.setAttribute("client", client);
                    log.debug("no " + session.getAttribute("role"));
                    session.setAttribute(ROLE_ATTRIBUTE, client.getAuthentication().getRole().name().toLowerCase());
                    session.setAttribute("name", client.getName());
                    //request.setAttribute("wrongRegistration", messageManager.getMessage("message.wrong_reg"));

                    if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {//request.getSession().getAttribute("role")
                        log.debug("in user: " + session.getAttribute("role"));
//                        applicationListUser = applicationService.currentApplicationsById(client.getId());
//                        session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.main_user");
                        page = ConfigurationManager.getProperty("path.page.main_user");
                    }
                    if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                        log.debug("in admin: " + session.getAttribute("role"));
                        applicationListAdmin = applicationService.currentApplications();
//                        session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.main_admin");
                        page = ConfigurationManager.getProperty("path.page.main_admin");
                    }
                } catch (ServiceException e) {
                    log.debug("cant add to database");
//                    session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.register");
                    page = ConfigurationManager.getProperty("path.page.register");
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
                        session.setAttribute("applicationListAdmin", applicationListAdmin);
                        session.setAttribute("bannedApplications", bannedApplications);
                        session.setAttribute("applicationConfirmedListAdmin", applicationConfirmedListAdmin);
                    }
                }
                if (applicationListUser != null) {
                    session.setAttribute("applicationListUser", applicationListUser);
                }
            } else {
                log.debug("Wrong registration input " + message);
                request.setAttribute("wrongRegistration", messageManager.getMessage(message));//берем из переменных локалей
//                request.getSession().setAttribute("before_page", "path.page.login");
                page = ConfigurationManager.getProperty("path.page.register");
            }
        }
        return page;
    }
}
