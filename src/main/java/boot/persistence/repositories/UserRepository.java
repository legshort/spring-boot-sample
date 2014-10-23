package boot.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import boot.persistence.domains.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);
}
