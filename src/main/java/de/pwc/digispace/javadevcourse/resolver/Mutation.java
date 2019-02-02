package de.pwc.digispace.javadevcourse.resolver;

import java.math.BigDecimal;
import java.util.Map;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.BeverageType;

/**
 * Mutation
 */
public class Mutation implements GraphQLMutationResolver {

    private BeverageRepository beverageRepository;

    public Mutation (BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    public Beverage createBeverage(Map<String,Object> createItemInput,
        BeverageType beverageType, Boolean containsAlcohol){
        String name = null;
        if (createItemInput.containsKey("name")) {
            name = (String) createItemInput.get("name");
        }
        
        BigDecimal price = new BigDecimal(0);
        if (createItemInput.containsKey("price")) {
            price = new BigDecimal(createItemInput.get("price").toString());
        }
        
        int tax = 19;
        if (createItemInput.containsKey("tax")) {
            tax = Integer.parseInt(createItemInput.get("tax").toString());
        }

        String description = "";
        if (createItemInput.containsKey("description")) {
            description = (String) createItemInput.get("description");
        }

        Beverage beverage = new Beverage( name, price, tax, description,
                                            beverageType, containsAlcohol);
        beverageRepository.add(beverage);
        return beverage;
    }

    public Beverage updateBeverage(Map<String,Object> createBeverageInput) {
        String name = null;
        if (createBeverageInput.containsKey("name")) {
            name = createBeverageInput.get("name").toString();
        }
        Beverage oldBeverage = beverageRepository.findById(name);

        BigDecimal price = (createBeverageInput.get("price") != null) ? 
                        new BigDecimal(createBeverageInput.get("price").toString()) :
                        oldBeverage.getPrice();

        String description = (createBeverageInput.get("description") != null) ? 
                                createBeverageInput.get("description").toString() :
                                oldBeverage.getDescription();

        Beverage newBeverage = new Beverage(name, price, oldBeverage.getTax(), 
                                description, oldBeverage.getBeverageType(),
                                oldBeverage.isContainsAlcohol());
        
        beverageRepository.update(newBeverage);
        
        return newBeverage;
    }

    public Boolean deleteBeverage( String name ) {
        return beverageRepository.delete(name);
    }
    
}