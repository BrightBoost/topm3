package nl.topicus.api.booking;

/**
 * Standaard error response body voor alle API-fouten.
 *
 * Velden:
 *   code      – machine-leesbare foutcode, bijv. "BOOKING_CONFLICT"
 *   message   – human-readable boodschap voor de developer
 *   timestamp – ISO 8601 tijdstip van de fout, bijv. "2026-06-22T10:15:30Z"
 *
 * Gebruik in een ExceptionMapper:
 *   ErrorBody body = new ErrorBody("BOOKING_CONFLICT", exception.getMessage(), Instant.now().toString());
 */
public class ErrorBody {

    private String code;
    private String message;
    private String timestamp;

    // Standaard constructor voor JSON-deserialisatie
    public ErrorBody() {
    }

    public ErrorBody(String code, String message, String timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
