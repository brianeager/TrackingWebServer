package sojern.trackingwebserver.log;

import sojern.trackingwebserver.rest.TrackingWebServerAPI;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.logging.*;

public class LogHandler {

    private static final String LOG_FILE_NAME = "c:/temp/log.log";
    private static final Logger LOGGER = Logger.getLogger(TrackingWebServerAPI.class.getName());

    public void logRequest(HttpHeaders httpHeaders) {
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.log(Level.INFO, "Image requested from: "+ ((ContainerRequestContext) httpHeaders).getUriInfo().getRequestUri().toString());
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}