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
	
	// Attributes relevant for an active order
	private UUID orderId;
	
	private LocalDateTime dateCreated;
	
	private LocalDateTime dateEdited;
	
	private int tableNumber;
	
	// true=active -> order, false=inactive -> invoice
	private boolean isOpen = true;
			
	private List<Food> meals;
	
	private List<Beverage> drinks;
	
	// Attributes relevant for a paid (immutable) order, null for active order
	private LocalDateTime datePaid;
	
	private PaymentMethod paymentMethod;
	
	private List<Tax> taxes;
	
	private BigDecimal totalAmount;
		
	public Order (UUID orderId) {
		super();
		this.orderId = orderId;
	}
	
	public Order(int tableNumber, List<Food> meals, List<Beverage> drinks) {
		super();
		this.orderId = UUID.randomUUID();
		this.dateCreated = LocalDateTime.now();
		this.tableNumber = tableNumber;
		this.meals = meals;
		this.drinks = drinks;
		setTotalAmount();
	}
	
	public Order(UUID orderId, int tableNumber, List<Beverage> drinks,
			List<Food> meals, PaymentMethod paymentMethod, boolean isOpen) {
		super();
		this.orderId = orderId;
		this.tableNumber = tableNumber;
		if (!isOpen) {
			this.datePaid = LocalDateTime.now();
		}
		this.isOpen = isOpen;
		this.meals = meals;
		this.drinks = drinks;
		this.paymentMethod = paymentMethod;
		setTotalAmount();
		if (this.dateCreated != null) {
			this.dateEdited = LocalDateTime.now();
		} else {
			this.dateCreated = LocalDateTime.now();
		}
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public List<Food> getMeals() {
		return meals;
	}

	public void setMeals(List<Food> meals) {
		this.meals = meals;
	}

	public List<Beverage> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<Beverage> drinks) {
		this.drinks = drinks;
	}

	public LocalDateTime getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(LocalDateTime datePaid) {
		this.datePaid = datePaid;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethodType) {
		this.paymentMethod = paymentMethodType;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getDateEdited() {
		return dateEdited;
	}

	public void setDateEdited(LocalDateTime dateEdited) {
		this.dateEdited = dateEdited;
	}

	public float getTotalAmount() {
		return totalAmount.floatValue();
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = BigDecimal.valueOf(totalAmount);
	}
	
	public void setTotalAmount() {
		float totalTemp = 0;
		for (Beverage beverage : this.drinks) {
			totalTemp += beverage.getPrice().floatValue();
		}
		for (Food food : this.meals) {
			totalTemp += food.getPrice().floatValue();
		}
		this.totalAmount = BigDecimal.valueOf(totalTemp);
	}

	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes() {
		Map<Integer, BigDecimal> map = new HashMap<>();
		
		for (Beverage beverage : this.drinks) {
			if (map.containsKey(beverage.getTax())) {
				map.put(beverage.getTax(), map.get(beverage.getTax()).add(beverage.getPrice()));
			} else {
				map.put(beverage.getTax(), map.get(beverage.getTax()));
			}
			
		}
		
		for (Food food : this.meals) {
			if (map.containsKey(food.getTax())) {
				map.put(food.getTax(), map.get(food.getTax()).add(food.getPrice()));
			} else {
				map.put(food.getTax(), map.get(food.getTax()));
			}
		}
		
		for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
			this.taxes.add(new Tax(entry.getKey(), entry.getValue()));
		}
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
