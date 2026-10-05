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
class ProfilesResourceTest
{
	private static final URI REDIRECT_URI = URI.create("http://localhost:8080/callback");

	@Value("${local.server.port}")
	private int port;

	private final HttpClient client = HttpClient.newHttpClient();

	@Test
	void callersCanReadOnlyTheirOwnProfile() throws Exception
	{
		Tokens tokens = flow().run("student", "devpro", new Scope("openid", "greeting.read"));

		HttpResponse<String> ownProfile = requestProfile("student", tokens.accessToken());
		HttpResponse<String> anotherProfile =
			requestProfile("administrator", tokens.accessToken());

		assertThat(ownProfile.statusCode()).isEqualTo(200);
		assertThat(ownProfile.body()).contains("student");
		assertThat(anotherProfile.statusCode()).isEqualTo(403);
	}

	@Test
	void profileRequiresAuthentication() throws Exception
	{
		assertThat(requestProfile("student", null).statusCode()).isEqualTo(401);
	}

	private AuthorizationCodeFlow flow()
	{
		return new AuthorizationCodeFlow(baseUri(), "devpro-client", "devpro-secret", REDIRECT_URI);
	}

	private HttpResponse<String> requestProfile(String username, String token) throws Exception
	{
		HttpRequest.Builder request = HttpRequest.newBuilder(
			baseUri().resolve("/api/profiles/" + username)).GET();
		if (token != null)
			request.header("Authorization", "Bearer " + token);
		return client.send(request.build(), BodyHandlers.ofString());
	}

	private URI baseUri()
	{
		return URI.create("http://localhost:" + port);
	}
}
