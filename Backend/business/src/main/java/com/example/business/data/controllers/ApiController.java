package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FavoritesRepository;
import com.example.business.data.repositories.FoodRepository;

/**
 * This class is designed for 
 * @author Will
 *
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {

	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	FavoritesRepository favoritesRepository;
	
	/**
	 *returns a json object for the user id of a specific favorites
	 * @param favorite_id
	 * @return a json object response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "/json/favorites/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getfavoriteJSONObject(@PathVariable String user_id) {
		JSONObject response = new JSONObject();
		JSONArray usersFavorites = new JSONArray();
		
		ArrayList<Food> result = new ArrayList<>();
		
		List<Favorites> fullListOfFavorites = getFavorites();
		List<Food> fullListOfFoods = getFoods();
		
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(user_id)) {
				usersFavorites.add(fav);
			}
		}
		
		for(int i = 0; i < usersFavorites.size(); i++) {
			for(int j = 0; j < fullListOfFoods.size(); j++)
			if(((Favorites) usersFavorites.get(i)).getFid() == fullListOfFoods.get(j).getFood_id()) {
				result.add(fullListOfFoods.get(j));
				break;
			}
		}
		response.put("data", result);
		return response;
	}
	
	/**
	 *get all of a users favorites
	 * @param favorite_id
	 * @return a json object response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "/favorites/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Food> getfavoriteFoodsByUser(@PathVariable String user_id) {
		JSONArray usersFavorites = new JSONArray();
		
		ArrayList<Food> result = new ArrayList<>();
		
		List<Favorites> fullListOfFavorites = getFavorites();
		List<Food> fullListOfFoods = getFoods();
		
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(user_id)) {
				usersFavorites.add(fav);
			}
		}
		
		for(int i = 0; i < usersFavorites.size(); i++) {
			for(int j = 0; j < fullListOfFoods.size(); j++)
			if(((Favorites) usersFavorites.get(i)).getFid() == fullListOfFoods.get(j).getFood_id()) {
				result.add(fullListOfFoods.get(j));
				break;
			}
		}
		return result;
	}

	/**
	 * helper to get all favorites
	 * @return list of all favorites
	 */
	private List<Favorites> getFavorites(){
		Iterable<Favorites> uIters = favoritesRepository.findAll();
		List<Favorites> uList = new ArrayList<Favorites>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	
	/**
	 *gets a specific food's json object by its id
	 * @param food_id
	 * @return a json object response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "food/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getFoodJSONObject(@PathVariable int food_id) {
		Optional<Food> temp = foodRepository.findById(food_id);
		JSONObject response = new JSONObject();
		response.put("data", temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the food from the Data base so it is easier to parse into a JSONObject
	 * @return List<Food>
	 */
	private List<Food> getFoods(){
		Iterable<Food> uIters = foodRepository.findAll();
		List<Food> uList = new ArrayList<Food>();
		uIters.forEach(uList::add);
		return uList;
	}
}
