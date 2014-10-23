package boot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boot.persistence.domains.User;
import boot.persistence.repositories.UserRepository;
import boot.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Override
	@Transactional
	public User signUp(User requestedUser) {
		User savedUser = userRepository.save(requestedUser);

		return savedUser;
	}

	@Override
	@Transactional(readOnly = true)
	public User signIn(User requestedUser) {
		User savedUser = userRepository.findByEmailAndPassword(
				requestedUser.getEmail(), requestedUser.getPassword());

		return savedUser;
	}

	@Override
	@Transactional
	public User updateUser(User requestedUser) {
		User updatedUser = userRepository.save(requestedUser);

		return updatedUser;
	}

	@Override
	@Transactional
	public void deleteUser(User requestedUser) {
		userRepository.delete(requestedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByEmail(User requestedUser) {
		User savedUser = userRepository.findByEmail(requestedUser.getEmail());
		
		if (null == savedUser) {
			throw new UsernameNotFoundException("Invalid username/password");
		}
		
		return savedUser;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean checkOwnership(User userFromToken, Long resourceId) {
		User requtedUser = userRepository.findOne(resourceId);
		
		if (null == requtedUser) {
			return false;
		}
		
		return userFromToken.getId() == requtedUser.getId();
	}

}
