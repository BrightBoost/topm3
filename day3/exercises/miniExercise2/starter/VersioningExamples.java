package nl.topicus.api.versioning;

/**
 * Starter voor de versioning & compatibility lab.
 *
 * Gebruik deze class als basis om verschillende response-varianten te vergelijken.
 */
public class VersioningExamples {

    public static final String V1_RESPONSE = """
            {
              "id": 10,
              "name": "Alice",
              "email": "alice@example.com",
              "role": "admin"
            }
            """;

    public static final String V2_RESPONSE = """
            {
              "id": 10,
              "name": "Alice",
              "role": "admin",
              "status": "active"
            }
            """;

    public static void main(String[] args) {
        System.out.println("Compare v1 and v2 contract evolution.");
        System.out.println("v1 response: " + V1_RESPONSE);
        System.out.println("v2 response: " + V2_RESPONSE);
    }
}
