package nl.topicus.api.user;

/**
 * Exception voor business logic fouten waarbij een actie conflicteert
 * met de huidige staat van het systeem.
 *
 * Voorbeeld: een e-mailadres registreren dat al in gebruik is.
 *
 * Gebruik in UserService:
 *   throw new ConflictException("Email already in use: " + email);
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
