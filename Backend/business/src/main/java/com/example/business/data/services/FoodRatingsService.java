package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

/**
 * The foodRating service class is where the bulk of the business logic is. 
 * This is the layer that will interface with its repository.
 */
@Service
public class FoodRatingsService extends AbstractService<FoodRating, Integer>{
	@Autowired
	FoodRatingRepository repo;
	
	/**
	 * This function uses the foodrating repo to get all the ratings a specific user has made
	 * @param user_email
	 * @return list of food ratings by user
	 */
	public List<FoodRating> getAllRatingsByUser(String user_email){
		return repo.getFoodRatingsForUser(user_email);
	}
	
	/**
	 * This functions uses the Food rating repo to get a specific food rating by a user and food id.
	 * Generally used for either deleting or editing a previous rating.
	 * @param user_email
	 * @param food_id
	 * @return a specific food rating.
	 */
	public FoodRating getRatingForUserAndFood(String user_email, int fid){
		return repo.getFoodRatingByUserAndFood(user_email, fid);
	}
	
	/**
	 * This class gets all the food ratings for a specific user so that front end may
	 * find the average if needed.
	 * @param food_id
	 * @return List of all rating values for a specific food.
	 */
	public List<Integer> findAllRatingsForFood(int food_id){
		return repo.findAllRatingsForFood(food_id);
	}
	
	
}
