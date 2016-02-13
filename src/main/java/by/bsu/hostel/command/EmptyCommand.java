package by.bsu.hostel.command;

/**
 * Created by Kate on 05.02.2016.
 */

import by.bsu.hostel.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
    public class EmptyCommand implements ActionCommand {
        @Override
        public String execute(HttpServletRequest request) {
/* в случае ошибки или прямого обращения к контроллеру
* переадресация на страницу ввода логина */
            String page = ConfigurationManager.getProperty("path.page.login");
            return page;
        }
    }
