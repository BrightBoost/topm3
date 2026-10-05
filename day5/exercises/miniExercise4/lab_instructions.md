# Lab: Harden Bearer-Token Error Responses

## Scenario / Context

An API security review found that malformed or forged bearer tokens can produce diagnostic details in an authentication response. Those details help legitimate developers debug, but can also reveal implementation internals to callers. The API team needs a stable public error contract and tests proving that internal verifier messages are not exposed.

This is a guided review of one intentionally included information-disclosure flaw. You are expected to find and fix that issue; this is not an open-ended security audit, and you do not need to search for unrelated weaknesses.

## Learning Goals

- Trace invalid bearer tokens through a servlet filter.
- Remove sensitive diagnostic details from public responses.
- Preserve useful HTTP status and `WWW-Authenticate` semantics.
- Add an integration regression test for malformed tokens.
- Evaluate the operational trade-off between client errors and server-side diagnostics.

## Prerequisites

- Java 21, servlet filters, Maven, and JUnit 5.
- Familiarity with bearer authentication and `401` responses.
- The provided project at `module3/day5/devpro-authenticator-main`.
- Run commands from that project's root directory.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Find the intentional information-disclosure flaw

### What you will do

Run `ProtectedEndpointTest` and inspect `BearerTokenFilter`. Send a malformed token to `/api/greeting`, record its HTTP status and `WWW-Authenticate` header, and trace the verifier exception message into the response. Compare this with the response for missing credentials.

### Success criteria

- Missing credentials and invalid credentials are distinguished.
- Invalid credentials receive `401` and an `invalid_token` challenge.
- You see the problem with what is returned.
- You can state what should remain in the public contract.

### Hints

<details>
<summary>Hint 1</summary>

A client needs to know that its token is invalid; it usually does not need a parser or cryptography error message.

</details>

<details>
<summary>Hint 2</summary>

Start in `BearerTokenFilter.doFilter()` and follow the `TokenValidationException` catch block.

</details>

<details>
<summary>Hint 3</summary>

Compare `WWW-Authenticate` headers for a missing token and an invalid token in `ProtectedEndpointTest`.

</details>

<details>
<summary>Hint 4</summary>

Identify the verifier message exposed as `error_description` and locate where the exception detail enters the response.

</details>

## Part 2: Replace detail with a safe contract

### What you will do

Copy `starter/ProtectedEndpointHardeningTest.java` into `authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`. Confirm that the test fails against the current behavior. Update the filter so an invalid token still returns `401` with `error="invalid_token"`, but does not include the verifier's exception message. Run the full test suite.

### Success criteria

- Missing bearer credentials still return `401` without an invalid-token error.
- Malformed credentials return `401` with `invalid_token`.
- The response does not reveal verifier exception text or stack traces.
- The existing valid-token and insufficient-scope behavior remains unchanged.
- `mvn test` passes from the demo root.

### Hints

<details>
<summary>Hint 1</summary>

Keep the public reason generic, and preserve detailed diagnostics only in appropriately protected server-side logs if needed.

</details>

<details>
<summary>Hint 2</summary>

The filter currently creates a `BearerTokenError` from the caught exception's message.

</details>

<details>
<summary>Hint 3</summary>

Use the standard invalid-token error without attaching the exception description.

</details>

<details>
<summary>Hint 4</summary>

Keep the status and challenge construction intact; change only the detail passed into the error object, then assert that the header has no `error_description`.

</details>

# Bonus Challenge (Optional)

Add a test proving that a valid access token without `greeting.read` still returns `403` with `insufficient_scope`, and that the generic invalid-token handling did not collapse authorization failures into authentication failures.

# Reflection Questions

1. Which exact response information did the API retain, and which detail did it remove?
2. Why should malformed credentials and insufficient scope produce different statuses?
3. What could go wrong if detailed verifier exceptions were returned to every caller?
4. Which evidence made the leak easiest to locate: the response header or the filter's exception path?
5. What server-side logging would help diagnose invalid tokens without logging the token itself?
6. How would the response behavior change if a gateway performed token validation before this service?
