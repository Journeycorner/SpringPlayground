package de.medhelfer.web;

import de.medhelfer.security.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin
    @RequestMapping("/login")
    public String login(String username, String password) {
        return authenticationService.createToken(username, password);
    }
}
