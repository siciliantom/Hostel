package by.bsu.hostel.command.show;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Kate on 09.03.2016.
 */
public class ShowBookingFormCommand implements ActionCommand {
    private static final int DAY_IN_MS = 24*60*60*1000;
    private MessageManager messageManager = null;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (("en_US").equals(session.getAttribute("locale"))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }
        Date date = new Date();
        session.setAttribute("today", new java.sql.Date(date.getTime()));
        session.setAttribute("tomorrow", new java.sql.Date(date.getTime()+ DAY_IN_MS));
        Client client = (Client)session.getAttribute("client");
        if(client.getStatus().getBanned().equals(ConfirmationEnum.YES)){
            request.setAttribute("accessPermissionError", messageManager.getMessage("message.ban_error"));
            return ConfigurationManager.getProperty("path.page.main_user");
        }
        session.setAttribute("before_page", "path.page.main_user");
//        session.setAttribute("last_page", "path.page.order");
        return ConfigurationManager.getProperty("path.page.order");
    }
}
