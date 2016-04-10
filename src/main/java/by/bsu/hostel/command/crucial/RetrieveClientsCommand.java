package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ClientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 10.04.2016.
 */
public class RetrieveClientsCommand implements ActionCommand {
//    private static final String ROLE_ADMIN = "admin";
//    private static final String ROLE_USER = "user";
//    private static final String ROLE_ATTRIBUTE = "role";
    static Logger log = Logger.getLogger(RetrieveClientsCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("RetrieveClientsCommand!!");
        HttpSession session = request.getSession();
        ClientService clientService = ClientService.getInstance();
        List<Client> clientsList = null;
            try {
                clientsList = clientService.findAll();
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        if (clientsList != null) {
            session.setAttribute("clientsList", clientsList);
        }
        request.getSession().setAttribute("before_page", "path.page.main_admin");
        return ConfigurationManager.getProperty("path.page.clients");
    }
}
