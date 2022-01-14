package com.example.business.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FoodRepository;

/**
 * The Food service class is where the bulk of the business logic is. 
 * This is the layer that will interface with its repository.
 */
@Service
@Transactional
public class FoodService extends AbstractService<Food, Integer>{
	
	final String FID = "fid";
	final String FNAME="fname";
	final String PROTEIN = "protein";
	final String CARB = "carb";
	final String FAT = "fat";
	final String CALORIE = "calorie";
	final String PRICE = "price";
	final String LESS_THAN_EQ = "<";
	final String GREATHER_THAN_EQ = ">";

	@Autowired
	FoodRepository foodRepository;

	
	public Page<Food> listWithKeywordAndOrdering(String keyword, Pageable pageable){
		return foodRepository.getFoodListWithKeyword(keyword, pageable);
	}
	
	public Page<Food> listAllByPage(Pageable pageable) {
		return foodRepository.findAll(pageable);
	}

	public Page<Food> findPaginated(int page, int size) {
		return foodRepository.findAll(PageRequest.of(page, size));
	}
	
	public Page<Food> findPaginatedAndSort(int page, int size, Sort sort) {
		return foodRepository.findAll(PageRequest.of(page, size, sort));
	}
	
	public Page<Food> propertySearch(String property, Sort.Direction direction, int page, int size){
		Sort howToSort = new Sort(direction, property);
		Pageable pageable = PageRequest.of(page, size, howToSort);
		return foodRepository.findAll(pageable);
	}
	
	public Page<Food> orderBy(Sort sort, int page, int size){
		Pageable pageable = PageRequest.of(page, size, sort);
		return foodRepository.findAll(pageable);
	}
	
	public Page<Food> categorySearch(String category, Pageable pageable){
		return foodRepository.getFoodListWithCategory(category, pageable);
	}
}
