package boot.persistence.domains;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import boot.persistence.domains.User.Status;

@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails {

	private User user;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialNonExpired;

	public UserDetailsImpl(User savedUser) {
		this.user = savedUser;
		this.authorities = AuthorityUtils.createAuthorityList("USER");
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialNonExpired = true;
	}
		
	public User getUser() {
		return user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return (Status.ACTIVATED == user.getStatus()) ? true : false; 
	}

}
