# Lab: Authorization Rules and IDOR

## Scenario / Context

A customer API has user profiles and orders. The first security review found that the application checks whether someone is logged in, but does not always check whether that person owns the requested resource. Your team must close these authorization gaps before the API is used with real customer data.

## Learning Goals

- Identify broken object-level authorization in API code.
- Implement role-based and resource-based authorization checks.
- Write tests for ownership boundaries and admin access.
- Explain why UI restrictions do not protect API resources.

## Prerequisites

- Basic Java, HTTP, and JUnit knowledge.
- Familiarity with users, roles, resource IDs, and `403` responses.
- JDK 17 or newer and Maven.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Find the authorization bugs

### What you will do

Inspect `OrderService.java` and the existing tests. Trace the current user through requests for another user's order and identify every path that returns data without checking ownership.

### Success criteria

- You can point to each missing authorization decision.
- You can distinguish a role check from an ownership check.
- You can describe the data exposure caused by each bug.

### Hints

<details>
<summary>Hint 1</summary>

Authentication answers who the caller is; it does not automatically grant access to every order.

</details>

<details>
<summary>Hint 2</summary>

Compare the authenticated user ID with the owner ID on the order before returning or changing it.

</details>

<details>
<summary>Hint 3</summary>

Check both read and write paths. IDOR can expose data through `GET` and alter it through `PUT` or `DELETE`.

</details>

## Part 2: Enforce and test the rules

### What you will do

Implement the authorization rules in the service and add tests for a normal user, another user's resource, and an admin. Choose and document whether unauthorized ownership access returns `403` or `404`.

### Success criteria

- A user can read and update their own order.
- A user cannot read, update, or delete another user's order.
- An admin can access the permitted orders.
- Tests cover both allowed and denied requests.
- The chosen `403`/`404` policy is documented.

### Hints

<details>
<summary>Hint 1</summary>

Make the authorization decision before calling the repository mutation or returning the object.

</details>

<details>
<summary>Hint 2</summary>

A small method such as `canAccess(user, order)` can make the rule visible and reusable.

</details>

<details>
<summary>Hint 3</summary>

Do not trust an `ownerId` supplied by the request body when the authenticated identity is already known.

</details>

<details>
<summary>Hint 4</summary>

A safe sequence is: load resource -> check role or ownership -> perform action -> return result.

</details>

# Bonus Challenge (Optional)

Refactor the repository query so ordinary users can only retrieve orders belonging to their own user ID. Compare this with loading any order first and checking access in the service.

# Reflection Questions

1. Which authorization check prevented the IDOR bug, and where should that check live?
2. What is the trade-off between returning `403` and hiding resource existence with `404`?
3. Why is trusting `ownerId` from a request body unsafe?
4. What would you log when an ownership check fails, and which sensitive fields would you exclude?
5. How would the design change for a manager who may access every order in one department?
6. How would you test the same rule through a real HTTP endpoint instead of a service unit test?
