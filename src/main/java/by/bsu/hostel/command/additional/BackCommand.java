package by.bsu.hostel.command.additional;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kate on 29.03.2016.
 */
public class BackCommand implements ActionCommand {
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    static Logger log = Logger.getLogger(BackCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
//        log.debug("BACk" + request.getSession().getAttribute("locale"));
        HttpSession session = request.getSession();
        session.setAttribute("locale", session.getAttribute("locale"));
//        if (("en_US").equals(session.getAttribute("locale"))) {
//            session.setAttribute("selectRu", " ");
//            session.setAttribute("selectEn", "selected");
//        } else {
//            session.setAttribute("selectRu", "selected");
//            session.setAttribute("selectEn", " ");
//        }
//        return null;
//        session.setAttribute("locale", request.getParameter("locale"));//
        return ConfigurationManager.getProperty(session.getAttribute("before_page").toString());
    }
}
