package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

/**
 * The favorites service class is where the bulk of the business logic is. 
 * This is the layer that will interface with its repository.
 */
@Service
public class FavoritesService extends AbstractService<Favorites, Integer>{
	@Autowired 
	FavoritesRepository favoritesRepository;
	
	/**
	 * This call uses the Favorites repo to return a list of all the favorites for a specific user.
	 * @param user_id
	 * @return list of favorites by user
	 */
	public List<Favorites> getAllFavoritesForUser(String user_id) {
		return favoritesRepository.getAllFavoritesForUser(user_id);
	}
	
	/**
	 * This function uses the Favorites repo to return a specific favorite object either for editing or deleting it.
	 * @param user_email
	 * @param food_id
	 * @return a specific favorite object called by food id and user email
	 */
	public Favorites getFavoriteByUserAndFood(String user_email, int food_id) {
		return favoritesRepository.getFavoriteByUserAndFood(user_email, food_id);
	}

}