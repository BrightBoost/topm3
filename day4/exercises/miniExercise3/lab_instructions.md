# Lab: Diagnose and Fix CORS

## Scenario / Context

A frontend team has moved its development UI to `http://localhost:5500`, while the Java API still runs on `http://localhost:8080`. The API works in curl, but the browser blocks the frontend request. Diagnose the browser behavior and configure the smallest correct CORS policy.

## Learning Goals

- Explain what CORS protects in a browser.
- Distinguish CORS from authentication and authorization.
- Configure an explicit allowed origin and HTTP method.
- Debug preflight and response-header problems with browser tools.

## Prerequisites

- Basic HTTP headers and Java knowledge.
- Familiarity with browser developer tools.
- JDK 17 or newer and Maven.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Observe the failure

### What you will do

Start the Java API and static frontend. Inspect the browser console and network tab, then compare the browser request with the same request made using curl.

### Success criteria

- You can explain why curl succeeds while the browser blocks the request.
- You can identify the request origin and the missing CORS response information.
- You can tell whether the failure is a browser policy issue or an API authorization failure.

### Hints

<details>
<summary>Hint 1</summary>

Compare the frontend origin with the API origin. A different port is a different origin.

</details>

<details>
<summary>Hint 2</summary>

Inspect the response headers, not only the response body.

</details>

<details>
<summary>Hint 3</summary>

CORS is enforced by browsers. curl does not enforce the browser's same-origin policy.

</details>

## Part 2: Configure the smallest policy

### What you will do

Configure the API to allow the supplied frontend origin and required `GET` request. Test the browser flow again, then verify that an unrelated origin is not allowed.

### Success criteria

- The frontend can call the API from the intended origin.
- The response includes the correct `Access-Control-Allow-Origin` value.
- The policy does not use a wildcard unnecessarily.
- The API still requires its normal authentication and authorization checks.

### Hints

<details>
<summary>Hint 1</summary>

Allow the exact origin, including scheme and port.

</details>

<details>
<summary>Hint 2</summary>

If the browser sends an `OPTIONS` preflight, the API must answer it with the allowed method and headers.

</details>

<details>
<summary>Hint 3</summary>

CORS headers make a browser willing to expose a response; they do not grant permission to protected data.

</details>

# Bonus Challenge (Optional)

Add a protected `POST` request and configure the preflight for `Content-Type` and `Authorization` without allowing arbitrary origins.

# Reflection Questions

1. Why does curl succeed when the browser reports a CORS error?
2. What is the security trade-off between an exact origin and `*`?
3. Why should CORS never be treated as authentication?
4. Which network request was the hardest to understand: the actual request or the preflight?
5. How would the configuration change when the frontend is deployed to two known domains?
6. What monitoring would help detect unexpected origins or repeated preflight failures?
