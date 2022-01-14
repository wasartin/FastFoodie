package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Food;
import com.example.business.data.services.FoodService;

/**
 *  A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the Food repository.
 */
@RestController
@RequestMapping(value="/foods")
public class FoodController {

	private final int PAGE_SIZE = 10;
	
	@Autowired
	FoodService foodService;

	/**
	 * returns an optional for a specified food
	 * @param food_id
	 * @return optional<food>
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
	@ResponseBody
	public Optional<Food> getFood(@PathVariable int food_id){
		return foodService.getEntityByID(food_id);
	}
	
	/**
	 * Returns all of the food in the database to the client in pages.
	 * @param pageable
	 * @return a page of food
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	Page<Food> listAllFood(@PageableDefault(size = PAGE_SIZE) Pageable pageable){
		Page<Food> foods = foodService.listAllByPage(pageable);
		return foods;
	} 
	
	@RequestMapping(value = "/order/{keyword}", method = RequestMethod.GET)
	public Page<Food> keyWordAndOrdering(@PathVariable String keyword,
										@SortDefault Sort sort,
										@PageableDefault Pageable p) {
		Page<Food> list = foodService.listWithKeywordAndOrdering(keyword, PageRequest.of(p.getPageNumber(), p.getPageSize(), sort));
		return list;
	}
	
	@RequestMapping(value = "/search/{category}", method = RequestMethod.GET)
	public Page<Food> categoryAndOrdering(@PathVariable String category,
										@SortDefault Sort sort,
										@PageableDefault Pageable p) {
		Page<Food> list = foodService.categorySearch(category, PageRequest.of(p.getPageNumber(), p.getPageSize(), sort));
		return list;
	}

	@RequestMapping(value = "/conditionalPagination", method = RequestMethod.GET)
	public Page<Food> somethingNew(@RequestParam(value="property", required=false) String property,
									@RequestParam(value="direction", required=false) Optional<String> direction, 
									@PageableDefault Pageable p) {
		Sort.Direction wayToGo = Sort.Direction.fromString(direction.orElse("desc"));
		Page<Food> list = foodService.propertySearch(property, wayToGo, p.getPageNumber(), p.getPageSize());
		return list;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public Page<Food> getByOrdering(@SortDefault Sort sort,
									@PageableDefault Pageable p) {
		Page<Food> list = foodService.orderBy(sort, p.getPageNumber(), p.getPageSize());
		return list;
	}

	/**
	 * returns iterable for all food objects
	 * @return iterable<food>
	 */
	@GetMapping("/all")
	public Iterable<Food> getAllFoodList() {
		return foodService.getAllEntities();
	}

	/**
	 * Currently just takes food Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newFood
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFood(@RequestBody Food newFood) {
		return foodService.createEntity(newFood, newFood.getFid());
	}
	
	/**
	 * Deletes the food given their unique id
	 * @param food_id
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?> deleteFood(@PathVariable int food_id) {
		return foodService.deleteEntityById(food_id);
	}
	
	/**takes in a food object and edits the specified food to match the object taken in 
	 * @param food To edit
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> editFood(@RequestBody Food newFood, @PathVariable int food_id) {
		return foodService.editEntity(newFood, food_id);
	}

}