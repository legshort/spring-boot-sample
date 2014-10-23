package boot.services;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import boot.persistence.domains.User;
import boot.persistence.domains.UserFactory;
import boot.utils.StringUtil;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Test
	public void testSignUp() {
		User savedUser = signUpUser();

		// @formatter:off
		assertThat("UserCount", userService.count(), is(equalTo(NumberUtils.LONG_ONE)));
		assertThat("UserEmail", savedUser.getEmail(), is(equalTo(UserFactory.EMAIL)));
		assertThat("UserName", savedUser.getName(), is(equalTo(UserFactory.NAME)));
		// @formatter:on
	}

	@Test
	public void testSignIn() {
		User requestedUser = signUpUser();
		User savedUser = userService.signIn(requestedUser);

		// @formatter:off
		assertThat("UserCount", userService.count(), is(equalTo(NumberUtils.LONG_ONE)));
		assertThat("UserEmail", savedUser.getEmail(), is(equalTo(UserFactory.EMAIL)));
		assertThat("UserName", savedUser.getName(), is(equalTo(UserFactory.NAME)));
		// @formatter:on
	}

	@Test
	public void testUpdateUser() {
		User requestedUser = signUpUser();
		requestedUser.setName(StringUtil.NEW + requestedUser.getName());

		User savedUser = userService.updateUser(requestedUser);

		// @formatter:off
		assertThat("UserCount", userService.count(), is(equalTo(NumberUtils.LONG_ONE)));
		assertThat("UserEmail", savedUser.getEmail(), is(equalTo(UserFactory.EMAIL)));
		assertThat("UserName", savedUser.getName(), is(equalTo(StringUtil.NEW + UserFactory.NAME)));
		// @formatter:on
	}

	@Test
	public void testDeleteUser() {
		User savedUser = signUpUser();
		userService.deleteUser(savedUser);

		// @formatter:off
		assertThat("UserCount", userService.count(), is(equalTo(NumberUtils.LONG_ZERO)));
		// @formatter:on
	}
}
