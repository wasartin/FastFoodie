package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Food;
import com.example.business.data.entities.FoodRating;
import com.example.business.data.services.FoodRatingsService;
import com.example.business.data.services.FoodService;

/**
 *  A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the FoodRating repository.
 */
@RestController
@RequestMapping(value="/foodRatings")
public class FoodRatingController {

	@Autowired
	FoodRatingsService foodRatingService;
	
	
	@Autowired
	FoodService foodServ;

	/**
	 * Uses Repo class to return a rating in the DB
	 * @return aFood Rating
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{rating_id}")
	@ResponseBody
	public Optional<FoodRating> getSpecific(@PathVariable int rating_id){
		return foodRatingService.getEntityByID(rating_id);
	}
	
	/**
	 * Uses Repo class to return all ratings for the foods in the DB
	 * @return list of all Food Ratings
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/all")
	@ResponseBody
	public Iterable<FoodRating> getAll(){
		return foodRatingService.getAllEntities();
	}
	
	/**
	 * retrieves a specific food rating with that rating's user email and food id
	 * @param user_email
	 * @param food_id
	 * @return Specific food rating
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/{food_id}")
	@ResponseBody
	public FoodRating getSpecific(@PathVariable String user_email, @PathVariable int food_id){
		return foodRatingService.getRatingForUserAndFood(user_email, food_id);
	}
	
	/**
	 * gets all food ratings for a specific user
	 * @param user_email
	 * @return list of all user's food ratings
	 */
	@RequestMapping(method = RequestMethod.GET, path = "all/{user_email}")
	@ResponseBody
	public List<FoodRating> getAllForUser(@PathVariable String user_email){
		return foodRatingService.getAllRatingsByUser(user_email);
	}
	
	/**
	 * get all food ratings for a specific food
	 * @param food_id
	 * @return list of food ratings
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/all/{food_id}")
	@ResponseBody
	public List<Integer> getFoodRatingList(@PathVariable int food_id){
		return foodRatingService.findAllRatingsForFood(food_id);
	}
	
	/**
	 * gets the average food ratings for a food, a great method for a lazy front end
	 * @param food_id
	 * @return food's rating
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/average/{food_id}")
	@ResponseBody
	public double getFoodRating(@PathVariable int food_id){
		List<Integer> ratingList = foodRatingService.findAllRatingsForFood(food_id);
		 double sum = 0;
		  if(!ratingList.isEmpty()) {
		    for (int curr : ratingList) {
		    	if (curr > 0 && curr < 6) {
				       sum += curr;
		    	}
	
		    }
		  }
		    return sum / ratingList.size();
	}
	
	/**
	 * create new food rating
	 * @param newRating
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFoodRatingByJSON(@RequestBody FoodRating newRating) {
		return foodRatingService.createEntity(newRating, newRating.getRating_id());
	}
	
	/**
	 * creates a new food rating by email, food id, and rating
	 * @param user_email
	 * @param food_id
	 * @param rating
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
		FoodRating newRating = new FoodRating();
		newRating.setFid(food_id);
		newRating.setRating(rating);
		newRating.setUser_email(user_email);
		ResponseEntity<?> result = foodRatingService.createEntity(newRating, newRating.getRating_id());
		
		//get food and reset ratings.
		Food temp = foodServ.getEntityByID(food_id).get();
		if(temp.getRating() < 1) {
			temp.setRating(1);
			temp.setRated(temp.getRated() + 1);
		}else {
			temp.setRated(temp.getRated() + 1);
			Double oldRating = temp.getRating();
			int numRated = temp.getRated();
			Double something = oldRating / numRated;
			temp.setRating(something);
		}
		foodServ.editEntity(temp, food_id);
		
		return result;
	}
	
	/**
	 * edits an existing food rating
	 * @param user_email
	 * @param food_id
	 * @param rating
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> editFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
		FoodRating toEdit = foodRatingService.getRatingForUserAndFood(user_email, food_id);
		toEdit.setRating(rating);
		return foodRatingService.editEntity(toEdit, toEdit.getRating_id());
	}
	
	/**
	 * deletes an existing food rating
	 * @param user_email
	 * @param food_id
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?>  deleteFood(@PathVariable String user_email, @PathVariable int food_id) {
		FoodRating toDelete = foodRatingService.getRatingForUserAndFood(user_email, food_id);
		return foodRatingService.deleteEntityById(toDelete.getRating_id());
	}
	
}