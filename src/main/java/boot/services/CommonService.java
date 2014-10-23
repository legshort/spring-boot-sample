package boot.services;

import boot.persistence.domains.User;

public interface CommonService<T> {
	Long count();
	
	boolean checkOwnership(User userFromToken, Long resourceId);
}
