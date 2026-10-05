# Lab: Trust the Token, Not Its Payload

## Scenario / Context

A resource-server team received reports that a caller can change a JWT claim and still reach protected data. The API owner needs reproducible evidence that token claims are accepted only after cryptographic and claim validation. The provided DevPro Authenticator is already wired and runnable, so your will can focus on security behavior rather than project setup. However, you are going to familiarize yourself with the setup.

## Learning Goals

- Inspect how `TokenIssuer` signs access tokens.
- Extend verifier tests to cover payload tampering.
- Explain why decoding a JWT does not verify it.
- Evaluate the signature and claims checks as a trust boundary.
- Adapt a verifier test to a different protected API.

## Prerequisites

- Java 21, Maven, and JUnit 5.
- Basic familiarity with JSON, HTTP, and Java tests.
- The provided project at `module3/day5/devpro-authenticator-main`.
- Run commands from that project's root directory.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Inspect a token's trust boundary

### What you will do

Issue an access token using the prepared test fixtures. Inspect its header and payload, then trace the verifier's signature, issuer, audience, expiry, and token-type checks. Record which checks happen before claims are used.

### Success criteria

- A valid issued access token passes verification.
- The decoded payload is distinguished from a verified token.
- You can identify the issuer, audience, subject, scope, and expiry claims.
- You can point to the verifier checks that protect each value.

### Hints

<details>
<summary>Hint 1</summary>

A JWT's payload is encoded, not encrypted. Readable claims are not proof of authenticity.

</details>

<details>
<summary>Hint 2</summary>

Start with `TokenIssuer` and `TokenVerifierTest` under `authenticator-core/src/main` and `src/test`.

</details>

<details>
<summary>Hint 3</summary>

Follow `verifyAccessToken()` in `TokenVerifier`: note the order of type, signature, issuer/audience, and time checks.

</details>




## Part 2: Add a tampering regression test

### What you will do

Copy `starter/TokenTamperingExerciseTest.java` into `authenticator-core/src/test/java/nl/topicus/devpro/authenticator/jwt/`. Complete the test so it changes a signed token's subject while retaining its original signature, then assert that `verifyAccessToken()` refuses it. Run the focused test and the full core test suite.

### Success criteria

- The test starts from an authentic token issued by the fixture.
- The modified token contains a different subject but the original signature.
- Verification throws `TokenValidationException`.
- `mvn -pl authenticator-core test` passes.

### Hints

<details>
<summary>Hint 1</summary>

Change one claim in the payload; do not re-sign the changed payload.

</details>

<details>
<summary>Hint 2</summary>

Nimbus `SignedJWT.parse(token).getJWTClaimsSet()` reads claims. Reading them does not establish trust.

</details>

<details>
<summary>Hint 3</summary>

The compact token has three dot-separated parts. Keep the first and third parts and replace only the payload part.

</details>

<details>
<summary>Hint 4</summary>

Build updated claims from the original claims, change `sub`, encode the claims as a payload, and assemble `header.payload.originalSignature`.

</details>

<summary>Hint 5</summary>

You can change the subject from `student` to `administrator`.

</details>

<details>

<summary>Hint 6</summary>

Verify the refusal with the following assertion:

```java
assertThatExceptionOfType(TokenValidationException.class)
    .isThrownBy(() -> verifier.verifyAccessToken(tamperedToken));
```

</details>

# Bonus Challenge (Optional)

Add a second rejection test for an access token with an incorrect `aud` or `iss`. Sign the deliberately incorrect claims with the known test key so the test proves claim validation, rather than failing earlier on the signature.

# Reflection Questions

1. Why does changing `sub` without changing the signature make this particular token invalid?
2. Which checks in `TokenVerifier` are cryptographic, and which validate token context?
3. What operational problem could result from accepting a validly signed token with the wrong audience?
4. Which step was hardest to debug: constructing the tampered JWT or interpreting the verifier failure?
5. How would a resource server obtain trusted keys if it ran separately from this issuer?
6. What key-rotation behavior would production need that this in-memory demo omits?
