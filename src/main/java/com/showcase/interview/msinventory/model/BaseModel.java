package com.showcase.interview.msinventory.model;

import java.time.ZonedDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	protected ZonedDateTime created_at;
	protected ZonedDateTime updated_at;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.created_at = created_at;
	}

	public ZonedDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(ZonedDateTime updated_at) {
		this.updated_at = updated_at;
	}
	

}
