package com.sicredi.assembleia.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.caelum.stella.validation.InvalidStateException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<Object> handleBusinessException(BusinessException ex) {
		ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(InvalidStateException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(InvalidStateException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "O cpf informado não é válido.", ex);
		return buildResponseEntity(apiError);
	} 	

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}