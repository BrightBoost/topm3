package nl.topicus.day4.cors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {
    @GetMapping("/api/public")
    public String publicData() {
        return "The browser may read this response from the configured origin.";
    }

    @GetMapping("/api/private")
    public String privateData() {
        return "CORS did not authenticate this request; real APIs still need auth checks.";
    }
}
