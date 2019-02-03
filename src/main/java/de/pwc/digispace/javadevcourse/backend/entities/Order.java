package de.pwc.digispace.javadevcourse.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author john
 * Implements Orders and Invoices (datePaid!=null) for the restaurant
 * created: 30.11.2018
 *
 */

public class Order {
	
	private UUID orderId;
	private LocalDateTime dateCreated;
	private LocalDateTime dateEdited;
	private int tableNumber;
	// true=active -> order, false=inactive -> invoice
	private boolean isOpen;
	private LocalDateTime datePaid;
	private List<Food> meals;
	private List<Beverage> drinks;
	// Attributes relevant for a paid (immutable) order, null for active order
	private PaymentMethod paymentMethod;
	private List<Tax> taxes;
	private BigDecimal totalAmount;
		
	public Order(UUID orderId, LocalDateTime dateCreated, LocalDateTime dateEdited,
					int tableNumber, boolean isOpen, LocalDateTime datePaid,
					List<Food> meals, List<Beverage> drinks, PaymentMethod paymentMethod, 
					List<Tax> taxes, BigDecimal totalAmount) {
		this.orderId = orderId;
		this.dateEdited = dateEdited;
		this.dateCreated = dateCreated;
		this.tableNumber = tableNumber;
		this.isOpen = isOpen;
		this.datePaid = datePaid;
		this.meals = meals;
		this.drinks = drinks;
		this.paymentMethod = paymentMethod;
		this.taxes = taxes;
		this.totalAmount = setTotalAmount(meals, drinks);

	}
	
	public Order(UUID orderId, int tableNumber, boolean isOpen, List<Food> meals, 
					List<Beverage> drinks, PaymentMethod paymentMethod, 
					List<Tax> taxes) {
		this(orderId , null, null, tableNumber, isOpen, null, meals, drinks,
				paymentMethod, taxes, null);
	}
	
	public UUID getOrderId() {
		return orderId;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public LocalDateTime getDateEdited() {
		return dateEdited;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public List<Food> getMeals() {
		return meals;
	}

	public List<Beverage> getDrinks() {
		return drinks;
	}

	public LocalDateTime getDatePaid() {
		return datePaid;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal setTotalAmount( List<Food> meals, List<Beverage> drinks ) {
		float totalTemp = 0;
		if( drinks != null )
		{
			for (Beverage beverage : this.drinks) {
				totalTemp += beverage.getPrice().floatValue() * beverage.getAmountOrdered();
			}
		}
		if( meals != null )
		{
			for (Food food : this.meals) {
				totalTemp += food.getPrice().floatValue() * food.getAmountOrdered();
			}
		}
		
		return BigDecimal.valueOf(totalTemp);
	}

	public List<Tax> getTax() {
		return taxes;
	}

	public List<Tax> setTaxes( List<Food> meals, List<Beverage> drinks ) {
		Map<Integer, BigDecimal> taxMap = new HashMap<>();
		if( drinks != null )
		{
			for (Beverage beverage : drinks) {
				BigDecimal tempTax = taxMap.containsKey(beverage.getTax()) ? 
					taxMap.get(beverage.getTax()).add(beverage.getPrice()) : taxMap.get(beverage.getTax());
				taxMap.put(beverage.getTax(), tempTax);
			}
		}
		
		if( meals != null )
		{
			for (Food food : meals) {
				BigDecimal tempTax = taxMap.containsKey(food.getTax()) ? 
					taxMap.get(food.getTax()).add(food.getPrice()) : taxMap.get(food.getTax());
				taxMap.put(food.getTax(), tempTax);
			}
		}		
		
		List<Tax> taxes = new ArrayList<>();
		for (Map.Entry<Integer, BigDecimal> entry : taxMap.entrySet()) {
			taxes.add(new Tax(UUID.randomUUID(), entry.getKey(), entry.getValue()));
		}
		return taxes;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Order order = (Order) o;
		
		return this.getOrderId().equals(order.getOrderId());
	}
	
	@Override
	public int hashCode() {
		return this.getOrderId().hashCode();
	}
	
	@Override
	public String toString() {
		return "Beverage{id= " + this.orderId + 
				", createdDate='" + this.dateCreated + '\'' + 
				", dateEdited='" + this.dateEdited + '\'' + 
				", tableNumber='" + this.tableNumber + '\'' + 
				", isOpen='" + this.isOpen + '\'' + 
				", datePaid='" + this.datePaid + '\'' + 
				", paymentMethod='" + this.paymentMethod + '\'' +
				", totalAmount='" + this.totalAmount + '\'' + 
				", drinks='" + this.drinks + '\'' + 
				", meals='" + this.meals + 
				'}';
	}		
	
}
