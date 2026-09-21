# Security Testing Demo

This small Spring Boot API demonstrates the four key checks from the day 4 slide deck:

- no credentials -> `401 Unauthorized`
- valid login, wrong permissions -> `403 Forbidden`
- wrong user requests someone else's resource -> `403 Forbidden`
- valid authorized request -> `200 OK`

## Run

```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8080`.

## Demo flow

### 1. Missing credentials

```bash
curl -i http://localhost:8080/api/profile
```

Expected result: `401 Unauthorized`

### 2. Correct identity, wrong role

```bash
curl -i -u user:password http://localhost:8080/api/admin/reports
```

Expected result: `403 Forbidden`

### 3. Wrong user for a resource

```bash
curl -i -u alice:password http://localhost:8080/api/orders/102
```

Expected result: `403 Forbidden`

### 4. Valid and allowed request

```bash
curl -i -u alice:password http://localhost:8080/api/orders/101
```

Expected result: `200 OK`

## Tests

The project also contains a Rest Assured contract test that verifies the security behavior.

```bash
mvn test
```

## Teaching points

- Security is part of the API contract, not a frontend-only concern.
- `401` means the client is not authenticated.
- `403` means the client is authenticated but not allowed.
- Ownership checks are still needed even when the user is identified.
- Security tests should verify the unhappy path as deliberately as the happy path.
