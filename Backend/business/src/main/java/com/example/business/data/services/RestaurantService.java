package com.example.business.data.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepo;
	
	/**
	 * Private method used for returned a list of restaurants
	 * @return List of Restaurants
	 */
	public List<Restaurant> getAllRestaurantsList(){
		Iterable<Restaurant> rIters = restaurantRepo.findAll();
		List<Restaurant> rList = new ArrayList<Restaurant>();
		rIters.forEach(rList::add);
		return rList;
	}
	/**
	 * get all restaurants as jsonobjects
	 * @return JSONObject that has key1-> "Restaurants": value1->JSONArray of restaurants in System
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAllRestaurantsJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = "data";
		JSONArray listOfRestaurants = new JSONArray(); 
		List<Restaurant> uList = getAllRestaurantsList(); 
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject(); 
			temp.put("last_updated", uList.get(i).getLast_updated());
			temp.put("restaurant_name", uList.get(i).getRestaurant_name());
			temp.put("restaurant_id", uList.get(i).getRestaurant_id()); 
			listOfRestaurants.add(temp);
		}
		toReturn.put(key1, listOfRestaurants);
		return toReturn;
	}
	/**
	 * returns JSON Object of restaurant whose id was specified
	 * @param restaurant_id id of desired restaurant
	 * @return JSON Object of desired restaurant
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getRestaurantJSONObject(int restaurant_id) {
		Optional<Restaurant> temp = restaurantRepo.findById(restaurant_id);
		JSONObject toReturn = new JSONObject();
		JSONObject restaurantInfo = new JSONObject();
		restaurantInfo.put("last_updated", temp.get().getLast_updated());
		restaurantInfo.put("restaurant_name", temp.get().getRestaurant_name());
		restaurantInfo.put("restaurant_id", temp.get().getRestaurant_id());
		toReturn.put("data", restaurantInfo);
		return toReturn;
	}
	/**
	 * Old method for GETting restaurant object
	 * @param restaurant_id
	 * @return restaurant object of stated id. (Not JSON Object)
	 */
	public Optional<Restaurant> getRestaurant(int restaurant_id){
		return restaurantRepo.findById(restaurant_id);
	}
	/**
	 * adds a new restaurant to the database if said restaurant doesn't already exist
	 * @param newRestaurant
	 * @return Map of the message and http status of the post
	 */
	public Map<String, Object> createRestaurant(Restaurant newRestaurant){
		HashMap<String, Object> response = new HashMap<>();
		try {
			if(restaurantRepo.existsById(newRestaurant.getRestaurant_id())) {
				throw new IllegalArgumentException();	
			}
			if(newRestaurant.getLast_updated() == null) {
				newRestaurant.setLast_updated(new Timestamp(System.currentTimeMillis()));
			}
			restaurantRepo.save(newRestaurant);
			response.put("status", 200);
			response.put("message",HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Restaurant might already exists or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
	/**
	 * Deletes restaurant corresponding to restaurant_id in url path
	 * @param restaurant_id
	 * @return response
	 */
	public Map<String,Object> deleteRestaurant(int restaurant_id) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(!restaurantRepo.existsById(restaurant_id)) {
				throw new IllegalArgumentException();
			}
			restaurantRepo.deleteById(restaurant_id);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Could not find that restaurant in the database or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
	/**
	 * edits a restaurant, specified in the url path by its id 
	 * @param updatedRestaurant info to update restaurant with
	 * @param restaurant_id which restaurant to update
	 * @return response
	 */
	public Map<String,Object> editRestaurant(Restaurant updatedRestaurant, int restaurant_id) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(!restaurantRepo.existsById(restaurant_id)) {
				throw new IllegalArgumentException();
			}
			if(updatedRestaurant.getLast_updated()==null) {
				updatedRestaurant.setLast_updated(new Timestamp(System.currentTimeMillis()));
			}
			if(restaurant_id != updatedRestaurant.getRestaurant_id()) {
				restaurantRepo.deleteById(restaurant_id);
			}
			restaurantRepo.save(updatedRestaurant);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Could not find that restaurant in the database or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
}