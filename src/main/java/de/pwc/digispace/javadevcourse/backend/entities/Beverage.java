package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @author john
 * Implements Beverages that can be ordered by customers
 * created: 30.11.2018
 *
 */
public class Beverage extends Product {
		
	private BeverageType beverageType;
	
	private boolean containsAlcohol;

	// Constructor for fetching from the database
	public Beverage( String name, BigDecimal price, int tax, String description,
					LocalDateTime dateCreated, LocalDateTime dateEdited, 
					Boolean deprecated, BeverageType beverageType,
					boolean containsAlcohol) {
		super(name, price, tax, description, dateCreated, dateEdited, deprecated);
		this.containsAlcohol = containsAlcohol;
		this.beverageType = beverageType;
	}

	// Constructor for graphql mutation createBeverage/createFood
	public Beverage(String name, BigDecimal price, int tax, String description,
					BeverageType beverageType, Boolean containsAlcohol) {
		super(name, price, tax, description);
		this.beverageType = beverageType;
		this.containsAlcohol = containsAlcohol;
	}

	public BeverageType getBeverageType() {
		return beverageType;
	}

	// public void setBeverageType(BeverageType beverageType) {
	// 	this.beverageType = beverageType;
	// }

	public boolean isContainsAlcohol() {
		return containsAlcohol;
	}

	// public void containsAlcohol(boolean containsAlcohol) {
	// 	this.containsAlcohol = containsAlcohol;
	// }
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Beverage beverage = (Beverage) o;
		
		return this.getName().equals(beverage.getName());
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
				", containsAlcohol='" + this.isContainsAlcohol() + '\'' + 
				", beverageType='" + this.getBeverageType()  + '\'' + 
				", createdDate='" + this.getDateCreated() + '\'' + 
				", dateEdited='" + this.getDateEdited() + '\'' + 
				", description='" + this.getDescription() +
				'}';
	}
	

}
