package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 26.03.2016.
 */
public class RetrieveHistoryCommand implements ActionCommand {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    static Logger log = Logger.getLogger(RetrieveHistoryCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ApplicationService applicationService = ApplicationService.getInstance();
        List<Application> applicationListAdmin = null;
        List<Application> applicationListUser = null;
        Client client = (Client)session.getAttribute("client");
        String page = null;
            try {
                if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am user!");
                    applicationListUser = applicationService.applicationsHistoryById(client.getId());
//                    session.setAttribute("last_page", "path.page.orders_history");
                    request.getSession().setAttribute("before_page", "path.page.main_user");
                    page = ConfigurationManager.getProperty("path.page.orders_history");
                }
                if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am admin!");
                    applicationListAdmin = applicationService.applicationsHistory();
//                    session.setAttribute("last_page", "path.page.applications_history");
                    request.getSession().setAttribute("before_page", "path.page.main_admin");
                    page = ConfigurationManager.getProperty("path.page.applications_history");

                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (applicationListAdmin != null) {
                session.setAttribute("historyApplicationListAdmin", applicationListAdmin);
            }
            if (applicationListUser != null) {
                session.setAttribute("historyApplicationListUser", applicationListUser);
            }
        return page;
    }
}

