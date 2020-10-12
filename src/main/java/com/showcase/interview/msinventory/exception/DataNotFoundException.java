package com.showcase.interview.msinventory.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends Exception {

	private HttpStatus status;
	private String message;

	public DataNotFoundException() {
		status = HttpStatus.NOT_FOUND;
		message = "No Matching Record was found";
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
