# Starter

Run the service with:

```bash
mvn compile exec:java -Dexec.mainClass=com.example.day4.SecurityApi
```

The starter intentionally returns incorrect `500` responses for authentication and authorization failures. Use curl to inspect the behavior:

```bash
curl -i http://localhost:8080/profile
curl -i -u user:password http://localhost:8080/admin/reports
curl -i -u admin:password http://localhost:8080/admin/reports
```

The lab asks you to repair the behavior and add tests.
