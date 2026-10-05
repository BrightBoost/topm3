# Lab: Keep Access Tokens and ID Tokens in Their Lanes

## Scenario / Context

A client team has started forwarding every token returned by its identity provider to every API. The resource-server team needs a regression test proving that an ID token, intended for the client, cannot be used as an API access token. The provided authenticator already issues both token types and protects a real HTTP endpoint.

## Learning Goals

- Exercise an OAuth authorization-code flow with PKCE.
- Distinguish access-token and ID-token purposes.
- Add an HTTP-level test for token substitution.
- Relate `typ`, `aud`, and `scope` to API acceptance.
- Evaluate how a client should handle tokens safely.

## Prerequisites

- Java 21, Maven, JUnit 5, and basic HTTP knowledge.
- Familiarity with OAuth/OIDC terminology and `401`/`403`.
- The provided project at `module3/day5/devpro-authenticator-main`.
- Run commands from that project's root directory.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Observe both tokens in the flow

### What you will do

Go to the `app` project. Read `AuthorizationCodeFlow` and `ProtectedEndpointTest`, then run the existing protected-endpoint tests. Inspect the returned access token and ID token. Record their `typ`, `aud`, and `scope` values, and identify which component consumes each token.

### Success criteria

- A successful authorization-code flow returns an access token.
- An OIDC request also returns an ID token.
- The access token is addressed to the API; the ID token is addressed to the client.
- You can explain why the ID token is not an API credential.

### Hints

<details>
<summary>Hint 1</summary>

The word “token” does not mean every token has the same audience or purpose.

</details>

<details>
<summary>Hint 2</summary>

Inspect `issueAccessToken()` and `issueIdToken()` in `TokenIssuer`.

</details>

<details>
<summary>Hint 3</summary>

The access token uses `at+jwt`; the ID token uses the ordinary JWT type and names the client as its audience.

</details>

## Part 2: Prove the API rejects token substitution

### What you will do

Copy `starter/TokenPurposeExerciseTest.java` into `authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`. Run the test, then extend it to call `/api/greeting` once with the ID token and once with the access token. Assert `401` for the ID token and `200` for the access token; inspect the authentication challenge on refusal.

### Success criteria

- Both requests use tokens from the same real authorization-code flow.
- The resource endpoint rejects the ID token with `401`.
- The endpoint accepts the appropriately scoped access token.
- The test verifies the HTTP response, not just a decoded JWT payload.
- `mvn -pl authenticator-app test` passes.

### Hints

<details>
<summary>Hint 1</summary>

Use the `Tokens` record returned by `AuthorizationCodeFlow.run()`.

</details>

<details>
<summary>Hint 2</summary>

The helper's `baseUri()` already uses the random test-server port.

</details>

<details>
<summary>Hint 3</summary>

Send each token as `Authorization: Bearer ...`; compare the status codes and `WWW-Authenticate` header.

</details>

<details>
<summary>Hint 4</summary>

Keep the scope `greeting.read` in the authorization request so the valid access token reaches the resource method.

</details>
<details>
<summary>Hint 5</summary>

You can assert for the status codes:

```java
assertThat(idTokenResponse.statusCode()).isEqualTo(401);
assertThat(accessTokenResponse.statusCode()).isEqualTo(200);
```

</details>

# Bonus Challenge (Optional)

Request a token without `greeting.read` and verify that the token is valid but insufficient for the endpoint (`403`). Explain how that differs from the ID-token rejection (`401`).

# Reflection Questions

1. Why is the access token's audience different from the ID token's audience?
2. Which checks prevent the ID token from being treated as an access token?
3. What could break if an API accepted any signed JWT from a trusted issuer?
4. Which failure was most useful to inspect: the HTTP challenge or the JWT header and claims?
5. How should a client store and transmit the access token compared with the ID token?
6. How would the API verify tokens if the issuer were a separate service publishing a JWKS endpoint?
