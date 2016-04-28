package de.medhelfer.web;

import de.medhelfer.data.SensorDataDto;
import de.medhelfer.data.SensorDataService;
import de.medhelfer.security.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public @ResponseBody String authenticate(@RequestBody UsernamePassword usernamePassword) {
        return authenticationService.createToken(usernamePassword);
    }
}
