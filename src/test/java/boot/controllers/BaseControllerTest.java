package boot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import boot.configuration.OAuth2ServerConfiguration;
import boot.persistence.domains.TokenResponse;
import boot.persistence.domains.UserFactory;
import boot.services.BaseTest;
import boot.utils.HeaderUtil;

@WebAppConfiguration
public abstract class BaseControllerTest extends BaseTest {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy filterChainProxy;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(filterChainProxy).build();
	}
	
	protected String getAuthorizationWithAccessToken() throws Exception {
		// @formatter:off
		String content = mockMvc.perform(
				post("/oauth/token")
				.header(HeaderUtil.AUTHORIZATION, HeaderUtil.AUTHORIZATION_VALUE)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", UserFactory.EMAIL)
				.param("password", UserFactory.PASSWORD)
				.param("grant_type", OAuth2ServerConfiguration.GRANT_TYPE)
				.param("scope", "read write")
				.param("client_id", OAuth2ServerConfiguration.CLIENT_ID)
				.param("client_secret", OAuth2ServerConfiguration.CLIENT_SECRET)).andReturn().getResponse().getContentAsString();
		// @formatter:on
		TokenResponse tokenResponse = new ObjectMapper().readValue(content, TokenResponse.class);
		 
		return "Bearer " + tokenResponse.getAccessToken();
	}
}
