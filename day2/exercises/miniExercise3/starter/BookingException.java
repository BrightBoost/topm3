package nl.topicus.api.booking;

/**
 * Custom exception voor booking-gerelateerde business logic fouten.
 *
 * Bevat een machine-leesbare errorCode (bijv. "BOOKING_CONFLICT", "ACCOUNT_BLOCKED")
 * naast de normale human-readable message.
 */
public class BookingException extends RuntimeException {

    private final String errorCode;

    public BookingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
