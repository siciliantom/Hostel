package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Room;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.RoomService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 07.04.2016.
 */
public class RetrieveAppropriateRoomsCommand implements ActionCommand {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    static Logger log = Logger.getLogger(RetrieveAppropriateRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("RetrieveAppropriateRoomsCommand!!");
        HttpSession session = request.getSession();
        RoomService roomService = RoomService.getInstance();
        List<Room> appropriateRoomList = null;
       // Client client = (Client) session.getAttribute("client");
//        String page = null;
        String applicationId = request.getParameter("application_id");
        if (applicationId != null) {
            log.debug("INSIDER");
            session.setAttribute("applicationId", applicationId);
            roomService = RoomService.getInstance();
//            roomService = ClientService.getInstance();
            try {
                appropriateRoomList = roomService.getAppropriateRooms(applicationId);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (appropriateRoomList != null) {
                session.setAttribute("appropriateRoomList", appropriateRoomList);
            }
            request.getSession().setAttribute("before_page", "path.page.main_admin");
            return ConfigurationManager.getProperty("path.page.appropriate_rooms");

        }
        return ConfigurationManager.getProperty("path.page.main_admin");
    }
}