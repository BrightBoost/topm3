# Lab: Authentication and Authorization Debugging

## Scenario / Context

A small internal API has just been connected to a frontend. The team reports that some unauthenticated requests return `500`, while authenticated users receive inconsistent responses when they open admin pages. The API owner needs a clear distinction between identity failures and permission failures before the service is exposed to more clients.

## Learning Goals

- Classify authentication and authorization failures in HTTP requests.
- Implement consistent `401` and `403` responses.
- Debug a Java API using request and response evidence.
- Evaluate why frontend-only permission checks are insufficient.

## Prerequisites

- Basic Java and HTTP knowledge.
- Familiarity with `401`, `403`, and `500` status codes.
- JDK 17 or newer and Maven.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Classify the failures

### What you will do

Run the starter service and use the supplied requests to classify each failure as authentication or authorization. Record the expected status code and compare it with the actual response.

### Success criteria

- You can explain the difference between `401` and `403`.
- Each supplied scenario has an expected status code.
- You can identify where a `500` hides a client-visible security error.

### Hints

<details>
<summary>Hint 1</summary>

Ask whether the server knows who the caller is before asking what that caller may do.

</details>

<details>
<summary>Hint 2</summary>

Inspect `SecurityApi.java` and compare the request headers with the response status.

</details>

<details>
<summary>Hint 3</summary>

Missing or invalid Basic Auth credentials should not become an unhandled exception.

</details>

## Part 2: Repair the API responses

### What you will do

Fix the starter service so that missing or invalid credentials return `401`, authenticated users without the required role return `403`, and valid requests continue to work. Add or update tests for all three outcomes.

### Success criteria

- The endpoints are returning the correct HTTP responses for different scenarios.
- Maven tests pass.

### Hints

<details>
<summary>Hint 1</summary>

Keep authentication and authorization as separate decisions in the request flow.

</details>

<details>
<summary>Hint 2</summary>

The frontend may hide the admin link, but test the endpoint directly with curl.

</details>

<details>
<summary>Hint 3</summary>

Decode the Basic Auth header, find the user, then check the required role.

</details>

<details>
<summary>Hint 4</summary>

A useful flow is: missing header -> `401`; unknown credentials -> `401`; known non-admin -> `403`; admin -> success.

</details>

# Bonus Challenge (Optional)

Add a `WWW-Authenticate: Basic realm="day4"` header to `401` responses and make sure it is absent from successful responses.

# Reflection Questions

1. Which request was hardest to classify as authentication or authorization, and what evidence resolved it?
2. Why is returning `500` for missing credentials harmful to API clients and monitoring?
3. What could go wrong if the frontend hides the admin link but the endpoint has no backend role check?
4. How would this flow change if users were authenticated with bearer tokens instead of Basic Auth?
5. What logging would help investigate repeated `401` responses without logging passwords?
6. How would you adapt the design if authentication were delegated to an identity provider?
