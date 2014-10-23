package boot.forms;

import boot.persistence.domains.UserFactory;

public class UserSignUpFormFactory {

	public static UserSignUpForm newInstance() {
		UserSignUpForm userSignUpForm = new UserSignUpForm();
		userSignUpForm.setEmail(UserFactory.EMAIL);
		userSignUpForm.setName(UserFactory.NAME);
		userSignUpForm.setPassword(UserFactory.PASSWORD);
		userSignUpForm.setPasswordConfirm(UserFactory.PASSWORD);
		
		return userSignUpForm;
	}
}
