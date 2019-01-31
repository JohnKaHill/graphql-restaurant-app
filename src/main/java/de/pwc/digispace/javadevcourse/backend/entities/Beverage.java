package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;

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

	public Beverage() {
		super();
	}

	public Beverage(String name, BigDecimal price, int tax, BeverageType beverageType, 
					boolean containsAlcohol, String description) {
		super(name, price, tax, description);
		this.containsAlcohol = containsAlcohol;
		this.beverageType = beverageType;
	}

	public Beverage(String name, BigDecimal price, boolean deprecated, String description) {
		super(name, description, deprecated, price);
	}

	public BeverageType getBeverageType() {
		return beverageType;
	}

	public void setBeverageType(BeverageType beverageType) {
		this.beverageType = beverageType;
	}

	public boolean containsAlcohol() {
		return containsAlcohol;
	}

	public void containsAlcohol(boolean containsAlcohol) {
		this.containsAlcohol = containsAlcohol;
	}
	
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
				", containsAlcohol='" + this.containsAlcohol() + '\'' + 
				", beverageType='" + this.getBeverageType()  + '\'' + 
				", createdDate='" + this.getDateCreated() + '\'' + 
				", dateEdited='" + this.getDateEdited() + '\'' + 
				", description='" + this.getDescription() +
				'}';
	}
	

}
