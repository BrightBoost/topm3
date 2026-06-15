package nl.topicus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/api/";

    public static void main(String[] args) throws IOException {
        // Scan the nl.topicus.api package for @Path-annotated classes
        ResourceConfig config = new ResourceConfig().packages("nl.topicus.api");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);

        System.out.println("API server gestart op: " + BASE_URI);
        System.out.println("Probeer: GET " + BASE_URI + "examples");
        System.out.println("Druk op Enter om te stoppen.");
        System.in.read();

        server.shutdownNow();
    }
}
