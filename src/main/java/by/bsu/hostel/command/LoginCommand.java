package by.bsu.hostel.command;

import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ClientService;
import by.bsu.hostel.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 05.02.2016.
 */
public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private MessageManager messageManager = null;
    static Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if(request.getParameter("locale") == "en") {
            messageManager = MessageManager.EN;
        }
        else{
            messageManager = MessageManager.RU;
        }

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD));//here?
        Authentication authentication = new Authentication();
        authentication.setLogin(login);
        authentication.setPassword(password);


        log.debug("In loginCommand");

        ClientService clientService = ClientService.getInstance();
        //List<Client> list = clientService.findAll();
        Client client = null;
        try {
            client = clientService.logIn(authentication);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page = null;
        if (client != null) {
            log.debug("Client is set");
            request.setAttribute("client", client);

            request.getSession().setAttribute("role", ((Client) request.getAttribute("client")).getRole());
            request.getSession().setAttribute("name", ((Client)request.getAttribute("client")).getName());

            page = ConfigurationManager.getProperty("path.page.order");
        } else {
            log.debug("No such client");
            request.setAttribute("errorLoginPassMessage", messageManager.getMessage("message.login_error"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
