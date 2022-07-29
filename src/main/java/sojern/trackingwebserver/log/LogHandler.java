package sojern.trackingwebserver.log;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.logging.*;

public class LogHandler {

    private static final String LOG_FILE_NAME = "c:/temp/log.log";

    /**
     * Log request information to log file
     * @param httpHeaders
     */
    public void logRequest(HttpHeaders httpHeaders, String className) {
        try {
            Logger LOGGER = Logger.getLogger(className);
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.log(Level.INFO, "Requested: "+ ((ContainerRequestContext) httpHeaders).getUriInfo().getRequestUri().toString());
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}