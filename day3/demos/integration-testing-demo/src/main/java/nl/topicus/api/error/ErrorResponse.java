package nl.topicus.api.error;

/**
 * One consistent error shape used across the whole API.
 * The timestamp is a plain ISO-8601 string, per the day 3 naming conventions.
 */
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String timestamp;

    public ErrorResponse() {
        // required by Jackson
    }

    public ErrorResponse(int status, String error, String message, String timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
