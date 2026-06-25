package nl.topicus.api.order;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Lab: HTTP-Methodes – Annotatie-bugs opsporen
 *
 * Deze klasse bevat vier methodes, elk met een fout in de HTTP-annotaties.
 * Jouw taak:
 *   1. Identificeer voor elke methode welke annotatie fout is
 *   2. Leg in één zin uit wat het probleem is en wat de consequentie is
 *   3. Schrijf de gecorrigeerde annotaties
 *
 * Je hoeft de method body NIET aan te passen – alleen de annotaties.
 */
@Path("/orders")
public class OrderResource {

    private final OrderService orderService = new OrderService();

    /**
     * Fout 1: deze methode maakt een nieuwe order aan.
     * Wat is er mis met de HTTP-annotatie?
     * Wat zijn de gevolgen in productie?
     */
    @GET                              // <-- FOUT: corrigeer deze annotatie
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderRequest request) {
        Order created = orderService.create(request);
        return Response.ok(created).build();
    }

    /**
     * Fout 2: deze methode verwijdert alle orders van een gebruiker.
     * Wat is er mis met de HTTP-annotatie?
     * Wat zijn de gevolgen als een CDN of monitoring tool dit endpoint aanroept?
     */
    @GET                              // <-- FOUT: corrigeer deze annotatie
    @Path("/clear")
    public Response deleteAllOrdersForUser(@QueryParam("userId") int userId) {
        orderService.deleteByUser(userId);
        return Response.noContent().build();
    }

    /**
     * Fout 3: deze methode werkt alleen het afleveradres bij van een bestaande order.
     * De overige velden van de order blijven ongewijzigd.
     * Wat is er mis met de HTTP-annotatie?
     */
    @POST                             // <-- FOUT: corrigeer deze annotatie
    @Path("/{id}/address")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDeliveryAddress(@PathParam("id") int id, AddressRequest address) {
        orderService.updateAddress(id, address);
        return Response.ok().build();
    }

    /**
     * Fout 4: deze methode vervangt een volledige order – alle velden worden overschreven
     * met de waarden uit de request body. Ontbrekende velden worden null.
     * Wat is er mis met de HTTP-annotatie?
     */
    @PATCH                            // <-- FOUT: corrigeer deze annotatie
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceOrder(@PathParam("id") int id, OrderRequest request) {
        Order updated = orderService.replace(id, request);
        return Response.ok(updated).build();
    }
}
