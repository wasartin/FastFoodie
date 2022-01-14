package com.example.business.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An entity (or dao) that maps to the table 'resturant' in the Database. This represents the model of the object.
 */
@Entity
@Table(name="restaurant")
public class Restaurant {

	/**
	 * Unique id to find this restaurant.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="restaurant_id")
	private int restaurant_id;
	
	/**
	 * The name of the restaurant
	 */
	@Column(name="restaurant_name")
	private String restaurant_name;
	
	/**
	 * The last time anything with this restaurant was updated
	 */
	@Column(name="last_updated")
	private Timestamp last_updated;
	
	public Restaurant(){
		super();
	}
	
	public Restaurant(int id, String name, Timestamp last_updated) {
		this.restaurant_id = id;
		this.restaurant_name = name;
		this.last_updated = last_updated;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public Timestamp getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(Timestamp last_updated) {
		this.last_updated = last_updated;
	}
}