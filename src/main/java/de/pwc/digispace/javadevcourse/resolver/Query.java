package de.pwc.digispace.javadevcourse.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Beverage;

public class Query implements GraphQLQueryResolver{
	
    private BeverageRepository beverageRepository;

	public Query(BeverageRepository beverageRepository) {
		this.beverageRepository = beverageRepository;
	}
	
	public List<Beverage> findAllDrinks() {
		Map<String, Beverage> beverages = beverageRepository.findAll();
		return new ArrayList(beverages.values());
	}
	
	public Beverage beverage(String name) {
		return beverageRepository.findById(name);
	}

}
