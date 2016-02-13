package by.bsu.hostel.logic;

/**
 * Created by Kate on 05.02.2016.
 */
    public class LoginLogic {
        private final static String ADMIN_LOGIN = "admin";
        private final static String ADMIN_PASS = "Qwe123";
        public static boolean checkLogin(String enterLogin, String enterPass) {
            return ADMIN_LOGIN.equals(enterLogin) && ADMIN_PASS.equals(enterPass);
        }
    }
