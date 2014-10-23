package boot.controllers;

import javax.validation.Valid;

import lombok.Getter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boot.forms.UserSignUpForm;
import boot.forms.UserUpdateForm;
import boot.persistence.domains.User;
import boot.persistence.domains.UserDetailsImpl;
import boot.services.UserService;
import boot.utils.annotations.BindingResultError;
import boot.utils.annotations.ResourceOnwer;
import boot.utils.annotations.TryAndCatchInternalServerError;

@RestController
@TryAndCatchInternalServerError
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserController {

	private static Log logger = LogFactory.getLog(UserController.class);

	@Autowired
	@Getter
	private UserService userService;

	@BindingResultError
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity signUp(
			@Valid @RequestBody UserSignUpForm userSignUpForm,
			BindingResult bindingResult) {
		logger.info("RequestUserForm: " + userSignUpForm);
		
		User savedUser = userService.signUp(userSignUpForm.createUser());

		return new ResponseEntity(savedUser, HttpStatus.CREATED);
	}

	@BindingResultError
	@ResourceOnwer
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{userId}")
	public ResponseEntity updateUser(@PathVariable Long userId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@Valid @RequestBody UserUpdateForm userUpdateForm,
			BindingResult bindingResult) {
		logger.info("UserUpdate: " + userUpdateForm);

		User updatedUser = userService.updateUser(userUpdateForm
				.createUser(userId));

		return new ResponseEntity(updatedUser, HttpStatus.OK);
	}

	@ResourceOnwer
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
	public ResponseEntity deleteUser(@PathVariable Long userId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		logger.info("UserDelete: " + userId);

		User requestedUser = new User(userId);
		userService.deleteUser(requestedUser);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}