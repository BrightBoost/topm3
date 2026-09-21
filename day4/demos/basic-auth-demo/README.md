# Basic Auth Demo

A small Spring Boot API for demonstrating authentication, authorization, roles, and the difference between `401` and `403`.

## Run

```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8080`.

## Demo flow

No credentials: the API challenges the client with `401`:

```bash
curl -i http://localhost:8080/api/profile
```

A normal user can access the profile:

```bash
curl -i -u user:password http://localhost:8080/api/profile
```

The same user is authenticated but forbidden from the admin endpoint:

```bash
curl -i -u user:password http://localhost:8080/api/admin/reports
```

An admin can access it:

```bash
curl -i -u admin:password http://localhost:8080/api/admin/reports
```

## Teaching points

- Authentication establishes identity.
- Authorization checks permissions after identity is known.
- `401` means the client is not authenticated.
- `403` means the client is authenticated but not allowed.
- Basic Auth is suitable for this demo, but production traffic needs HTTPS and stronger credential management.
