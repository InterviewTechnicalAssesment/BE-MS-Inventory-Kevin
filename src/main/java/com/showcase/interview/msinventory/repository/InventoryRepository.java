package com.showcase.interview.msinventory.repository;

import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.msinventory.model.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

}
