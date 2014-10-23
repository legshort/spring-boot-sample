package boot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import boot.persistence.domains.User;
import boot.persistence.domains.UserDetailsImpl;
import boot.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {

		User requestedUser = new User();
		requestedUser.setEmail(email);

		User savedUser = userService.findByEmail(requestedUser);

		return new UserDetailsImpl(savedUser);
	}

}
