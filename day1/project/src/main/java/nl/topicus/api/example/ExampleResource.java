package nl.topicus.api.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Een werkend voorbeeld van een JAX-RS resource.
 *
 * Annotaties uitgelegd:
 *   @Path("/examples")    → deze class behandelt requests op /api/examples
 *   @GET                  → deze methode wordt aangeroepen bij een HTTP GET
 *   @Produces(...)        → de response body is JSON
 *   @PathParam("id")      → haalt het {id} stuk uit de URL op
 *
 * Verwijder deze class als je je eigen resource hebt aangemaakt.
 */
@Path("/examples")
public class ExampleResource {

    // Hardcoded data voor nu -- database komt later deze week
    private static final List<Example> EXAMPLES = List.of(
            new Example(1, "Eerste voorbeeld", "Zo ziet een resource eruit"),
            new Example(2, "Tweede voorbeeld", "Vervang dit door jouw eigen domein")
    );

    /**
     * GET /api/examples
     * Geeft alle voorbeelden terug als JSON array.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Example> getAll() {
        return EXAMPLES;
    }

    /**
     * GET /api/examples/{id}
     * Geeft één voorbeeld terug op ID, of 404 als het niet bestaat.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        return EXAMPLES.stream()
                .filter(e -> e.id() == id)
                .findFirst()
                .map(e -> Response.ok(e).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
