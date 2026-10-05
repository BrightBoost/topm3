package nl.topicus.devpro.authenticator.app;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import nl.topicus.devpro.authenticator.jwt.VerifiedAccessToken;
import nl.topicus.devpro.authenticator.resource.BearerTokenFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilesResource
{
	@GetMapping("/api/profiles/{username}")
	public ResponseEntity<Map<String, String>> profile(@PathVariable String username,
			HttpServletRequest request)
	{
		VerifiedAccessToken token = BearerTokenFilter.tokenOf(request);

		// TODO: deny access when the requested username differs from the verified subject.
		// Return a forbidden response before constructing the profile body.

		return ResponseEntity.ok(Map.of("username", username, "displayName", "DevPro Student"));
	}
}
