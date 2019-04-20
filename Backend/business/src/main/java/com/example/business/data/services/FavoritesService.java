package com.example.business.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

@Service
public class FavoritesService {
	
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";

	@Autowired 
	FavoritesRepository favoritesRepository;
	
	/**
	 * get favorites by id
	 * @param fav_id
	 * @return optional of favorite
	 */
	public Optional<Favorites> getfavorite(int fav_id){
		return favoritesRepository.findById(fav_id);
	}
	
	/**
	 * gets all favorites
	 * @return iterable of favorites
	 */
	public Iterable<Favorites> getAllfavorite() {
		return favoritesRepository.findAll();
	}
	
	/**
	 * gets favorites
	 * @param favorites_id
	 * @return json object of favorite
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getfavoriteJSONObject(int favorites_id) {
		JSONObject response = new JSONObject();
		JSONArray listOfFavorites = new JSONArray();
		List<Favorites> fullListOfFavorites = getFavorites();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getFavorites_id() == favorites_id) {
				listOfFavorites.add(fav);
			}
		}
		response.put("data", listOfFavorites);
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the favorite from the Data base so it is easier to parse into a JSONObject
	 * @return List<favorite>
	 */
	public List<Favorites> getFavorites(){
		Iterable<Favorites> uIters = favoritesRepository.findAll();
		List<Favorites> uList = new ArrayList<Favorites>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	/**
	 * takes in favorite and spits out json of that favorite
	 * @param favorite
	 * @return json object of favorite
	 */
	@SuppressWarnings("unchecked")
	public JSONObject parsefavoriteIntoJSONObject(Favorites favorite) {
		final String favorite_USER_ID_KEY = "user_id";
		final String favorite_fid_KEY = "fid";
		
		JSONObject favoriteAsJSONObj = new JSONObject();
		favoriteAsJSONObj.put(favorite_USER_ID_KEY, favorite.getUser_id());
		favoriteAsJSONObj.put(favorite_fid_KEY, favorite.getFid());
		favoriteAsJSONObj.put("favorites_id", favorite.getFavorites_id());
		return favoriteAsJSONObj;
	}
	
	/**
	 * gets all favorites
	 * @return json object of all favorites
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAllFavoritesJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfFavorites = new JSONArray();
		List<Favorites> uList = getFavorites();
		for(Favorites favorite : uList) {
			listOfFavorites.add(parsefavoriteIntoJSONObject(favorite));
		}
		toReturn.put(key1, listOfFavorites);
		return toReturn;
	}
	
	/**
	 * get all favorites
	 * @param user_id
	 * @return json object of favorites
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getAllFavorites(String user_id) {
		JSONArray usersFavorites = new JSONArray();
		List<Favorites> fullList = getFavorites();
		for(Favorites fav : fullList) {
			if(fav.getUser_id().equals(user_id)) {
				usersFavorites.add(parsefavoriteIntoJSONObject(fav));
			}
		}
		return usersFavorites;
	}
	
	/**
	 * decides if a favorites exists or not
	 * @param newFav
	 * @return boolean
	 */
	private boolean alreadyExists(Favorites newFav) {
		List<Favorites> fullListOfFavorites = getFavorites();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(newFav.getUser_id()) && fav.getFid()== newFav.getFid()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * generate a json response
	 * @param status
	 * @param input
	 * @param message
	 * @return json object response to respond to status of operation
	 */
	@SuppressWarnings("unchecked")
	public JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
	
	/**
	 * creates new favorites
	 * @param newfavorite
	 * @return json object response
	 */
	public JSONObject createFavorite(Favorites newfavorite) {
		JSONObject response;
		try {
			if(favoritesRepository.existsById(newfavorite.getFavorites_id())) {
				throw new IllegalArgumentException();
			}
			if(alreadyExists(newfavorite)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.save(newfavorite);
			response = generateResponse(204, HttpStatus.OK, "favorite has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "favorite might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * creates new favorite
	 * @param user_id
	 * @param fid
	 * @return json object response
	 */
	public JSONObject createFavoriteForUser(String user_id, int fid) {
		Favorites newFav = new Favorites(user_id, fid);
		JSONObject response;
		try {
			if(alreadyExists(newFav)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.save(newFav);
			response = generateResponse(204, HttpStatus.OK, "favorite has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "favorite might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * deletes a specific favorite
	 * @param favorites_id
	 * @return json object response
	 */
	public JSONObject deleteFavorite(int favorites_id) {
		JSONObject response;
		try {
			if(!favoritesRepository.existsById(favorites_id)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.deleteById(favorites_id);
			response = generateResponse(204, HttpStatus.OK, "favorite has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}

	/**
	 * delete a specific favorite
	 * @param user_id
	 * @param fid
	 * @return json object response
	 */
	public JSONObject deleteFavoriteByUser(String user_id, int fid) {
		JSONObject response;
		boolean found = false;
		try {
			List<Favorites> fullListOfFavorites = getFavorites();
			for(Favorites fav : fullListOfFavorites) {
				if(fav.getUser_id().equalsIgnoreCase(user_id) && fav.getFid()==fid) {
					favoritesRepository.deleteById(fav.getFavorites_id());
					found = true;
					break;
				}
			}
			if(!found) {
				throw new IllegalArgumentException();
			}
			response = generateResponse(204, HttpStatus.OK, "favorite has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
}
