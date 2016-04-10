package by.bsu.hostel.command.factory;

/**
 * Created by Kate on 08.02.2016.
 */

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    static Logger log = Logger.getLogger(ActionFactory.class);

    public static ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        log.debug("Action in factory: " + action);
        if (action == null || action.isEmpty()) {
            return current;
        }
        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
        current = currentEnum.getCurrentCommand();
        return current;
    }
}
