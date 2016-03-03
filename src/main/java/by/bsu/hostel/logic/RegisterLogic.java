package by.bsu.hostel.logic;

import by.bsu.hostel.domain.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kate on 25.02.2016.
 */
public class RegisterLogic {//
    private static final String REGEX_LETTERS = "\\w{2,24}";
    private static final String REGEX_LOGIN = "\\w{6,16}";
    private static final String REGEX_PASSWORD = "[a-zA-Z]\\w{6,12}";
    private static final String REGEX_ROLE = "(admin)||(user)";
    private static String message = null;

    public static boolean checkRegistration(Client client) {
        boolean isValidReg = true;
        Pattern lettersPattern = Pattern.compile(REGEX_LETTERS);
        Pattern loginPattern = Pattern.compile(REGEX_LOGIN);
        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);
        Pattern rolePattern = Pattern.compile(REGEX_ROLE);

        Matcher loginMatcher = loginPattern.matcher(client.getLogin());
        Matcher passwordMatcher = passwordPattern.matcher(client.getPassword());
        Matcher nameMatcher = lettersPattern.matcher(client.getName());
        Matcher surnameMatcher = lettersPattern.matcher(client.getSurname());
        Matcher countryMatcher = lettersPattern.matcher(client.getCountry());
        Matcher roleMatcher = rolePattern.matcher(client.getRole());

        if (!loginMatcher.matches() || !passwordMatcher.matches()) {
            message = "Wrong login or password!";
        }
        if (!nameMatcher.matches()) {
            message = "Invalid name!";
        }
        if (!surnameMatcher.matches()) {
            message = "Invalid surname!";
        }
        if (!countryMatcher.matches()) {
            message = "Invalid country!";
        }
        if (!roleMatcher.matches()) {
            message = "Invalid role!";
        }
        if (message != null) {
            isValidReg = false;
        }
        return isValidReg;
    }
}
