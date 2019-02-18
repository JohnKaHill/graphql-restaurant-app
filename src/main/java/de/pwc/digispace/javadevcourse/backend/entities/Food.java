package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;

/**
 * 
 * @author john
 * Implements Meals that can be ordered by customers
 * created: 30.11.2018
 *
 */
public class Food extends Product {
		
	private FoodType foodType;
	private boolean containsMeat;

	// Constructor for data fetching from database
	public Food(String name, String description, BigDecimal price, int tax, 
				FoodType foodType, boolean containsMeat) {
		super(name, price, tax, description);
		this.containsMeat = containsMeat;
		this.foodType = foodType;
	}

	// Constructor for graphql mutation createBeverage
	//	createBeverage(name: String!, description: String, price: Float!, 
	//  tax: Int!, containsAlcohol: Boolean!, beverageType: BeverageType!): Beverage!

	public Food(String name, String description, BigDecimal price, boolean containsMeat ) {
		super(name, description, price, containsMeat);
	}

	// Constructor for graphql mutation updateBeverage
	// 	updateBeverage(name: String!, description: String, price: Float, 
	//  deprecated: Boolean): Beverage!


	public FoodType getFoodType() {
		return foodType;
	}

	// public void setFoodType(FoodType foodType) {
	// 	this.foodType = foodType;
	// }

	public boolean containsMeat() {
		return containsMeat;
	}

	// public void containsMeat(boolean containsMeat) {
	// 	this.containsMeat = containsMeat;
	// }	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Food food = (Food) o;
		
		return this.getName().equals(food.getName());
	}
	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}
	
	// @Override
	// public String toString() {
	// 	return "Beverage{name= " + this.getName() + 
	// 			", price='" + this.getPrice() + '\'' + 
	// 			", tax='" + this.getTax() + '\'' + 
	// 			", containsAlcohol='" + this.containsMeat + '\'' + 
	// 			", beverageType='" + this.foodType  + '\'' + 
	// 			", createdDate='" + this.getDateCreated() + '\'' + 
	// 			", dateEdited='" + this.getDateEdited() + '\'' + 
	// 			", description='" + this.getDescription() +
	// 			'}';
	// }
	

}
