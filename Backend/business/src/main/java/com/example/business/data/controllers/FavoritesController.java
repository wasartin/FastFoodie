package com.example.business.data.controllers;

import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.services.FavoritesService;

/**
 * 
 * @author Jon
 *
 */
@RestController
@RequestMapping(value="/favorites")
public class FavoritesController {
	
	@Autowired
	FavoritesService favoritesService;
	
	/**
	 * Returns an optional of favorites for the specified fav_id
	 * @param fav_id
	 * @return Optional<Favorites>
	 */
@RequestMapping(method = RequestMethod.GET, path = "/{fav_id}")
	@ResponseBody
	public Optional<Favorites> getfavorite(@PathVariable int fav_id){
		return favoritesService.getfavorite(fav_id);
	}

	/**
	 * returns an iterable for all favorites
	 * @return Iterable<Favorites> 
	 */
	@GetMapping("/all")
	public Iterable<Favorites> getAllfavoritesList() {
		return favoritesService.getAllfavorite();
	}

	/**
	 * Returns a favorite JSON object for the specified favorites_id
	 * @param favorite_id
	 * @return JSONObject for favorite
	 */
	@RequestMapping(method = RequestMethod.GET, path = "json/{favorites_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getfavoriteJSONObject(@PathVariable int favorites_id) {
		return favoritesService.getfavoriteJSONObject(favorites_id);
	}

	/**
	 * Returns all favorites in the format of a JSONObject with values ({a description}, {JSON array of favorites})
	 * @return JSONObject 
	 */
	@RequestMapping(value = "json/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFavoritesJSONObject()  {
		return favoritesService.getAllFavoritesJSONObject();
	}
	
	/**
	 * getting favorites from a specific user
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONArray getAllFavorites(@PathVariable String user_id) {
		return favoritesService.getAllFavorites(user_id);
	}
	
	/**
	 * Takes in a Favorites Object and creates a new favorite
	 * @param newUser
	 * @return a response detailing how the addition went 
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createFavorite(@RequestBody Favorites newfavorite) {
		return favoritesService.createFavorite(newfavorite);
	}
	
	/**
	 * creates a new favorite for a specific user
	 * @param user_id -the id of the user whose favorite is being added, fid -the favorite id
	 * @return a JSON object response
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createFavoriteForUser(@PathVariable String user_id, @PathVariable int fid) {
		return favoritesService.createFavoriteForUser(user_id, fid);
	}
	
	/**
	 * Deletes the favorite given their unique id
	 * @param favorite_id
	 * @return a json object response
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{favorites_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteFavorite(@PathVariable int favorites_id) {
		return favoritesService.deleteFavorite(favorites_id);
	}
	
	/**
	 * Deletes the favorite given their user and food 
	 * @param favorite_id
	 * @return a json object response 
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteFavoriteByUser(@PathVariable String user_id, @PathVariable int fid) {
		return favoritesService.deleteFavoriteByUser(user_id, fid);
	}

}