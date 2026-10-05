# Lab: Close an IDOR in a Protected API

## Scenario / Context

A profile endpoint is being added to a customer API. Login is already handled, but the route accepts a username in its URL. The API team must ensure that a valid caller can read only the profile belonging to the verified token subject. The supplied authenticator provides the HTTP server, signed-token verification, and test flow; no project scaffolding is needed.

## Learning Goals

- Implement resource-based authorization using a verified subject.
- Prevent broken object-level authorization on a URL identifier.
- Add integration tests for owner and non-owner requests.
- Distinguish authentication failures from authorization failures.
- Justify the API's chosen `403`/`404` behavior.

## Prerequisites

- Java 21, Spring MVC, Maven, and JUnit 5.
- Familiarity with bearer tokens, request mappings, and `401`/`403`.
- The provided project at `module3/day5/devpro-authenticator-main`.
- Run commands from that project's root directory.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Add an ownership-protected resource

### What you will do

Copy `starter/ProfilesResource.java` into `authenticator-app/src/main/java/nl/topicus/devpro/authenticator/app/`. Complete the endpoint so `GET /api/profiles/{username}` obtains the verified token through `BearerTokenFilter.tokenOf(request)`, returns the caller's own profile, and denies requests for a different username with `403`.

### Success criteria

- A request without a bearer token is rejected by the existing filter.
- The authenticated subject comes from the verified token, not a request parameter or header.
- A caller can read their own profile.
- A caller cannot read another profile by changing the path parameter.
- The endpoint returns only the profile fields needed for this exercise.

### Hints

<details>
<summary>Hint 1</summary>

Authentication establishes the subject. It does not grant access to every resource identifier.

</details>

<details>
<summary>Hint 2</summary>

Use `BearerTokenFilter.tokenOf(request)` after the filter has verified the bearer token.

</details>

<details>
<summary>Hint 3</summary>

Compare the requested username with `VerifiedAccessToken.subject()` before creating the response.

</details>

<details>
<summary>Hint 4</summary>

A clear sequence is: get verified token, compare subject and path value, return `403` on mismatch, otherwise return the profile.

</details>

## Part 2: Test the ownership boundary over HTTP

### What you will do

Copy `starter/ProfilesResourceTest.java` into `authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`. Use the existing `AuthorizationCodeFlow` helper to obtain a scoped access token. Test owner access, another user's profile, and a request without a token. Run the app-module tests.

### Success criteria

- `student` can request `/api/profiles/student` with `200`.
- `student` cannot request `/api/profiles/administrator` with `403`.
- A missing token produces `401` before the controller runs.
- All cases exercise the real HTTP filter and endpoint.
- `mvn -pl authenticator-app test` passes.

### Hints

<details>
<summary>Hint 1</summary>

The `AuthorizationCodeFlow` helper returns access and ID tokens; use the access token.

</details>

<details>
<summary>Hint 2</summary>

Request `openid` and `greeting.read`; the existing filter requires the latter for `/api/*`.

</details>

<details>
<summary>Hint 3</summary>

Build request URIs from the random port injected by `@Value("${local.server.port}")`.

</details>

<details>
<summary>Hint 4</summary>

For each case, send a GET to the same endpoint with either the bearer header or no header, then assert the contract's status code.

</details>

# Bonus Challenge (Optional)

Choose whether a non-owner request should return `403` or conceal resource existence with `404`. Implement the policy consistently and document how it affects clients and security monitoring.

# Reflection Questions

1. Why must the subject come from `VerifiedAccessToken` rather than the URL or a client-supplied header?
2. Which part of the implementation closes the object-level authorization gap?
3. What data could leak if the endpoint returned a whole user record?
4. Which test caught the IDOR most directly, and what made that failure reproducible?
5. When might `404` be preferable to `403` for a non-owner request?
6. How would the ownership rule change for a manager permitted to see a department's profiles?
