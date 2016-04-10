package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kate on 21.03.2016.
 */
public class DeleteApplicationCommand implements ActionCommand {
    static Logger log = Logger.getLogger(DeleteApplicationCommand.class);
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
//        log.debug("Rows ! " + request.getParameterValues("rowid").length);
        List<Application> applicationListAdmin = null;
        List<Application> applicationListUser = null;
        ApplicationService applicationService = null;
        boolean isdDeleted = false;
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if(request.getParameterValues("rowid") != null) {
            List<String> rowIds = new ArrayList(Arrays.asList(request.getParameterValues("rowid")));
            applicationService = ApplicationService.getInstance();
            try {
                isdDeleted = applicationService.deleteById(rowIds);
                log.debug("IsDeleted ! " + isdDeleted);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        if (isdDeleted) {//this update table
            try {
                if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am user!");
                    applicationListUser = applicationService.currentApplicationsById(client.getId());
                }
                if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    log.debug("I am admin!");
                    applicationListAdmin = applicationService.currentApplications();
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (applicationListAdmin != null) {
                session.setAttribute("applicationListAdmin", applicationListAdmin);
            }
            if (applicationListUser != null) {
                session.setAttribute("applicationListUser", applicationListUser);
            }
        }
        log.debug("last delete page" +session.getAttribute("last_page").toString());
        return session.getAttribute("last_page").toString();
//                ConfigurationManager.getProperty(request.getSession().getAttribute("last_page").toString());
    }
}
