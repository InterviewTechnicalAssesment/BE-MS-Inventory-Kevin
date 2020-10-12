package com.showcase.interview.msinventory.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.showcase.interview.msinventory.MsInventoryApplication;
import com.showcase.interview.msinventory.utils.SuccessTemplateMessage;

@RestController
@RequestMapping("/api/v1/ms-inventory/")
public class HelloController {

	@Value("${message:Hello default}")
	private String message;

	@Autowired
	private final RabbitTemplate rabbitTemplate;

	HelloController() {
		this.rabbitTemplate = new RabbitTemplate();
	}

	@GetMapping("/send-message")
	public ResponseEntity<Object> SendMessage() {
		this.rabbitTemplate.convertAndSend(MsInventoryApplication.topicExchangeName, MsInventoryApplication.routingKey, "Hello from RabbitMQ!");
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

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