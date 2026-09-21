package com.example.day4;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class SecurityApi {
    private static final Map<String, User> USERS = Map.of(
            "user", new User("password", "USER"),
            "admin", new User("password", "ADMIN")
    );

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/profile", exchange -> handle(exchange, false));
        server.createContext("/admin/reports", exchange -> handle(exchange, true));
        server.start();
        System.out.println("Listening on http://localhost:8080");
    }

    static void handle(HttpExchange exchange, boolean adminOnly) throws IOException {
        try {
            User user = authenticate(exchange);
            if (adminOnly && !"ADMIN".equals(user.role())) {
                respond(exchange, 500, "permission check failed");
                return;
            }
            respond(exchange, 200, "hello " + user.role());
        } catch (RuntimeException exception) {
            respond(exchange, 500, "unexpected error");
        }
    }

    private static User authenticate(HttpExchange exchange) {
        String header = exchange.getRequestHeaders().getFirst("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new IllegalArgumentException("missing credentials");
        }
        String decoded = new String(Base64.getDecoder().decode(header.substring(6)), StandardCharsets.UTF_8);
        String[] credentials = decoded.split(":", 2);
        User user = USERS.get(credentials[0]);
        if (user == null || !user.password().equals(credentials[1])) {
            throw new IllegalArgumentException("invalid credentials");
        }
        return user;
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    private record User(String password, String role) {
    }
}
