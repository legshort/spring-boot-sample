package boot.forms;

import lombok.Data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import boot.persistence.domains.User;
import cz.jirutka.validator.spring.SpELAssert;

@Data
@SpELAssert(value = "password.equals(passwordConfirm)", applyIf = "true")
public class UserSignUpForm {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Length(min = 3)
	private String name;

	@NotBlank
	@Length(min = 6)
	private String password;

	@NotBlank
	private String passwordConfirm;

	public User createUser() {
		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);

		return user;
	}

}
