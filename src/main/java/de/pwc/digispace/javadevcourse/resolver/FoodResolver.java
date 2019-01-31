package de.pwc.digispace.javadevcourse.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.pwc.digispace.javadevcourse.backend.FoodRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Food;

public class FoodResolver implements GraphQLResolver<Food> {
	
	private FoodRepository foodRepository;
	
	public FoodResolver(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}
	
	public List<Food> findAllMeals() {
		return foodRepository.findAll();
	}
	
	public Food findFoodByName(String name) {
		return foodRepository.findById(name);
	}

}
