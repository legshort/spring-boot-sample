package boot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import boot.utils.exceptions.BadRequestException;
import boot.utils.exceptions.InternalServerErrorException;
import boot.utils.exceptions.UnauthorizedException;

@ControllerAdvice
public class ControllerException {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public void handleBindingResultError() {
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public void handleUnauthorized() {
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerErrorException.class)
	public void handleInternalServerError() {
	}

}
