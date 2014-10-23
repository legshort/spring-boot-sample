package boot.forms;

import boot.forms.UserUpdateForm;
import boot.persistence.domains.UserFactory;
import boot.utils.StringUtil;

public class UserUpdateFormFactory {

	public static UserUpdateForm newInstance() {
		UserUpdateForm userUpdateForm = new UserUpdateForm();
		userUpdateForm.setName(StringUtil.NEW + UserFactory.NAME);
		
		return userUpdateForm;
	}

}
