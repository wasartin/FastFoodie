package com.example.business.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant")
public class Restaurant {

	//TODO this should be an int
	@Id
	@Column(name="restaurant_id")
	private int restaurant_id;
	@Column(name="restaurant_name")
	private String restaurant_name;
	@Column(name="last_updated")
	private Timestamp last_updated;
	
	public Restaurant(){
		super();
	}
	
	/**
	 * restaurant constructor
	 * @param id
	 * @param name
	 * @param last_updated
	 */
	public Restaurant(int id, String name, Timestamp last_updated) {
		this.restaurant_id = id;
		this.restaurant_name = name;
		this.last_updated = last_updated;
	}

	/**
	 * restaurant id getter
	 * @return int id
	 */
	public int getRestaurant_id() {
		return restaurant_id;
	}

	/**
	 * id setter
	 * @param restaurant_id
	 */
	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	/**
	 * name getter
	 * @return restaurant name
	 */
	public String getRestaurant_name() {
		return restaurant_name;
	}

	/**
	 * name getter 
	 * @param restaurant_name
	 */
	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	/**
	 * time getter
	 * @return Timestamp last updated
	 */
	public Timestamp getLast_updated() {
		return last_updated;
	}

	/**
	 * time setter
	 * @param last_updated (A timestamp)
	 */
	public void setLast_updated(Timestamp last_updated) {
		this.last_updated = last_updated;
	}
}