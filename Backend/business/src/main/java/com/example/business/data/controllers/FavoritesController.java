package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.services.FavoritesService;
import com.example.business.data.services.FoodService;

/**
 *  A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the Favorites repository.
 */
@RestController
@RequestMapping(value="/favorites")
public class FavoritesController {
	
	@Autowired
	FavoritesService favoritesService;

	/**
	 * Returns an optional of favorites for the specified fav_id
	 * @param fav_id
	 * @return Optional<Favorites> which is just one favorites object.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{fav_id}")
	@ResponseBody
	public Optional<Favorites> getfavorite(@PathVariable int fav_id){
		return favoritesService.getEntityByID(fav_id);
	}

	/**
	 * returns an iterable for all favorites
	 * @return Iterable<Favorites> of all favorites in the database
	 */
	@GetMapping("/all")
	public Iterable<Favorites> getAllfavoritesList() {
		return favoritesService.getAllEntities();
	}
	
	/**
	 * getting favorites from a specific user
	 * @param user_id
	 * @return a list of the favorites for a specific user.
	 */
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Favorites> getAllFavorites(@PathVariable String user_id) {
		return favoritesService.getAllFavoritesForUser(user_id);
	}
	
	/**
	 * Takes in a Favorites Object and creates a new favorite
	 * @param newUser
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFavorite(@RequestBody Favorites newfavorite) {
		return favoritesService.createEntity(newfavorite, newfavorite.getFavorites_id());
	}
	
	/**
	 * creates a new favorite for a specific user
	 * @param user_id -the id of the user whose favorite is being added, fid -the favorite id
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFavoriteForUser(@PathVariable String user_id, @PathVariable int fid) {
		Favorites toAdd = new Favorites();
		toAdd.setFid(fid);
		toAdd.setUser_id(user_id);
		
		
		return favoritesService.createEntity(toAdd, toAdd.getFavorites_id());
	}
	
	/**
	 * Deletes the favorite given their unique id
	 * @param favorite_id
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{favorites_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?>  deleteFavorite(@PathVariable int favorites_id) {
		return favoritesService.deleteEntityById(favorites_id);
	}
	
	/**
	 * Deletes the favorite given their user and food 
	 * @param favorite_id
	 * @return a response entity which spring turns into a json object. It contains information concerning the transaction.
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?> deleteFavoriteByUser(@PathVariable String user_id, @PathVariable int fid) {
		Favorites toDelete = favoritesService.getFavoriteByUserAndFood(user_id, fid);
		return favoritesService.deleteEntityById(toDelete.getFavorites_id());
	}

}
