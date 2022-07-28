package sojern.trackingwebserver.rest;

import sojern.trackingwebserver.log.LogHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@Path("/tracking-web-server")
public class TrackingWebServerAPI {

    private static final String OK_FILE = "/tmp/ok";
    private static final String TEMP_OK_FILE = System.getProperty("java.io.tmpdir")+"tmp/ok";
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
            return buildErrorResponse(Response.Status.SERVICE_UNAVAILABLE);
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
            return buildErrorResponse(Response.Status.NO_CONTENT);
        }

    }

    @GET
    @Path("/ping-temp-file")
    @Produces("text/plain")
    public Response pingTempFile() {
        File file = new File(TEMP_OK_FILE);
        if(file!=null && file.exists()) {
            return Response.status(Response.Status.OK).entity("OK").build();
        }
        else{
            return buildErrorResponse(Response.Status.SERVICE_UNAVAILABLE);
        }

    }

    @GET
    @Path("/create-temp-file")
    @Produces("text/plain")
    public Response createTempFile() throws IOException {
        File file = new File(TEMP_OK_FILE);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bufferedWriter.close();
        return Response.status(Response.Status.OK).entity("File Created").build();

    }

    @GET
    @Path("/delete-temp-file")
    @Produces("text/plain")
    public Response deleteTempFile() {
        File file = new File(TEMP_OK_FILE);
        if (file.exists()){
            file.delete();
        }
        return Response.status(Response.Status.OK).entity("File Deleted").build();

    }

    private File getFile(String filePath) {
        URL resource = getClass().getResource(filePath);
        try {
            return Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }

    private Response buildErrorResponse(Response.Status status){
        return Response.status(status).entity("Error handling request").build();
    }

}