package de.pwc.digispace.javadevcourse.resolver;

import java.util.Map;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.pwc.digispace.javadevcourse.backend.ItemRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Item;

/**
 * Mutation
 */
public class Mutation implements GraphQLMutationResolver {

    private ItemRepository itemRepository;

    public Mutation (ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Map<String,Object> createItemInput) {
        String name = null;
        if (createItemInput.containsKey("name")) {
            name = createItemInput.get("name").toString();
        }
        double price = 0;
        if (createItemInput.containsKey("price")) {
            price = Double.parseDouble(createItemInput.get("price").toString());
        }
        
        Item item = new Item(UUID.randomUUID().toString(), name, price, UUID.randomUUID().toString(), (String) createItemInput.get("description"));
        itemRepository.addItem(item);
        return item;
    }
    
}