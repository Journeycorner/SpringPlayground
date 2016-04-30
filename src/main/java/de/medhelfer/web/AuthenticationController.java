package de.medhelfer.web;

import de.medhelfer.security.AuthenticationService;
import org.springframework.util.StringUtils;
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
        return (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) ?
                null :
                authenticationService.createToken(username, password);
    }
}
