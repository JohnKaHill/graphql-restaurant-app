package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @author john
 * Implements Product super class for Food, Drinks, Merchandise
 * created: 30.11.2018
 * edited: 1.2.2019
 *
 */
public class Product {
		
	private String name;	
	private BigDecimal price;
	private int tax;
	private String description;
	private LocalDateTime dateCreated;
	private LocalDateTime dateEdited;
	private boolean deprecated;
	private int amountOrdered;

	// Constructor for fetching from the database
	public Product( String name, BigDecimal price, int tax, String description,
					LocalDateTime dateCreated, LocalDateTime dateEdited, 
					Boolean deprecated) {
		this.name = name;
		this.price = price;
		this.tax = tax;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateEdited = dateEdited;
		this.deprecated = deprecated;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getTax() {
		return tax;
	}

	public LocalDateTime getDateEdited() {
		return dateEdited;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public boolean isDeprecated() {
		return deprecated;
	}

	public int getAmountOrdered() {
		return amountOrdered;
	}

	public void setAmountOrdered(int amountOrdered) {
		this.amountOrdered = amountOrdered;
	}
}
