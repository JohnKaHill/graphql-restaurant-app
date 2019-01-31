package de.pwc.digispace.javadevcourse.resolver;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.pwc.digispace.javadevcourse.backend.OrderRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Order;

public class OrderResolver implements GraphQLResolver<Order> {
	
	private OrderRepository orderRepository;
	
	public OrderResolver(OrderRepository orderDAO) {
		this.orderRepository = orderDAO;
	}
	
	public List<Order> findAllOrders() {
		return orderRepository.findAll();
	}
	
	public Order findOrderById(UUID orderId) {
		return orderRepository.findById(orderId);
	}
		
}