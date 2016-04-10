package by.bsu.hostel.logic;

import by.bsu.hostel.domain.Application;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kate on 27.03.2016.
 */
public class OrderLogic {
    private static final String REGEX_NUMBERS = "[1-8]{1}";
//    private static String message = null;
    static Logger log = Logger.getLogger(OrderLogic.class);

//    public static String getMessage() {
//        return message;
//    }

    public static String checkApplication(Application application) {
//        boolean isValidReg = true;
        String message = null;
        Pattern numbersPattern = Pattern.compile(REGEX_NUMBERS);
        if (application != null) {
            log.debug("Logic!!!!!! " +application.getDepartureDate().before(application.getArrivalDate()));
            Matcher placesMatcher = numbersPattern.matcher(Integer.toString(application.getPlacesAmount()));
            if (!placesMatcher.matches()) {
                message = "message.places_error";
            }
            if (application.getDepartureDate().before(application.getArrivalDate())) {
                log.debug("i am here is before! ");
                message = "message.date_error";
            }
        } else {
            message = "message.make_application";
        }
//        if (message != null) {
//            isValidReg = false;
//        }
        return message;
    }
}
