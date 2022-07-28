package sojern.trackingwebserver.rest;

import sojern.trackingwebserver.log.LogHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@Path("/tracking-web-server")
public class TrackingWebServerAPI {

    private static final String OK_FILE = "/tmp/ok";
    private static final String IMAGE_FILE = "/img/1x1.gif";
    private final LogHandler logger = new LogHandler();


    @GET
    @Path("/ping")
    @Produces("text/plain")
    public Response ping() {
        File okFile = getFile(OK_FILE);
        if(okFile!=null && okFile.exists()) {
            return Response.status(Response.Status.OK).entity("OK").build();
        }
        else{
            return buildErrorResponse();
        }

    }

    @GET
    @Path("/img")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response image(@Context HttpHeaders httpHeaders) {
        logger.logRequest(httpHeaders);
        File image = getFile(IMAGE_FILE);
        if(image!=null){
            return Response.ok(image, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + image.getName() + "\"" )
                    .build();
        }
        else{
            return buildErrorResponse();
        }

    }

    private File getFile(String filePath) {
        URL resource = getClass().getResource(filePath);
        try {
            return Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }

    private Response buildErrorResponse(){
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Error handling request").build();
    }

}