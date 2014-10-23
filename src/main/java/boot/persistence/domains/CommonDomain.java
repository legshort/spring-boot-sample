package boot.persistence.domains;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CommonDomain {
	private LocalDate createdDateTime;
	private LocalDate updatedDateTime;
	private LocalDate deletedDateTime;
}
