package com.showcase.interview.msinventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.showcase.interview.msinventory.exception.CommitFailedException;
import com.showcase.interview.msinventory.exception.DataNotFoundException;
import com.showcase.interview.msinventory.exception.UndefinedException;
import com.showcase.interview.msinventory.model.Order;
import com.showcase.interview.msinventory.repository.InventoryRepository;
import com.showcase.interview.msinventory.utils.SuccessTemplateMessage;
import com.showcase.interview.msinventory.utils.Util;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private Util util;

	public Iterable<Order> getAll() {
		return inventoryRepository.findAll();
	}

	public Order createNew(Order newData) throws CommitFailedException, UndefinedException {
		try {
			newData.setCreated_at(util.getTimeNow());
			newData.setUpdated_at(util.getTimeNow());
			return inventoryRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public Order getById(long id) throws DataNotFoundException {
		return inventoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public Order updateById(Order updatedData, Long id) {

		return inventoryRepository.findById(id).map(data -> {
			updatedData.setId(id);
			updatedData.setCreated_at(data.getCreated_at());
			data = updatedData;

			data.setUpdated_at(util.getTimeNow());
			return inventoryRepository.save(data);
		}).orElseGet(() -> {
			updatedData.setCreated_at(util.getTimeNow());
			updatedData.setUpdated_at(util.getTimeNow());
			return inventoryRepository.save(updatedData);
		});
	}
	
	public Order reserveById(Order updatedData, Long id) {

		return inventoryRepository.findById(id).map(data -> {
			int newQty = data.getQuantity()-updatedData.getQuantity();
			data.setQuantity(newQty);
			data.setUpdated_at(util.getTimeNow());
			return inventoryRepository.save(data);
		}).orElseGet(() -> {
			updatedData.setCreated_at(util.getTimeNow());
			updatedData.setUpdated_at(util.getTimeNow());
			return inventoryRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (inventoryRepository.findById(id) != null) {
				inventoryRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

}