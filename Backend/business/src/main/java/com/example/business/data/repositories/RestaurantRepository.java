package com.example.business.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Restaurant;

/**
 * The restaurant repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 */
@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant,Integer> {

}
