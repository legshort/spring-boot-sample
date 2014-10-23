package boot.persistence.domains;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@JsonProperty("expires_in")
	private int expiresIn;
	
	private String scope;
}
