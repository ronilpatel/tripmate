package grp16.tripmate.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLoggerAdapter implements ILogger {
    Logger logger;

    private MyLoggerAdapter() {
    }

    public MyLoggerAdapter(Object classObj) {
        logger = LogManager.getLogger(classObj);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }
}