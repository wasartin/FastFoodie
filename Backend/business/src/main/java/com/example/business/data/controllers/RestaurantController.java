package com.example.business.data.controllers;

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

import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;
import com.example.business.data.services.RestaurantService;

/**
 *  A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the restaurant repository.
 */
@RestController
@RequestMapping(value="/restaurants")
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
	public Iterable<Restaurant> getAllRestaurantsList(){
		return restService.getAllEntities();
	}
	
	/**
	 * Get a specific restaurant object by calling it's specific id.
	 * @param restaurant_id
	 * @return restaurant object of stated id.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{restaurant_id}")
	@ResponseBody
	public Optional<Restaurant> getRestaurant(@PathVariable int restaurant_id){
		return restService.getEntityByID(restaurant_id);
	}

	/**
	 * adds a new restaurant to the database if said restaurant doesn't already exist
	 * @param newRestaurant
	 * @return Map of the message and http status of the post (Response entity)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant newRestaurant){
		return restService.createEntity(newRestaurant, newRestaurant.getRestaurant_id());
	}
	
	/**
	 * Deletes restaurant corresponding to restaurant_id in url path
	 * @param restaurant_id
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteRestaurant(@PathVariable int restaurant_id) {
		return restService.deleteEntityById(restaurant_id);
	}
	
	/**
	 * edits a restaurant, specified in the url path by its id 
	 * @param updatedRestaurant info to update restaurant with
	 * @param restaurant_id which restaurant to update
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> editRestaurant(@RequestBody Restaurant updatedRestaurant, @PathVariable int restaurant_id) {
		return restService.editEntity(updatedRestaurant, restaurant_id);
	}
}