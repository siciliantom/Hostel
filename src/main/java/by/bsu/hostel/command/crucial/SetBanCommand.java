package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ClientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 10.04.2016.
 */
public class SetBanCommand implements ActionCommand {
    //    private static final String ROLE_ADMIN = "admin";
//    private static final String ROLE_USER = "user";
//    private static final String ROLE_ATTRIBUTE = "role";
    static Logger log = Logger.getLogger(SetBanCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("RetrieveClientsCommand!!");
        boolean isBanned = false;
        HttpSession session = request.getSession();
        ClientService clientService = null;
        String clientId = request.getParameter("client_id");
        if (clientId != null) {
            clientService = ClientService.getInstance();
            try {
                isBanned = clientService.setBan(clientId);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        if (isBanned) {
//            Client client = (Client) session.getAttribute("client");
//            client.getStatus().setBanned(ConfirmationEnum.YES);
//            session.setAttribute("client", client);
            List<Application> bannedApplications = null;
            List<Application> applicationListAdmin = (List<Application>) session.getAttribute("applicationListAdmin");//
            if (applicationListAdmin != null) {
                Iterator<Application> iterator = applicationListAdmin.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if (currentApplication.getClientId() == Long.parseLong(clientId)) {
                        bannedApplications.add(currentApplication);
                        iterator.remove();
                    }
                }
                session.setAttribute("applicationListAdmin", applicationListAdmin);
                session.setAttribute("bannedApplications", bannedApplications);
            }
        }
//        request.getSession().setAttribute("before_page", "path.page.clients");
        return ConfigurationManager.getProperty("path.page.main_admin");
    }
}
