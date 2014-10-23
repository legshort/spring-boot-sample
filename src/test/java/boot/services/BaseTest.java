package boot.services;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import boot.Application;
import boot.configuration.OAuth2ServerConfiguration;
import boot.configuration.WebSecurityConfiguration;
import boot.persistence.domains.User;
import boot.persistence.domains.UserFactory;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class,
		OAuth2ServerConfiguration.class, WebSecurityConfiguration.class })
@Transactional
@TransactionConfiguration
public abstract class BaseTest {

	@Autowired
	private UserService userService;

	protected User signUpUser() {
		return userService.signUp(UserFactory.newInstance());
	}

}
