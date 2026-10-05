package nl.topicus.devpro.authenticator.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.nimbusds.oauth2.sdk.Scope;
import nl.topicus.devpro.authenticator.app.AuthorizationCodeFlow.Tokens;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TokenPurposeExerciseTest
{
	private static final URI REDIRECT_URI = URI.create("http://localhost:8080/callback");

	@Value("${local.server.port}")
	private int port;

	private final HttpClient client = HttpClient.newHttpClient();

	@Test
	void idTokenIsNotAnApiAccessToken() throws Exception
	{
		Tokens tokens = flow().run("student", "devpro", new Scope("openid", "greeting.read"));
		assertThat(tokens.idToken()).isNotNull();

		HttpResponse<String> idTokenResponse = requestGreeting(tokens.idToken());
		HttpResponse<String> accessTokenResponse = requestGreeting(tokens.accessToken());

		
        // TODO: assert the 401 and 200 status codes
		// TODO: assert the ID-token WWW-Authenticate header identifies an invalid token.
		// TODO: compare token typ/aud/scope and explain why the API accepts only one.
	}

	private AuthorizationCodeFlow flow()
	{
		return new AuthorizationCodeFlow(baseUri(), "devpro-client", "devpro-secret", REDIRECT_URI);
	}

	private HttpResponse<String> requestGreeting(String token) throws Exception
	{
		HttpRequest request = HttpRequest.newBuilder(baseUri().resolve("/api/greeting"))
			.header("Authorization", "Bearer " + token)
			.GET()
			.build();
		return client.send(request, BodyHandlers.ofString());
	}

	private URI baseUri()
	{
		return URI.create("http://localhost:" + port);
	}
}
