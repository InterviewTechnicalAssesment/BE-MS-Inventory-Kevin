package com.showcase.interview.msinventory.exception;

import org.springframework.http.HttpStatus;

public class CommitFailedException extends Exception {

	private HttpStatus status;
	private String message;

	public CommitFailedException() {
		status = HttpStatus.BAD_REQUEST;
		message = "Missing Fields or Wrong Data Type";
	}

	public CommitFailedException(String message) {
		status = HttpStatus.BAD_REQUEST;
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

	public void setMessage(String message) {
		this.message = message;
	}

}
