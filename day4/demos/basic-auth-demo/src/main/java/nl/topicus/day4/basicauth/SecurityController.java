package nl.topicus.day4.basicauth;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping("/api/profile")
    public String profile(Authentication authentication) {
        return "profile for " + authentication.getName();
    }

    @GetMapping("/api/admin/reports")
    public String adminReports(Authentication authentication) {
        return "admin report for " + authentication.getName();
    }
}
