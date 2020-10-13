package com.showcase.interview.msinventory.controller;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.interview.msinventory.model.Order;

@Component
public class ListenerController {
	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	private InventoryController inventoryController;

	@RabbitListener(queues = "ms-inventory-queue")
	public void receiveMessage(Order newInventory) throws IOException {
		if (newInventory == null) {
			throw new AmqpRejectAndDontRequeueException("Received message is null");
		}
		inventoryController.reservedData(newInventory, newInventory.getId());
	}

}

