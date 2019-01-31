package de.pwc.digispace.javadevcourse.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Beverage;

public class BeverageResolver implements GraphQLResolver<Beverage>{

	private BeverageRepository beverageRepository;
		
	public List<Beverage> findAllDrinks() {
		return beverageRepository.findAll();
	}
	
	public Beverage findBeverageByName(String name) {
		return beverageRepository.findById(name);
	}
	
	public Beverage createBeverage( Beverage beverage ) {
		beverageRepository.add(beverage);
		return beverage;
	}
	
}
