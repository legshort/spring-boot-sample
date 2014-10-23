package boot.persistence.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
public class User extends CommonDomain {
	
	public enum Role {
		ADMIN, ADVERTISER, MANAGER, USER
	}
	
	public enum Status {
		ACTIVATED, BANNED, UNACTIVATED, UNVALIDATED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email
	private String email;

	private String name;
	private String password;
	private Role role;
	private Status status;

	public User(Long userId) {
		this.id = userId;
	}
}
