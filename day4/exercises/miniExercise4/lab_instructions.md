# Lab: Security Tests and API Security Review

## Scenario / Context

Your API has happy-path integration tests, but the security review asks a harder question: can an unknown client, a normal user, or a caller with a guessed resource ID access data they should not see? Build a focused security test plan and use it to review an API contract.

## Learning Goals

- Design tests for authentication and authorization boundaries.
- Distinguish `401`, `403`, and contractually chosen `404` behavior.
- Identify IDOR, excessive data exposure, validation, and rate-limit risks.
- Justify a security improvement using observable API behavior.

## Prerequisites

- Completed the API integration testing material.
- Familiarity with Rest Assured or an equivalent HTTP client.
- Familiarity with `401`, `403`, IDOR, validation, and API contracts.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Design security tests

### What you will do

Create a table of requests for a protected API. Include the request, identity or role, expected status, expected body behavior, and the security rule being tested.

At minimum cover:

- no credentials
- invalid credentials
- normal user on an admin endpoint
- user A requesting user B's resource
- valid owner request
- valid admin request
- malformed or oversized input

### Success criteria

- Every security boundary has a test.
- Expected status codes and response behavior are explicit.
- At least one test verifies that sensitive data is not exposed.
- The tests distinguish authentication, authorization, validation, and abuse controls.

### Hints

<details>
<summary>Hint 1</summary>

Start with the identity and permission matrix before writing requests.

</details>

<details>
<summary>Hint 2</summary>

A status assertion alone is not enough for sensitive data. Also inspect the body and relevant headers.

</details>

<details>
<summary>Hint 3</summary>

For IDOR, use two users and a resource owned by only one of them.

</details>

## Part 2: Review the API against a security checklist

### What you will do

Review the supplied contract or your project using a short checklist. Classify each finding and propose the smallest useful fix. Then implement one high-value fix or write the corresponding failing test.

Review at least:

- broken object-level authorization
- broken authentication
- excessive data exposure
- missing rate limiting
- injection or weak validation
- security misconfiguration
- secrets in source code or logs

### Success criteria

- Findings are tied to a concrete endpoint or response.
- Each finding has a severity or priority and a proposed fix.
- At least one security test or implementation change is completed.
- The review distinguishes a real vulnerability from a browser-only CORS problem.

### Hints

<details>
<summary>Hint 1</summary>

Follow data from the incoming request to the response and ask where identity and ownership are checked.

</details>

<details>
<summary>Hint 2</summary>

Look for data returned simply because it exists, especially passwords, tokens, internal IDs, and stack traces.

</details>

<details>
<summary>Hint 3</summary>

Prioritize issues that let one user access another user's data or perform an action without permission.

</details>

# Bonus Challenge (Optional)

Convert the most important test cases into executable Rest Assured tests in your project. Run them against a fresh in-memory database and document the chosen `403`/`404` policy.

# Reflection Questions

1. Which security test gave the highest value and why?
2. Why can a passing happy-path integration suite coexist with an IDOR vulnerability?
3. What is the trade-off between returning `403` and `404` for another user's resource?
4. Which finding would be most urgent in production: excessive data exposure, missing rate limiting, or a minor CORS misconfiguration? Justify your choice.
5. How would the test setup change when authentication uses JWT scopes instead of Basic Auth roles?
6. What evidence would you collect before changing a production authorization rule?
7. How would you keep these tests useful as new roles and resources are added?
