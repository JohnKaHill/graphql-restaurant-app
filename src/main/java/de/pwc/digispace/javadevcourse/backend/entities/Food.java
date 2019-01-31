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

	public Food() {
		super();
	}
	
	

	public Food(String name, BigDecimal price, int tax, FoodType foodType, boolean containsMeat, String description) {
		super(name, price, tax, description);
		this.containsMeat = containsMeat;
		this.foodType = foodType;
	}
	
	public Food(String name, BigDecimal price, String description) {
		super(name, description, price);
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	public boolean containsMeat() {
		return containsMeat;
	}

	public void containsMeat(boolean containsMeat) {
		this.containsMeat = containsMeat;
	}	
	
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
	
	@Override
	public String toString() {
		return "Beverage{name= " + this.getName() + 
				", price='" + this.getPrice() + '\'' + 
				", tax='" + this.getTax() + '\'' + 
				", containsAlcohol='" + this.containsMeat + '\'' + 
				", beverageType='" + this.foodType  + '\'' + 
				", createdDate='" + this.getDateCreated() + '\'' + 
				", dateEdited='" + this.getDateEdited() + '\'' + 
				", description='" + this.getDescription() +
				'}';
	}
	

}
