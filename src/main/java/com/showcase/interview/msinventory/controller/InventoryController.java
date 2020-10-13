package com.showcase.interview.msinventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.showcase.interview.msinventory.exception.CommitFailedException;
import com.showcase.interview.msinventory.exception.DataNotFoundException;
import com.showcase.interview.msinventory.exception.UndefinedException;
import com.showcase.interview.msinventory.model.Order;
import com.showcase.interview.msinventory.service.InventoryService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/ms-inventory/inventory", produces = { "application/json" })
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/create")
	public @ResponseBody Order createNew(@RequestBody Order newInventory) {
		try {
			return inventoryService.createNew(newInventory);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@GetMapping("/{id}/detail")
	public @ResponseBody Order findById(@PathVariable Long id) {
		try {
			return inventoryService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Order updateData(@RequestBody Order newInventory, @PathVariable Long id) {
		return inventoryService.updateById(newInventory, id);
	}
	
	@PostMapping("/{id}/reserve-data")
	public @ResponseBody Order reservedData(@RequestBody Order newInventory, @PathVariable Long id) {
		return inventoryService.reserveById(newInventory, id);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Order> getAll() {
		return inventoryService.getAll();

	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return inventoryService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

}