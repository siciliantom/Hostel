//package by.bsu.hostel.logic;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by Kate on 05.02.2016.
// */
//public class LoginLogic {
//    private static final String REGEX_PASSWORD = "[a-zA-Z]\\w{6,12}";
//    private static final String REGEX_LOGIN = "\\w{6,16}";
//
//    public static boolean checkLogin(String enterLogin, String enterPass) {
//        Pattern loginPattern = Pattern.compile(REGEX_LOGIN);
//        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);
//        Matcher loginMatcher = loginPattern.matcher(enterLogin);
//        Matcher passwordMatcher = passwordPattern.matcher(enterPass);
//            return (loginMatcher.matches() && passwordMatcher.matches());
//    }
//}
