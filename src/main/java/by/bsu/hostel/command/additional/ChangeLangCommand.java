package by.bsu.hostel.command.additional;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kate on 07.03.2016.
 */
public class ChangeLangCommand implements ActionCommand {
    static Logger log = Logger.getLogger(ChangeLangCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        log.debug("lang " + request.getParameter("locale") + " "+ request.getSession().getAttribute("last_page").toString());
        HttpSession session = request.getSession();
        session.setAttribute("locale", request.getParameter("locale"));
        if (("en_US").equals(session.getAttribute("locale"))) {
            session.setAttribute("selectRu", " ");
            session.setAttribute("selectEn", "selected");
            log.debug("lll " + request.getAttribute("selectEn") + request.getAttribute("selectRu"));
        } else {
            log.debug("lllses " + (session.getAttribute("locale")));
            session.setAttribute("selectRu", "selected");
            session.setAttribute("selectEn", " ");
        }
        return session.getAttribute("last_page").toString();
//        ConfigurationManager.getProperty(session.getAttribute("last_page").toString());
    }
}
