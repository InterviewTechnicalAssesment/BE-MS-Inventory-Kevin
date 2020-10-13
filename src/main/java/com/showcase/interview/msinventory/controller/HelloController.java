package com.showcase.interview.msinventory.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ms-inventory/")
public class HelloController {

	@Value("${message:Hello default}")
	private String message;

	@GetMapping("/hello")
	public String hello() {
		return message;
	}

	@Value("${timezone:Asia/Jakarta}")
	private String timezone;

	@GetMapping("/timezone")
	public String tz() {
		return timezone;
	}

}