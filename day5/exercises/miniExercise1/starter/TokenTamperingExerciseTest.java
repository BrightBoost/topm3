package nl.topicus.devpro.authenticator.jwt;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Set;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import nl.topicus.devpro.authenticator.AuthorizationServerSettings;
import org.junit.jupiter.api.Test;

class TokenTamperingExerciseTest
{
    private static final AuthorizationServerSettings SETTINGS =
            AuthorizationServerSettings.forIssuer(URI.create("https://authenticator.example"));

    private static final Instant NOW = Instant.parse("2026-09-21T12:00:00Z");

    private final SigningKey signingKey = SigningKey.generate();

    private final TokenIssuer issuer = new TokenIssuer(SETTINGS, signingKey, fixedClock());

    private final TokenVerifier verifier =
            new TokenVerifier(SETTINGS, signingKey.publicKeys(), fixedClock());

    // TODO: assert that tampered token is denied
    @Test
    void anEditedSubjectCannotBeTrustedWithTheOriginalSignature() throws Exception
    {
        String validToken = issuer.issueAccessToken("student", "devpro-client",
                Set.of("greeting.read"));
        String tamperedToken = tamperSubject(validToken);

        // ASSERT
    }

    // TODO: tamper by changing the subject
    private String tamperSubject(String token) throws Exception
    {
        String[] parts = token.split("\\.");
        JWTClaimsSet editedClaims = new JWTClaimsSet.Builder(
                SignedJWT.parse(token).getJWTClaimsSet())
                .subject("student")
                .build();
        return parts[0] + "." + editedClaims.toPayload().toBase64URL() + "." + parts[2];
    }

    private static Clock fixedClock()
    {
        return Clock.fixed(NOW, ZoneOffset.UTC);
    }
}
