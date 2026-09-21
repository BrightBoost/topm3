# CORS Demo

A small API and browser page for showing the difference between browser CORS rules and server-side authentication.

## Run

Start the API:

```bash
mvn spring-boot:run
```

In a second terminal, serve the frontend from port `5500`:

```bash
cd frontend
python3 -m http.server 5500
```

Open <http://localhost:5500> in a browser and click both buttons.

## Demo flow

- `GET /api/public` is available to the configured frontend origin.
- `GET /api/private` demonstrates that CORS does not authenticate a caller; this endpoint only returns a teaching message in the demo.
- Change the frontend port or origin and observe the browser console.
- Compare the browser request with curl, which does not enforce browser CORS behavior:

```bash
curl -i -H 'Origin: http://localhost:5500' http://localhost:8080/api/public
```

## Teaching points

- CORS is a browser-enforced cross-origin policy.
- CORS is not authentication or authorization.
- Allow the exact known origin instead of using `*` by default.
- Preflight requests matter when methods or headers are not simple.
