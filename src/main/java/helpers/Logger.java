package helpers;

import java.util.Calendar;
import java.util.logging.Level;

/**
 * Created by manisha.v on 30/01/19.
 */
public class Logger {


    public static void info(String tag, String content) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tag);
        logger.setLevel(Level.ALL);
        logger.log(Level.INFO,String.format("[%s] %s - %s", Calendar.getInstance().getTime().toString(), tag, content));
    }

    public static void error(String tag, String content) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tag);
        logger.setLevel(Level.ALL);
        logger.log(Level.SEVERE, String.format("[%s] %s ERROR - %s", Calendar.getInstance().getTime().toString(), tag, content));
    }


}
