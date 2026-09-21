package nl.topicus.day4.securitytesting;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void noCredentials_shouldReturn401() {
        given()
                .when()
                .get("/api/profile")
                .then()
                .statusCode(401);
    }

    @Test
    void userWithoutAdminRole_shouldReturn403() {
        given()
                .auth().preemptive().basic("user", "password")
                .when()
                .get("/api/admin/reports")
                .then()
                .statusCode(403);
    }

    @Test
    void userRequestingOtherUsersOrder_shouldReturn403() {
        given()
                .auth().preemptive().basic("alice", "password")
                .when()
                .get("/api/orders/102")
                .then()
                .statusCode(403);
    }

    @Test
    void validUserOwnsOrder_shouldReturn200() {
        given()
                .auth().preemptive().basic("alice", "password")
                .when()
                .get("/api/orders/101")
                .then()
                .statusCode(200)
                .body("owner", equalTo("alice"))
                .body("description", equalTo("books"));
    }
}
