package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An entity (or dao) that maps to the table 'foodRating' in the Database. This represents the model of the object.
 */
@Entity
@Table(name="foodRating")
public class FoodRating {
	
	/**
	 * Unique id to find rating
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rating_id")
	private int rating_id;
	
	/**
	 * User that rated this food
	 */
	@Column(name="user_email")
	private String user_email; 
	
	/**
	 * Food id of the food being rated
	 */
	@Column(name="fid")
	private int fid; 
	
	/**
	 * The rating given.
	 */
	@Column(name="rating")
	private int rating;

	public FoodRating() {
		super();
	}

	public FoodRating(int rating_id, String user_email, int fid, int rating) {
		super();
		this.rating_id = rating_id;
		this.user_email = user_email;
		this.fid = fid;
		this.rating = rating;
	}
	
	public int getRating_id() {
		return rating_id;
	}
	
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}