package by.bsu.hostel.command.show;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 03.03.2016.
 */
public class ShowRegistrationFormCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
//        request.getSession().setAttribute("last_page", "path.page.register");
        request.getSession().setAttribute("before_page", "path.page.login");
        return ConfigurationManager.getProperty("path.page.register");
    }
}
