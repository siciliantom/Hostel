package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.OrderLogic;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate on 15.03.2016.
 */
public class BookCommand implements ActionCommand {
    private static final String PARAM_NAME_PLACES = "places";
    private static final String PARAM_NAME_PAY = "payment";//or book
    private static final String PARAM_NAME_ARRIVAL = "arrival";
    private static final String PARAM_NAME_DEPARTURE = "departure";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    private static final int CURRENT_PRICE = 25;
//    private static final int NOT_CONFIRMED = 0;
    private static final String PARAM_NAME_ROOM = "room_type";
    private MessageManager messageManager = null;
    static Logger log = Logger.getLogger(BookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (("en_US").equals(session.getAttribute("locale"))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }
        Client currentClient = (Client) session.getAttribute("client");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String page = null;
        Date arrivalDate;
        Date departureDate;

        log.debug("PAGE TAHAT "+ request.getAttribute("javax.servlet.forward.request_uri"));
        try {
            java.util.Date date = dateFormat.parse(request.getParameter(PARAM_NAME_ARRIVAL));
            arrivalDate = new Date(date.getTime());
            date = dateFormat.parse(request.getParameter(PARAM_NAME_DEPARTURE));
            departureDate = new Date(date.getTime());
        } catch (ParseException e) {
            throw new CommandException(e);
        }
        Application application = new Application();
        application.setArrivalDate(arrivalDate);
        application.setDepartureDate(departureDate);
        application.setPlacesAmount(Integer.parseInt(request.getParameter(PARAM_NAME_PLACES)));
        application.setClientId(currentClient.getId());
        application.setFinalPrice(application.getPlacesAmount() * CURRENT_PRICE);
        application.setConfirmed(ConfirmationEnum.NO);
        application.getRoom().setType(request.getParameter(PARAM_NAME_ROOM));
        log.debug("APPLICATION " + application.getArrivalDate() + " " +application.getDepartureDate());
        String message = OrderLogic.checkApplication(application);
        if(message != null){
            log.debug("not valid" + message);
            page = ConfigurationManager.getProperty(session.getAttribute(LAST_PAGE_ATTRIBUTE).toString());//
            request.setAttribute("errorApplication", messageManager.getMessage(message));
            return page;
        }
        ApplicationService applicationService = ApplicationService.getInstance();
        try {
            //
            if (applicationService.makeOrder(application)) {
                List<Application> currentApplicationList = (List<Application>) session.getAttribute("applicationListUser");
                log.debug("application added!!! " + application.toString());
                if (currentApplicationList == null) {
                    log.debug("null;;;;;;;;;;;;;;;;;");
                    currentApplicationList = new ArrayList<>();
                }
                currentApplicationList.add(application);
                session.setAttribute("applicationListUser", currentApplicationList);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
//            session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.main_user");
            page = ConfigurationManager.getProperty("path.page.main_user");
        }
        if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
//            session.setAttribute(LAST_PAGE_ATTRIBUTE, "path.page.main_admin");
            page = ConfigurationManager.getProperty("path.page.main_admin");
        }
        return page;
    }
}
