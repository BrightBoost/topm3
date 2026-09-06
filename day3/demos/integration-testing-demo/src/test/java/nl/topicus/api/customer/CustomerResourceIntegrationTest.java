package nl.topicus.api.customer;

import io.restassured.RestAssured;
import nl.topicus.api.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Integration tests: these hit the real, running API over HTTP against a real
 * (in-memory) database, instead of calling the service layer directly.
 *
 * Compare this with a unit test on CustomerService: a unit test can never tell
 * you whether routing, JSON serialization, status codes or headers are correct.
 */
class CustomerResourceIntegrationTest {

    private static HttpServer server;

    @BeforeAll
    static void startServer() throws Exception {
        // A unique database per test run keeps this test isolated and repeatable.
        String jdbcUrl = "jdbc:h2:mem:test-" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1";
        server = Main.startServer(jdbcUrl);
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @AfterAll
    static void stopServer() {
        server.shutdownNow();
    }

    @Test
    void getAllCustomers_returnsSeededCustomers() {
        given()
        .when()
            .get("/customers")
        .then()
            .statusCode(200)
            .body("size()", equalTo(2))
            .body("name", hasItems("Alice", "Bob"));
    }

    @Test
    void getCustomerById_returnsCustomer_whenFound() {
        given()
        .when()
            .get("/customers/42")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(42))
            .body("name", equalTo("Alice"))
            .body("email", equalTo("alice@example.com"));
    }

    @Test
    void getCustomerById_returns404WithConsistentErrorBody_whenNotFound() {
        given()
        .when()
            .get("/customers/999")
        .then()
            .statusCode(404)
            .body("status", equalTo(404))
            .body("error", equalTo("Not Found"));
    }

    @Test
    void createCustomer_returns201CreatedWithLocationHeader() {
        String requestBody = """
                {
                    "name": "Charlie",
                    "email": "charlie@example.com"
                }
                """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/customers")
        .then()
            .statusCode(201)
            .header("Location", containsString("/customers/"))
            .body("name", equalTo("Charlie"))
            .body("email", equalTo("charlie@example.com"));
    }

    @Test
    void createCustomer_returns400_whenRequiredFieldIsMissing() {
        String requestBody = """
                {
                    "email": "noname@example.com"
                }
                """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/customers")
        .then()
            .statusCode(400)
            .body("error", equalTo("Bad Request"));
    }
}
