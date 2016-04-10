package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.ClientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 07.04.2016.
 */
public class ConfirmCommand implements ActionCommand {
    static Logger log = Logger.getLogger(ConfirmCommand.class);
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
//        log.debug("Rows ! " + request.getParameterValues("rowid").length);
        List<Application> applicationListAdmin;
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        ApplicationService applicationService = ApplicationService.getInstance();
        ClientService clientService = null;
        boolean isdDeleted = false;
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        boolean confirmed = false;
        String roomId = request.getParameter("room_id");
        if (roomId != null) {
            clientService = ClientService.getInstance();
            try {
                if (!clientService.checkBan(roomId)) {
                    Application application = new Application();
                    application.setId(Long.parseLong((String) session.getAttribute("applicationId")));
                    application.getRoom().setId(Long.parseLong(roomId));
                    confirmed = applicationService.confirmApplication(application);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        if (confirmed) {
            try {
                applicationListAdmin = applicationService.currentApplications();
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (applicationListAdmin != null) {//
                Iterator<Application> iterator = applicationListAdmin.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if(currentApplication.getConfirmed().equals(ConfirmationEnum.YES)){
                        applicationConfirmedListAdmin.add(currentApplication);
                        iterator.remove();
                    }

                }
                session.setAttribute("applicationListAdmin", applicationListAdmin);
                session.setAttribute("applicationConfirmedListAdmin", applicationConfirmedListAdmin);
            }
        }
//        session.setAttribute("before_page", "path.page.appropriate_rooms");
        return ConfigurationManager.getProperty("path.page.main_admin");
//                ConfigurationManager.getProperty(request.getSession().getAttribute("last_page").toString());
    }
}

