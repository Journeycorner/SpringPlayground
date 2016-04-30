package de.medhelfer.integration;

import de.medhelfer.BackendApplication;
import de.medhelfer.data.*;
import de.medhelfer.security.AuthenticationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    public void loginAccessOk() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_CLIENT);
        User user = new User("foo", "correctPlainTextPassword", roles);
        userService.addUser(user);

        String jsonWebToken = restTemplate.getForObject("http://localhost:8080/login?username={username}&password={password}",
                String.class,
                user.getUsername(),
                "correctPlainTextPassword");

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-TOKEN", "Bearer " + jsonWebToken);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Collection> exchange = restTemplate.exchange("http://localhost:8080/sensorReadings", HttpMethod.GET, entity, Collection.class);

        assertEquals(exchange.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Ignore
    public void loginOkWrongRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_CLIENT);
        User user = new User("foo", "correctPlainTextPassword", roles);
        userService.addUser(user);

        String jsonWebToken = restTemplate.getForObject("http://localhost:8080/login?username={username}&password={password}",
                String.class,
                user.getUsername(),
                "correctPlainTextPassword");

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-TOKEN", "Bearer " + jsonWebToken);
        Collection<SensorData> sensorReadings= new ArrayList<>();
        sensorReadings.add(new SensorData(LocalDateTime.now(), 20f, 0f));
        HttpEntity<Collection> entity = new HttpEntity<Collection>(sensorReadings, headers);
        restTemplate.exchange("http://localhost:8080/saveSensorReadings", HttpMethod.POST, entity, Void.class); // FIXME 400 Bad Request

//        assertEquals(exchange.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void loginWrongPassword() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_CLIENT);
        roles.add(Role.ROLE_SENSOR_READER);
        User user = new User("foo2", "correctPlainTextPassword", roles);
        userService.addUser(user);

        String jsonWebToken = restTemplate.getForObject("http://localhost:8080/login?username={username}&password={password}",
                String.class,
                user.getUsername(),
                "wrongPassword");

        assertNull(jsonWebToken);
    }

}
