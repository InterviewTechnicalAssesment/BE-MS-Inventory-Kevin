package com.showcase.interview.msinventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.showcase.interview.msinventory.model.Order;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InventoryTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String baseUrl = "/api/v1/ms-inventory/inventory/";
	
	@Test
	public void formCustomerCRUDSeq() throws Exception {
		Order baseInventory = new Order();
		baseInventory.setPrice(BigDecimal.valueOf(50000));
		baseInventory.setCurrency("IDR");
		baseInventory.setQuantity(999);
		baseInventory.setProduct_name("Test Item");
		
//		Create Inventory
		Order insertedInventory = createInventoryShouldSuccess(baseInventory);
		
//		Read Inventory
		queryShouldReturnSpecificInventory(insertedInventory, baseInventory.getProduct_name());
		
//		Update Inventory
		insertedInventory.setProduct_name("Test Item Updated");
		updateInventoryProductNameShouldSuccess(insertedInventory, insertedInventory.getId());
		
		queryShouldReturnSpecificInventory(insertedInventory, insertedInventory.getProduct_name());
		
//		Delete Inventory
		deleteInventoryShouldSuccess(insertedInventory);
		
		
	}
	
	public Order createInventoryShouldSuccess(Order newInventory) throws Exception {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<Order> entity = new HttpEntity<>(newInventory, headers);

		// send POST request
		Order newInvResult = this.restTemplate.postForObject("http://localhost:" + port + baseUrl + "create",
				entity, Order.class);
		assertThat(newInvResult).isEqualToIgnoringGivenFields(newInventory, "id", "created_at", "updated_at");
		return newInvResult;

	}
	
	public void queryShouldReturnSpecificInventory(Order form, String productName) throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + baseUrl + form.getId() + "/detail",
				String.class)).contains(productName);
	}
	
	public void updateInventoryProductNameShouldSuccess(Order updatedInventory, Long insertedId) throws Exception {
		String url = "http://localhost:" + port + baseUrl + insertedId + "/update-data";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(updatedInventory);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		assertThat(response.getBody()).contains(updatedInventory.getProduct_name());
	}
	
	public void deleteInventoryShouldSuccess(Order inventory) throws Exception {

		String url = "http://localhost:" + port + baseUrl + inventory.getId() + "/delete-data";
		HttpEntity<?> requestEntity = null;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

		assertThat(response.getBody()).contains("Success");

	}
	
	@Test
	public void queryShouldReturnListInventory() throws Exception {
		assertThat(
				this.restTemplate.getForObject("http://localhost:" + port + baseUrl +"all", String.class))
						.contains("Baju");
	}
	
}