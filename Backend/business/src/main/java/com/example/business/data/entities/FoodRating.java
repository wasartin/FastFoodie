package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodRating")
public class FoodRating {
	
	@Id
	@Column(name="rating_id")
	private int rating_id;
	
	@Column(name="user_email")
	private String user_email; 
	
	@Column(name="food_id")
	private int food_id; 
	
	@Column(name="rating")
	private int rating;

	public FoodRating() {
		super();
	}

	/**
	 * rating constructor
	 * @param rating_id
	 * @param user_email
	 * @param food_id
	 * @param rating
	 */
	public FoodRating(int rating_id, String user_email, int food_id, int rating) {
		super();
		this.rating_id = rating_id;
		this.user_email = user_email;
		this.food_id = food_id;
		this.rating = rating;
	}
	
	/**
	 * get rating's id
	 * @return id
	 */
	public int getRating_id() {
		return rating_id;
	}
	
	/**
	 * rating setter
	 * @param rating_id
	 */
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}
	/**
	 * user email getter
	 * @return string
	 */
	public String getUser_email() {
		return user_email;
	}

	/**
	 * user email setter
	 * @param user_email
	 */
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	/**
	 * food id getter
	 * @return int
	 */
	public int getFood_id() {
		return food_id;
	}
	/**
	 * food id setter
	 * @param food_id
	 */
	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	/**
	 * rating getter
	 * @return int rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * rating setter
	 * @param rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
}