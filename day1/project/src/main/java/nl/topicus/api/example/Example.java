package nl.topicus.api.example;

/**
 * Een voorbeeld-resource om de structuur te tonen.
 *
 * Stap 1: start de server en test deze endpoints in Postman:
 *   GET  http://localhost:8080/api/examples
 *   GET  http://localhost:8080/api/examples/1
 *   GET  http://localhost:8080/api/examples/999  (wat krijg je terug?)
 *
 * Stap 2: maak je eigen resource class in een nieuw package naast dit package.
 *         Verwijder daarna deze ExampleResource (en het Example record).
 */
public record Example(int id, String name, String description) {
}
