package nl.topicus.devpro.authenticator.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProtectedEndpointHardeningTest
{
	@Value("${local.server.port}")
	private int port;

	private final HttpClient client = HttpClient.newHttpClient();

	@Test
	void malformedTokenDoesNotExposeVerifierDetails() throws Exception
	{
		HttpResponse<String> response = client.send(
			HttpRequest.newBuilder(baseUri().resolve("/api/greeting"))
				.header("Authorization", "Bearer not.a.token")
				.GET()
				.build(),
			BodyHandlers.ofString());

		assertThat(response.statusCode()).isEqualTo(401);
		String challenge = response.headers().firstValue("WWW-Authenticate").orElseThrow();
		assertThat(challenge).contains("error=\"invalid_token\"");
		assertThat(challenge).doesNotContain("error_description");
		assertThat(response.body()).doesNotContain("Token is malformed", "signature");
	}

	private URI baseUri()
	{
		return URI.create("http://localhost:" + port);
	}
}
