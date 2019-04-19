package com.example.business.data.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;
import com.example.business.data.services.RestaurantService;

/**
 * 
 * @author Jon
 *
 */
@RestController
@RequestMapping(value="restaurants")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	RestaurantService restService = new RestaurantService();
	
	/**
	 * returns a list of all restaurants
	 * @return list of restaurants
	 */
	@RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Restaurant> getAllRestaurantsList(){
		return restService.getAllRestaurantsList();
	}
	
	/**
	 * get all restaurants as jsonobjects
	 * @return JSONObject that has key1-> "Restaurants": value1->JSONArray of restaurants in System
	 */
	@RequestMapping(value ="json/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllRestaurantsJSONObject()  {
		return restService.getAllRestaurantsJSONObject();
	}
	
	/**
	 * returns JSON Object of restaurant whose id was specified
	 * @param restaurant_id id of desired restaurant
	 * @return JSON Object of desired restaurant
	 */
	@RequestMapping(method = RequestMethod.GET, path = "json/{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getRestaurantJSONObject(@PathVariable int restaurant_id) {
		return restService.getRestaurantJSONObject(restaurant_id);
	}
	
	/**
	 * Old method for GETting restaurant object
	 * @param restaurant_id
	 * @return restaurant object of stated id. (Not JSON Object)
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{restaurant_id}")
	@ResponseBody
	public Optional<Restaurant> getRestaurant(@PathVariable int restaurant_id){
		return restService.getRestaurant(restaurant_id);
	}

	/**
	 * adds a new restaurant to the database if said restaurant doesn't already exist
	 * @param newRestaurant
	 * @return Map of the message and http status of the post
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createRestaurant(@RequestBody Restaurant newRestaurant){
		return restService.createRestaurant(newRestaurant);
	}
	
	/**
	 * Deletes restaurant corresponding to restaurant_id in url path
	 * @param restaurant_id
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> deleteRestaurant(@PathVariable int restaurant_id) {
		return restService.deleteRestaurant(restaurant_id);
	}
	
	/**
	 * edits a restaurant, specified in the url path by its id 
	 * @param updatedRestaurant info to update restaurant with
	 * @param restaurant_id which restaurant to update
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> editRestaurant(@RequestBody Restaurant updatedRestaurant, @PathVariable int restaurant_id) {
		return restService.editRestaurant(updatedRestaurant, restaurant_id);
	}
}