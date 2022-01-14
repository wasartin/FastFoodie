package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.entities.Food;
import com.example.business.data.services.FavoritesService;
import com.example.business.data.services.FoodService;

/**
 * This class follows along with a Backend for frontend (BFF) design pattern. It combines and does more 
 * for the front end, so they can do less.
 *
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {
	
	@Autowired
	FoodService foodServ;
	
	@Autowired
	FavoritesService favServ;

	/**
	 * This simplified method returns a List of food object that a user likes.
	 * @param user_email
	 * @return List of food objects that are a users's favorite
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/favorites/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Food> getUsersFavoriteFoods(@PathVariable String user_id){
		List<Favorites> usersFavs = favServ.getAllFavoritesForUser(user_id);
		List<Food> result = new ArrayList<Food>();
		for(Favorites fav : usersFavs) {
			result.add(foodServ.getEntityByID(fav.getFid()).get());
		}
		return result;
	}
}