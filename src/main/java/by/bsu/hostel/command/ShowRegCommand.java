package by.bsu.hostel.command;

import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 03.03.2016.
 */
public class ShowRegCommand implements ActionCommand {//
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return ConfigurationManager.getProperty("path.page.register");
    }
}
