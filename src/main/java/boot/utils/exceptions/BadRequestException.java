package boot.utils.exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

	public BadRequestException(List<ObjectError> list) {
	}
}
