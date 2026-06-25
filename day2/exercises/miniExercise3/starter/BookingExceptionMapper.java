package nl.topicus.api.booking;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

/**
 * Lab: Foutafhandeling – implementeer deze ExceptionMapper
 *
 * Jouw taak:
 *   1. Voeg de juiste annotatie toe zodat Jakarta REST deze klasse registreert
 *   2. Implementeer de juiste interface (hint: ExceptionMapper voor BookingException)
 *   3. Implementeer de toResponse-methode:
 *      - Statuscode: 409 Conflict
 *      - Body: een ErrorBody met de errorCode uit de exception, de message en de timestamp
 *      - Content-Type: application/json
 *
 * Je mag GEEN stacktrace of interne informatie in de response zetten.
 */
// TODO: voeg @Provider toe
public class BookingExceptionMapper /* TODO: implementeer ExceptionMapper<BookingException> */ {

    // TODO: implementeer de methode toResponse(BookingException exception)
    //
    // Structuur die je nodig hebt:
    //   - Maak een ErrorBody aan met exception.getErrorCode(), exception.getMessage() en Instant.now().toString()
    //   - Return Response.status(Response.Status.CONFLICT)
    //                    .entity(body)
    //                    .type(MediaType.APPLICATION_JSON)
    //                    .build();

}
