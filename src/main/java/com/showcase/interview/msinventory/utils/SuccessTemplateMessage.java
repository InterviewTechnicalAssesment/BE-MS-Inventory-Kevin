package com.showcase.interview.msinventory.utils;

import org.springframework.http.HttpStatus;

public class SuccessTemplateMessage {
	private String message;
	private HttpStatus status;

	public SuccessTemplateMessage() {
		super();
		this.message = "Success";
		this.status = HttpStatus.OK;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
