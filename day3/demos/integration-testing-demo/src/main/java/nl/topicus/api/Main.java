package nl.topicus.api;

import nl.topicus.api.customer.CustomerRepository;
import nl.topicus.api.customer.CustomerResource;
import nl.topicus.api.customer.CustomerService;
import nl.topicus.api.customer.H2CustomerRepository;
import nl.topicus.api.error.NotFoundExceptionMapper;
import nl.topicus.api.error.ValidationExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static final String JDBC_URL = "jdbc:h2:mem:customerdb;DB_CLOSE_DELAY=-1";
    public static final URI BASE_URI = URI.create("http://localhost:8080/api/");

    public static HttpServer startServer(String jdbcUrl) throws SQLException {
        initDatabase(jdbcUrl);

        CustomerRepository repository = new H2CustomerRepository(jdbcUrl);
        CustomerService service = new CustomerService(repository);

        ResourceConfig config = new ResourceConfig()
                .register(new CustomerResource(service))
                .register(NotFoundExceptionMapper.class)
                .register(ValidationExceptionMapper.class);

        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
    }

    private static void initDatabase(String jdbcUrl) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS customers");
            statement.execute("""
                    CREATE TABLE customers (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL
                    )
                    """);
            // Seeded with the same customer used in the slides' Rest Assured example.
            statement.execute("""
                    INSERT INTO customers (id, name, email) VALUES
                        (42, 'Alice', 'alice@example.com'),
                        (43, 'Bob', 'bob@example.com')
                    """);
        }
    }

    public static void main(String[] args) throws Exception {
        startServer(JDBC_URL);
        System.out.println("API running at " + BASE_URI);
        Thread.currentThread().join();
    }
}
