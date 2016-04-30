package de.medhelfer.unit;

import de.medhelfer.BackendApplication;
import de.medhelfer.data.Role;
import de.medhelfer.data.User;
import de.medhelfer.data.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BackendApplication.class)
@WebAppConfiguration
public class UserServiceTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void saveFindUsers() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.ROLE_CLIENT);
		roles.add(Role.ROLE_SENSOR_READER);
		User toSave = new User("username", "password", roles);
		userService.addUser(toSave);

		Collection<User> allUsers = userService.findAllUsers();

		assertThat(allUsers);
	}

}
