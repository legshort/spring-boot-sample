package boot.persistence.domains;

import boot.persistence.domains.User.Status;

public class UserFactory {

	public static final String NAME = "Jun";
	public static final String EMAIL = "test@mango4.me";
	public static final String PASSWORD = "password";

	public static User newInstance() {
		User user = new User();
		user.setEmail(EMAIL);
		user.setName(NAME);
		user.setPassword(PASSWORD);
		user.setStatus(Status.ACTIVATED);

		return user;
	}

}
