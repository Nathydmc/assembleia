package com.sicredi.assembleia.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6311855103089767472L;

	private String message;

	private HttpStatus status;

	public BusinessException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}

}
