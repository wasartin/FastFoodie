package com.example.business.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 * An entity (or dao) that maps to the table 'food' in the Database. This represents the model of the object.
 */
@Entity
@Table(name="food")
public class Food {
	
	/**
	 * Each food is uniquely identified by it's id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fid")
	private Integer fid; 
	
	/**
	 * For displaying an easy to understand name to the user.
	 */
	@Column(name="fname")
	private String fname; 
	
	/**
	 * Grams of protein
	 */
	@Digits(fraction = 0, integer = 10)
	@Column(name="protein")
	private int protein;
	
	/**
	 * Grams of carbs
	 */
	@Column(name="carb")
	private int carb;
	
	/**
	 * Grams of fat
	 */
	@Column(name="fat")
	private int fat;
	
	/**
	 * Total number of calories
	 */
	@Column(name="calorie")
	private int calorie;
	
	/**
	 * Price in dollars
	 */
	@Column(name="price")
	private Double price;
	
	/**
	 * A restaurant specific category.
	 */
	@Column(name="category")
	private String category;
	
	/**
	 * The id of the restaurant it is located at.
	 */
	@Column (name="located")
	private int located;
	
	/**
	 * Average rating of food.
	 */
	@Column(name = "rating")
	private double rating;
	
	/**
	 * The number of times this food has been rated.
	 */
	@Column(name = "rated")
	private int rated;
	
	public Food() {
		super();
	}

	public Food(Integer fid, String fname, @Digits(fraction = 0, integer = 10) int protein, int carb, int fat,
			int calorie, Double price, String category, int located, double rating, int rated) {
		super();
		this.fid = fid;
		this.fname = fname;
		this.protein = protein;
		this.carb = carb;
		this.fat = fat;
		this.calorie = calorie;
		this.price = price;
		this.category = category;
		this.located = located;
		this.rating = rating;
		this.rated = rated;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public int getCarb() {
		return carb;
	}

	public void setCarb(int carb) {
		this.carb = carb;
	}

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getLocated() {
		return located;
	}

	public void setLocated(int located) {
		this.located = located;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getRated() {
		return rated;
	}

	public void setRated(int rated) {
		this.rated = rated;
	}

}