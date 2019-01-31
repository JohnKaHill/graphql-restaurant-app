//package de.pwc.digispace.javadevcourse.backend.server;
//
//import de.pwc.digispace.javadevcourse.resolver.Mutation;
//import de.pwc.digispace.javadevcourse.resolver.Query;
//import graphql.schema.idl.RuntimeWiring;
//
//public class Wiring {
//
//	private Query queryResolver;
//	private Mutation mutationResolver;
//	
//	public Wiring(Query queryResolver, Mutation mutationResolver) {
//		super();
//		this.queryResolver = queryResolver;
//		this.mutationResolver = mutationResolver;
//	}
//
//	public RuntimeWiring buildRuntimeWiring() {
//		return RuntimeWiring.newRuntimeWiring()
//				.type("Query", typeWiring -> typeWiring
//						.dataFetcher("findAllOrders", queryResolver.findAllOrders())
//						.dataFetcher("findOrderById", queryResolver.findOrderById(orderId))
//						.dataFetcher("findAllMeals", queryResolver.findAllMeals())
//						.dataFetcher("findFoodByName", queryResolver.findFoodByName(name))
//						.dataFetcher("findAllDrinks", queryResolver.findAllDrinks())
//						.dataFetcher("findBeverageByName", queryResolver.findBeverageByName(name))
//						)
//				.type("Mutation", typeWiring -> typeWiring
//						.dataFetcher("createOrder", mutationResolver.createOrder(order))
//						.dataFetcher("updateOrder", mutationResolver.updateOrder(order))
//						.dataFetcher("deleteOrder", mutationResolver.deleteOrder(orderId))
//						.dataFetcher("createFood", mutationResolver.createFood(food))
//						.dataFetcher("updateFood", mutationResolver.createOrder(food))
//						.dataFetcher("deleteFood", mutationResolver.deleteFood(name))
//						.dataFetcher("createBeverage", mutationResolver.createBeverage(beverage))
//						.dataFetcher("updateBeverage", mutationResolver.updateBeverage(beverage))
//						.dataFetcher("deleteBeverage", mutationResolver.deleteBeverage(name))
//						)
//				.type("Order", typeWiring -> typeWiring
//						.dataFetcher("findAllOrders", queryResolver.findAllOrders()))
//				
//	}
//		
//}
