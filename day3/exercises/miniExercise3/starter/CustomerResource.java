package nl.topicus.api.customer;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
public class CustomerResource {

    private final CustomerRepository customerRepository = new CustomerRepository();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") int id) {
        return customerRepository.findById(id)
                .map(customer -> Response.ok(customer).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found")
                        .build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        Customer saved = customerRepository.save(customer);
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
}
