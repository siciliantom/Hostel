package by.bsu.hostel.command;

import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.RegisterLogic;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ClientService;
import by.bsu.hostel.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 18.02.2016.
 */
public class RegisterCommand implements ActionCommand {
    private static final String PARAM_NAME_FIRSTNAME = "name";
    private static final String PARAM_NAME_LASTNAME = "surname";
    private static final String PARAM_NAME_COUNTRY = "country";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "role";
    private MessageManager messageManager = null;
    static Logger log = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("In RegisterCommand");
        if(request.getParameter("locale") == "en") {
            messageManager = MessageManager.EN;
        }
        else{
            messageManager = MessageManager.RU;
        }
        String password = MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD));
        String login = request.getParameter(PARAM_NAME_LOGIN);
        Client client = new Client();
        client.setName(request.getParameter(PARAM_NAME_FIRSTNAME));
        client.setSurname(request.getParameter(PARAM_NAME_LASTNAME));
        client.setCountry(request.getParameter(PARAM_NAME_COUNTRY));
        client.setLogin(login);
        client.setPassword(password);
        client.setRole(request.getParameter(PARAM_NAME_ROLE));

        Authentication authentication = new Authentication();
        authentication.setLogin(login);
        authentication.setPassword(password);

        ClientService clientService = ClientService.getInstance();
        try {
            client = clientService.logIn(authentication);//if client exists
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page = null;
        if (client != null) {
            log.debug("Such client already exists!");
            request.setAttribute("warningClientExists", messageManager.getMessage("message.client_exists"));
            page = ConfigurationManager.getProperty("path.page.order");
        } else {
            log.debug("Validation");
            if (RegisterLogic.checkRegistration(client)) {
                try{
                    if(clientService.register(client)){
                        request.setAttribute("client", client);
                        request.getSession().setAttribute("role", ((Client) request.getAttribute("client")).getRole());
                        request.getSession().setAttribute("name", ((Client)request.getAttribute("client")).getName());
                        //request.setAttribute("wrongRegistration", messageManager.getMessage("message.wrong_reg"));
                        page = ConfigurationManager.getProperty("path.page.main");
                    }
                    else{
                        log.debug("cant add to database");
                        page = ConfigurationManager.getProperty("path.page.register");
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }
            else{
                log.debug("Wrong registration input");
                request.setAttribute("wrongRegistration", messageManager.getMessage("message.wrong_reg"));//logic.getmessage
                page = ConfigurationManager.getProperty("path.page.register");
            }
        }
        return page;
    }
}
