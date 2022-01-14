package com.example.business.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;

/**
 * The food repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 */
@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Integer>{
	
	/**
	 * Finds foods that contain the keyword
	 * @param keyword
	 * @return a list of foods that contain the keyword
	 */
	@Query(value =
			"SELECT *"
			+" FROM food"
			+" WHERE fname LIKE %?1%", nativeQuery = true)
	Page<Food> getFoodListWithKeyword(String keyword, Pageable pageable);//wondering if all args must be page

	@Query(value =
			"SELECT *"
			+" FROM food"
			+" WHERE category LIKE %?1%", nativeQuery = true)
	Page<Food> getFoodListWithCategory(String category, Pageable pageable);//wondering if all args must be page

}
