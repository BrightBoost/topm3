package nl.topicus.api.product;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

/**
 * Lab: Statuscodes – Foute return-statements repareren
 *
 * Deze klasse bevat vier endpoints, elk met een foute statuscode.
 * Jouw taak:
 *   1. Identificeer voor elk endpoint welke statuscode verkeerd is
 *   2. Leg in één zin uit wat het gevolg is van de foute code
 *   3. Schrijf de gecorrigeerde return-statement
 *
 * Je hoeft de overige logica NIET aan te passen – alleen de return-statements.
 *
 * Beschikbare Response-builder methodes:
 *   Response.ok(entity)                              → 200 OK
 *   Response.created(uri).entity(entity).build()     → 201 Created + Location header
 *   Response.noContent().build()                     → 204 No Content
 *   Response.status(Response.Status.NOT_FOUND).build() → 404 Not Found
 *   Response.status(Response.Status.BAD_REQUEST)
 *           .entity(body).build()                    → 400 Bad Request
 */
@Path("/products")
public class ProductResource {

    private final ProductService productService = new ProductService();

    /**
     * POST /products
     * Maakt een nieuw product aan.
     *
     * Fout: Response.ok() geeft 200 terug, maar een succesvolle aanmaak
     * hoort een andere statuscode te hebben. Bovendien ontbreekt de Location-header.
     *
     * Corrigeer de return-statement. Bouw een URI aan via:
     *   URI location = URI.create("/api/products/" + created.getId());
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductRequest request) {
        Product created = productService.create(request);

        // FOUT: corrigeer deze return-statement
        return Response.ok(created).build();
    }

    /**
     * DELETE /products/{id}
     * Verwijdert een product. Er is niets terug te sturen.
     *
     * Fout: Response.ok() geeft 200 met een lege body. Dat is technisch
     * niet fout, maar de conventie voor een delete zonder response body is anders.
     *
     * Corrigeer de return-statement.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        productService.delete(id);

        // FOUT: corrigeer deze return-statement
        return Response.ok().build();
    }

    /**
     * GET /products/{id}
     * Geeft een product terug op ID.
     *
     * Fout: als het product niet bestaat, geeft de code 200 OK terug met
     * een lege body. Een client weet daardoor niet of het product bestaat of niet.
     *
     * Corrigeer het return-statement in de if-branch.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") int id) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            // FOUT: corrigeer deze return-statement
            return Response.ok().build();
        }
        return Response.ok(product.get()).build();
    }

    /**
     * PUT /products/{id}
     * Vervangt een product volledig.
     *
     * Fout: de catch-block vangt een ValidationException op (een client-fout)
     * en geeft daar 500 Internal Server Error voor terug. Dat is verkeerd:
     * de client heeft ongeldige input gestuurd.
     *
     * Corrigeer het return-statement in de catch-block.
     * Je mag een eenvoudige error message string als entity meegeven.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, ProductRequest request) {
        try {
            Product updated = productService.replace(id, request);
            return Response.ok(updated).build();
        } catch (ValidationException e) {
            // FOUT: corrigeer deze return-statement
            return Response.serverError().build();
        }
    }
}
