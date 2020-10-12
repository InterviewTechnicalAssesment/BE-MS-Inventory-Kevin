package com.showcase.interview.msinventory.exception;

import org.springframework.http.HttpStatus;

public class UndefinedException extends Exception {

	private HttpStatus status;
	private String message;

	public UndefinedException(String message) {
		status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.message = message;
	}

	private static final long serialVersionUID = 1L;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String reason) {
		this.message = reason;
	}

}
