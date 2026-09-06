# Integration Testing Demo

Demo for day 3, section 3 ("API Testing Part 3 - Integration Tests").

A small customer API (Jersey / Jakarta REST + Grizzly + H2) with one
integration test class written in JUnit 5 + REST Assured, that hits the
API over real HTTP against a real (in-memory) database.

## Stack

- Jersey (JAX-RS reference implementation) + embedded Grizzly HTTP server
- Jackson for JSON (de)serialization
- H2 in-memory database via plain JDBC
- JUnit 5 + REST Assured for the integration tests

This mirrors the stack used in the day 1 `api-starter` project, so nothing
here should look unfamiliar to the group.

## Running the API manually

```bash
mvn exec:java
```

Then, in another terminal:

```bash
curl http://localhost:8080/api/customers
curl http://localhost:8080/api/customers/42
curl http://localhost:8080/api/customers/999      # 404 with a consistent error body
curl -X POST http://localhost:8080/api/customers \
     -H "Content-Type: application/json" \
     -d '{"name": "Charlie", "email": "charlie@example.com"}'
```

## Running the integration tests

```bash
mvn test
```

`CustomerResourceIntegrationTest` starts the real Grizzly server against a
fresh, uniquely-named in-memory H2 database in `@BeforeAll`, then uses REST
Assured to:

- assert the seeded customers come back from `GET /customers`
- assert a single customer's JSON body and content type from `GET /customers/{id}`
- assert the consistent error shape and `404` status for an unknown customer
- assert `201 Created` + a `Location` header on `POST /customers`
- assert `400 Bad Request` when required fields are missing

This proves routing, serialization, status codes and headers
all work together.
