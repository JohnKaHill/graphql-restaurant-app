package de.pwc.digispace.javadevcourse.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.backend.OrderRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.Order;

public class Query implements GraphQLQueryResolver{
	
    private BeverageRepository beverageRepository;
    private OrderRepository orderRepository;

	public Query(BeverageRepository beverageRepository,
					OrderRepository orderRepository) {
		this.beverageRepository = beverageRepository;
		this.orderRepository = orderRepository;
	}

	public List<Order> findAllOrders() {
		Map<UUID, Order> orders = orderRepository.findAll();
		return new ArrayList(orders.values());
	}

	public Order order(UUID orderId) {
		return orderRepository.findById(orderId);
	}
	
	public List<Beverage> findAllDrinks() {
		Map<String, Beverage> beverages = beverageRepository.findAll();
		return new ArrayList(beverages.values());
	}
	
	public Beverage beverage(String name) {
		return beverageRepository.findById(name);
	}

}
