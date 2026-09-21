# IDOR Demo

This demo makes the resource-ownership problem visible by exposing the same order through two endpoints:

- `/api/vulnerable/orders/{id}` returns an order after authentication only.
- `/api/secure/orders/{id}` checks the authenticated user's ownership.

## Run

```bash
mvn spring-boot:run
```

Users are `alice:password` and `bob:password`.

## Demo flow

Alice owns order `101`; Bob owns order `102`:

```bash
curl -i -u alice:password http://localhost:8080/api/vulnerable/orders/102
curl -i -u alice:password http://localhost:8080/api/secure/orders/102
curl -i -u alice:password http://localhost:8080/api/secure/orders/101
```

The vulnerable endpoint demonstrates IDOR. The secure endpoint returns `403` when the authenticated user does not own the order.

## Teaching points

- Authentication alone does not establish resource access.
- The server must compare the authenticated subject with the resource owner.
- The same check must apply to reads and writes.
- The demo uses an in-memory map so the ownership rule stays visible.
