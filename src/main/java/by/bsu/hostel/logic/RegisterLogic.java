package by.bsu.hostel.logic;

import by.bsu.hostel.domain.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kate on 25.02.2016.
 */
public class RegisterLogic {
    private static final String REGEX_LETTERS = "[a-zA-ZА-Яа-я]{2,24}";
    private static final String REGEX_LOGIN = "[\\wА-Яа-я1-9]{3,16}";
    private static final String REGEX_PASSWORD = "[\\wА-Яа-я1-9]{4,12}";
    private static final String REGEX_ROLE = "(admin)||(user)";
//    private static String message = null;

//    public static String getMessage() {
//        return message;
//    }

    public static String checkRegistration(Client client) {
        String message = null;
        if(client == null){
            return "message.make_application";
        }
        boolean isValidReg = true;
        Pattern lettersPattern = Pattern.compile(REGEX_LETTERS);
        Pattern loginPattern = Pattern.compile(REGEX_LOGIN);
        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);
        Pattern rolePattern = Pattern.compile(REGEX_ROLE);

        Matcher loginMatcher = loginPattern.matcher(client.getAuthentication().getLogin());
        Matcher passwordMatcher = passwordPattern.matcher(client.getAuthentication().getPassword());
        Matcher nameMatcher = lettersPattern.matcher(client.getName());
        Matcher surnameMatcher = lettersPattern.matcher(client.getSurname());
        Matcher countryMatcher = lettersPattern.matcher(client.getCountry());
//        Matcher roleMatcher = rolePattern.matcher(client.getRole());

        if (!loginMatcher.matches() || !passwordMatcher.matches()) {
            message = "message.login_error";
        }
        if (!nameMatcher.matches()) {
            message = "message.wrong_name";
        }
        if (!surnameMatcher.matches()) {
            message = "message.wrong_surname";
        }
        if (!countryMatcher.matches()) {
            message = "message.wrong_country";
        }
//        if (!roleMatcher.matches()) {
//            message = "message.wrong_role";
//        }
//        if (message != null) {
//            isValidReg = false;
//        }
        return message;
    }
}
