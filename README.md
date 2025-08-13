# JWT Auth API

This is a simple Spring Boot REST API demonstrating JWT-based authentication.

## Endpoints

- `POST /auth/login` – Authenticate with JSON body `{ "username": "alice", "password": "password1" }` and receive a JWT token.
- `GET /auth/validate` – Send the JWT token in the `Authorization: Bearer <token>` header to validate it and receive the associated username.

User data is stored locally in `src/main/resources/users.json`.

## Building and Running

```bash
mvn spring-boot:run
```

## Testing

```bash
mvn test
```
