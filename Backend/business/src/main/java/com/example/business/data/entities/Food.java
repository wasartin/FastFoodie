package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="food")
public class Food {
	
	@Id
	@Column(name="food_id")
	private int food_id; 
	
	@Column(name="food_name")
	private String food_name; 
	
	@Column(name="protein_total")
	private int protein_total;
	
	@Column(name="carb_total")
	private int carb_total;
	
	@Column(name="fat_total")
	private int fat_total;
	
	@Column(name="calorie_total")
	private int calorie_total;
	
	@Column(name="price")
	private String price;
	
	@Column(name="category")
	private String category;
	
	@Column (name="located_at")
	private int located_at;
	
	@Column(name = "rating")
	private double rating;
	
	public Food() {
		super();
	}

	/**food constructor
	 * @param food_id
	 * @param food_name
	 * @param protein_total
	 * @param carb_total
	 * @param fat_total
	 * @param calorie_total
	 * @param price
	 * @param category
	 * @param located_at
	 * @param rating
	 */
	public Food(int food_id, String food_name, int protein_total, int carb_total, int fat_total, int calorie_total,
			String price, String category, int located_at, double rating) {
		super();
		this.food_id = food_id;
		this.food_name = food_name;
		this.protein_total = protein_total;
		this.carb_total = carb_total;
		this.fat_total = fat_total;
		this.calorie_total = calorie_total;
		this.price = price;
		this.category = category;
		this.located_at = located_at;
		this.rating = rating;
	}
	
	/**
	 * located at getter
	 * @return int for restaurant id
	 */
	public int getLocated_at() {
		return located_at;
	}

	/**
	 * located at setter
	 * @param located_at
	 */
	public void setLocated_at(int located_at) {
		this.located_at = located_at;
	}
	
	/**
	 * food id getter
	 * @return food id
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
	 * food name getter
	 * @return string of food name
	 */
	public String getFood_name() {
		return food_name;
	}

	/**
	 * food name setter
	 * @param food_name
	 */
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	/**
	 * get protein
	 * @return int protein
	 */
	public int getProtein_total() {
		return protein_total;
	}

	/**
	 * protein setter
	 * @param protein_total
	 */
	public void setProtein_total(int protein_total) {
		this.protein_total = protein_total;
	}

	/**
	 * carb total getter
	 * @return carbs
	 */
	public int getCarb_total() {
		return carb_total;
	}

	/**
	 * carb setter
	 * @param carb_total
	 */
	public void setCarb_total(int carb_total) {
		this.carb_total = carb_total;
	}

	/**
	 * fat getter
	 * @return int of total fat
	 */
	public int getFat_total() {
		return fat_total;
	}

	/**
	 * total fat setter
	 * @param fat_total
	 */
	public void setFat_total(int fat_total) {
		this.fat_total = fat_total;
	}

	/**
	 * calorie getter
	 * @return calaories
	 */
	public int getCalorie_total() {
		return calorie_total;
	}

	/**
	 * calorie setter
	 * @param calorie_total
	 */
	public void setCalorie_total(int calorie_total) {
		this.calorie_total = calorie_total;
	}

	/**
	 * get price
	 * @return price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * price setter
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * category getter
	 * @return string calorie
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * catagory setter
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * rating getter
	 * @return double for rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * rating setter
	 * @param rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

}