package com.example.business.data.controllers;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

/**
 * 
 * @author Will and Jon
 *
 */
@RestController
@RequestMapping(value="/foodRatings")
public class FoodRatingController {

		@Autowired
		FoodRatingRepository foodRatingRepo;
		
		/**
		 * Uses Repo class to return all ratings for the foods in the DB
		 * @return list of all Food Ratings
		 */
		@RequestMapping(method = RequestMethod.GET, path = "/all")
		@ResponseBody
		public List<FoodRating> getAll(){
			return (List<FoodRating>) foodRatingRepo.findAll();
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
			return foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
		}
		
		/**
		 * gets all food ratings for a specific user
		 * @param user_email
		 * @return list of all user's food ratings
		 */
		@RequestMapping(method = RequestMethod.GET, path = "all/user/{user_email}")
		@ResponseBody
		public List<FoodRating> getAllForUser(@PathVariable String user_email){
			return (List<FoodRating>) foodRatingRepo.getFoodRatingsForUser(user_email);
		}
		
		/**
		 * get all food ratings for a specific food
		 * @param food_id
		 * @return list of food ratings
		 */
		@RequestMapping(method = RequestMethod.GET, path = "/all/food/{food_id}")
		@ResponseBody
		public List<Integer> getFoodRatingList(@PathVariable int food_id){
			return foodRatingRepo.findAllRatingsForFood(food_id);
		}
		
		/**
		 * gets the average food ratings for a food, a great method for a lazy front end
		 * @param food_id
		 * @return food's rating
		 */
		@RequestMapping(method = RequestMethod.GET, path = "/average/{food_id}")
		@ResponseBody
		public double getFoodRating(@PathVariable int food_id){
			List<Integer> ratingList = foodRatingRepo.findAllRatingsForFood(food_id);
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
		 * @return a json object response
		 */
		@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createFoodRatingByJSON(@RequestBody FoodRating newRating) {
			JSONObject response;
			try {
				if(foodRatingRepo.existsById(newRating.getRating_id())) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.save(newRating);
				response = generateResponse(204, HttpStatus.OK, "Food has bee rated");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		/**
		 * creates a new food rating by email, food id, and rating
		 * @param user_email
		 * @param food_id
		 * @param rating
		 * @return a json object response
		 */
		@RequestMapping(method = RequestMethod.POST, path = "/create/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
			if(rating < 0 || rating > 5) {
				throw new IllegalArgumentException();
			}
			FoodRating newRating = new FoodRating();
			newRating.setFood_id(food_id);
			newRating.setRating(rating);
			newRating.setUser_email(user_email);
			JSONObject response;
			try {
				if(foodRatingRepo.getFoodRatingByUserAndFood(newRating.getUser_email(), newRating.getFood_id()) != null) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.save(newRating);
				response = generateResponse(204, HttpStatus.OK, "Food has been rated");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		/**
		 * edits an existing food rating
		 * @param user_email
		 * @param food_id
		 * @param rating
		 * @return a json object response
		 */
		@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject editFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
			if(rating < 0 || rating > 5) {
				throw new IllegalArgumentException();
			}
			FoodRating findOldVersion = foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
				if(findOldVersion == null) {
					throw new IllegalArgumentException();
				}
				findOldVersion.setRating(rating);
				foodRatingRepo.save(findOldVersion);
				response = generateResponse(204, HttpStatus.OK, "Food rating has been edited");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		/**
		 * deletes an existing food rating
		 * @param user_email
		 * @param food_id
		 * @return a json object response
		 */
		@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
		@ResponseBody
		public JSONObject deleteFood(@PathVariable String user_email, @PathVariable int food_id) {
			FoodRating oldVersion = foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
				if(oldVersion == null) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.deleteById(oldVersion.getRating_id());
				response = generateResponse(204, HttpStatus.OK, "Food Rating has been deleted");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that food rating in the database, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		/**
		 * helper to generate a json object response
		 * @param status
		 * @param input
		 * @param message
		 * @return a json object response
		 */
		@SuppressWarnings("unchecked")
		private JSONObject generateResponse(int status, HttpStatus input, String message) {
			JSONObject response = new JSONObject();
			response.put("status", status);
			response.put("HttpStatus", input);
			response.put("message", message);
			return response;
		}
}