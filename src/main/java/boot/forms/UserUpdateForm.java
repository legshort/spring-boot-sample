package boot.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import boot.persistence.domains.User;
import lombok.Data;

@Data
public class UserUpdateForm {

	@NotBlank
	@Length(min = 3)
	private String name;

	public User createUser(Long userId) {
		User user = new User();
		user.setId(userId);
		user.setName(name);
		
		return user;		
	}
}
