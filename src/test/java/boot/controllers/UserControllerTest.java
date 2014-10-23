package boot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import boot.configuration.OAuth2ServerConfiguration;
import boot.forms.UserSignUpForm;
import boot.forms.UserSignUpFormFactory;
import boot.forms.UserUpdateFormFactory;
import boot.persistence.domains.User;
import boot.persistence.domains.UserFactory;
import boot.services.UserService;
import boot.utils.HeaderUtil;
import boot.utils.StringUtil;
import boot.utils.TestUtil;

public class UserControllerTest extends BaseControllerTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testSignUp() throws Exception {
		// @formatter:off
		mockMvc.perform(
				post("/users")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(UserSignUpFormFactory.newInstance())))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(greaterThan(NumberUtils.INTEGER_ZERO))));
		// @formatter:on
	}

	@Test
	public void testSignUpWithBadRequest() throws Exception {
		UserSignUpForm userSignUpForm = UserSignUpFormFactory.newInstance();
		userSignUpForm.setEmail(StringUtil.WRONG_EMAIL_FORMAT);

		// @formatter:off
		mockMvc.perform(
				post("/users")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userSignUpForm)))
				.andExpect(status().isBadRequest());
		// @formatter:on
	}

	@Test
	public void testRequestAccessTokenWithBadRequest() throws Exception {
		signUpUser();

		// @formatter:off
		mockMvc.perform(
				post("/oauth/token")
				.header(HeaderUtil.AUTHORIZATION, HeaderUtil.AUTHORIZATION_VALUE)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isBadRequest());
		// @formatter:on
	}
	
	@Test
	public void testRequestAccessToken() throws Exception {
		signUpUser();
		
		// @formatter:off
		mockMvc.perform(
				post("/oauth/token")
				.header(HeaderUtil.AUTHORIZATION, HeaderUtil.AUTHORIZATION_VALUE)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", UserFactory.EMAIL)
				.param("password", UserFactory.PASSWORD)
				.param("grant_type", OAuth2ServerConfiguration.GRANT_TYPE)
				.param("scope", "read write")
				.param("client_id", OAuth2ServerConfiguration.CLIENT_ID)
				.param("client_secret", OAuth2ServerConfiguration.CLIENT_SECRET))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
				.andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
				.andExpect(jsonPath("$.scope", is(equalTo("read write"))));
		// @formatter:on
	}

	@Test
	public void testUpdateUser() throws Exception {
		User savedUser = signUpUser();
		
		// @formatter:off
		mockMvc.perform(
				put("/users/" + savedUser.getId())
				.header(HeaderUtil.AUTHORIZATION, getAuthorizationWithAccessToken())
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(UserUpdateFormFactory.newInstance())))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(greaterThan(NumberUtils.INTEGER_ZERO))))
				.andExpect(jsonPath("$.name", is(equalTo(StringUtil.NEW + UserFactory.NAME))));
		// @formatter:on
	}
	
	@Test
	public void testUpdateUserWithWrongUserId() throws Exception {
		signUpUser();
		String authorization = getAuthorizationWithAccessToken();
		
		// @formatter:off
		mockMvc.perform(
				put("/users/0")
				.header(HeaderUtil.AUTHORIZATION, authorization)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(UserUpdateFormFactory.newInstance())))
				.andExpect(status().isUnauthorized());
		// @formatter:on
	}

	@Test
	public void testUpdateUserWithoutAuthorization() throws Exception {
		User savedUser = signUpUser();

		// @formatter:off
		mockMvc.perform(
				put("/users/" + savedUser.getId())
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(UserUpdateFormFactory.newInstance())))
				.andExpect(status().isUnauthorized());
		// @formatter:on
	}

	@Test
	public void testDeleteUser() throws Exception {
		User savedUser = signUpUser();
		String authorization = getAuthorizationWithAccessToken();
		
		// @formatter:off
		mockMvc.perform(
				delete("/users/" + savedUser.getId())
				.header(HeaderUtil.AUTHORIZATION, authorization)
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isNoContent());
		// @formatter:on
	}

	@Test
	public void testDeleteUserWithoutAuthorization() throws Exception {
		User savedUser = signUpUser();

		// @formatter:off
		mockMvc.perform(
				delete("/users/" + savedUser.getId())
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isUnauthorized());
		// @formatter:on
	}
}
