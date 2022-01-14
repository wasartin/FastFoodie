package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.FoodRating;
/**
 * This FoodRating repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 */
@Repository
public interface FoodRatingRepository extends CrudRepository<FoodRating, Integer> {

	/**
	 * For getting all the ratings a specific user has made.
	 * @param user_email
	 * @return a list of food ratings
	 */
	@Query(value ="SELECT * FROM food_rating r WHERE r.user_email =?1", nativeQuery = true)
	List<FoodRating> getFoodRatingsForUser(String user_email);
	
	/**
	 * For calling a specific food rating so that it may be edited or deleted.
	 * @param user_email
	 * @param food_id
	 * @return a specific rating a user has made on a specific food.
	 */
	@Query(value="SELECT * FROM food_rating r WHERE r.user_email = ?1 AND r.fid = ?2", nativeQuery = true)
	FoodRating getFoodRatingByUserAndFood(String user_email, int food_id);
	
	/**
	 * Another way to calculate the average of a specific food for it's rating.
	 * @param food_id
	 * @return a list of all ratings for a specific food.
	 */
	@Query(value = "SELECT rating FROM food_rating WHERE fid = ?1 AND rating > 0 ", nativeQuery = true)
	List<Integer> findAllRatingsForFood(int food_id);
}