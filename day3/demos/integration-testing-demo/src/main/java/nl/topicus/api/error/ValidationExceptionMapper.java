package nl.topicus.api.error;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.topicus.api.customer.InvalidCustomerException;

import java.time.Instant;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<InvalidCustomerException> {

    @Override
    public Response toResponse(InvalidCustomerException exception) {
        ErrorResponse body = new ErrorResponse(400, "Bad Request", exception.getMessage(), Instant.now().toString());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(body)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
