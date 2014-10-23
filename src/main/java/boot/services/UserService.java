package boot.services;

import boot.persistence.domains.User;

public interface UserService extends CommonService<User> {
	User signUp(User requestedUser);

	User signIn(User requestedUser);

	User updateUser(User requestedUser);

	void deleteUser(User requestedUser);
	
	User findByEmail(User requestedUser);

}
