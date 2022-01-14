package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.business.data.entities.Favorites;

/**
 * This Favorites repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 */
public interface FavoritesRepository extends CrudRepository<Favorites, Integer> {

	/**
	 * Due to the structure of favorites in the DB, this method iterates through and gets all the 
	 * favorite id's for a user
	 * @param user_email
	 * @return list of favorites
	 */
	@Query(value ="SELECT * FROM favorites f WHERE f.user_id =?1", nativeQuery = true)
	List<Favorites> getAllFavoritesForUser(String user_email);
	
	/**
	 * Pulls user favorite on a specific food. 
	 * @param user_email
	 * @param food_id
	 * @return favorite id
	 */
	@Query(value ="SELECT * FROM favorites f WHERE f.user_id =?1 AND f.fid = ?2", nativeQuery = true)
	Favorites getFavoriteByUserAndFood(String user_email, int food_id);
	
}

