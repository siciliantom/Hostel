package by.bsu.hostel.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 08.02.2016.
 */
    public interface ActionCommand {
        String execute(HttpServletRequest request);
}
