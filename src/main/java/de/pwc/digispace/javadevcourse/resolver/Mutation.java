package de.pwc.digispace.javadevcourse.resolver;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.backend.OrderRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.BeverageType;
import de.pwc.digispace.javadevcourse.backend.entities.Order;
import de.pwc.digispace.javadevcourse.backend.entities.PaymentMethod;

/**
 * Mutation
 */
public class Mutation implements GraphQLMutationResolver {

    private BeverageRepository beverageRepository;
    private OrderRepository orderRepository;

    public Mutation (BeverageRepository beverageRepository,
                        OrderRepository orderRepository) {
        this.beverageRepository = beverageRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder( Map<String, Object> createOrderInput )
    {
        UUID orderId = UUID.randomUUID();
        int tableNumber = createOrderInput.containsKey("tableNumber") ?
            Integer.parseInt(createOrderInput.get("tableNumber").toString()) : 0;

        orderRepository.add(new Order(orderId, tableNumber, true, null, null,
            PaymentMethod.APPLEPAY, null));

        return orderRepository.findById(orderId);
    }

    public Order updateOrder( UUID orderId, Map<String, Object> updateOrderInput )
    {
        Order oldOrder = orderRepository.findById(orderId);

        int tableNumber = updateOrderInput.get("tableNumber") != null ?
            Integer.parseInt(updateOrderInput.get("tableNumber").toString()) :
            oldOrder.getTableNumber();

        orderRepository.update( new Order(orderId, tableNumber, oldOrder.getIsOpen(),
            oldOrder.getMeals(), oldOrder.getDrinks(), oldOrder.getPaymentMethod(),
            oldOrder.getTaxes() ));
        
        return orderRepository.findById(orderId);
    }

    public Boolean deleteOrder( UUID orderId )
    {
        return orderRepository.delete(orderId);
    }

    public Beverage createBeverage(Map<String,Object> createBeverageInput,
        BeverageType beverageType, Boolean containsAlcohol)
    {
        String name = createBeverageInput.containsKey("name") ? 
            (String) createBeverageInput.get("name") : null;

        BigDecimal price = createBeverageInput.containsKey("price") ?
            new BigDecimal(createBeverageInput.get("price").toString()) : new BigDecimal(0);

        int tax = createBeverageInput.containsKey("tax") ?
            Integer.parseInt(createBeverageInput.get("tax").toString()) : 19;

        String description = createBeverageInput.containsKey("description") ?
            (String) createBeverageInput.get("description") : "so fresh!";
        
        beverageRepository.add(new Beverage( name, price, tax, description, false,
            beverageType, containsAlcohol));

        return beverageRepository.findById(name);
    }

    public Beverage updateBeverage(Map<String,Object> productInput)
    {
        String name = null;
        if (productInput.containsKey("name")) {
            name = productInput.get("name").toString();
        }
        Beverage oldBeverage = beverageRepository.findById(name);

        BigDecimal price = (productInput.get("price") != null) ? 
            new BigDecimal(productInput.get("price").toString()) :
            oldBeverage.getPrice();

        String description = (productInput.get("description") != null) ? 
            productInput.get("description").toString() :
            oldBeverage.getDescription();

        beverageRepository.update(new Beverage(name, price, oldBeverage.getTax(), 
            description, false, oldBeverage.getBeverageType(),
            oldBeverage.isContainsAlcohol()));
        
        return beverageRepository.findById(name);
}

    public Boolean deleteBeverage( String name )
    {
        return beverageRepository.delete(name);
    }
    
}