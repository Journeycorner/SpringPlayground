package de.medhelfer;

import de.medhelfer.data.*;
import de.medhelfer.security.AuthenticationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BackendApplication.class)
@WebIntegrationTest
public class AuthenticationIntegrationTests {

    @Inject AuthenticationService authenticationService;
    @Inject UserService userService;
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void loginOk() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CLIENT);
        roles.add(Role.SENSOR_READER);
        User user = new User("foo", "correctPlainTextPassword", roles);
        userService.addUser(user);

        String jsonWebToken = restTemplate.getForObject("http://localhost:8080/login?username={username}&password={password}",
                String.class,
                user.getUsername(),
                "correctPlainTextPassword");
        Authentication authentication = authenticationService.parseToken(jsonWebToken);

        assertEquals(authentication.getName(), user.getUsername());
    }

    @Test
    public void loginWrongPassword() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CLIENT);
        roles.add(Role.SENSOR_READER);
        User user = new User("foo2", "correctPlainTextPassword", roles);
        userService.addUser(user);

        String jsonWebToken = restTemplate.getForObject("http://localhost:8080/login?username={username}&password={password}",
                String.class,
                user.getUsername(),
                "wrongPassword");

        assertNull(jsonWebToken);
    }

}
