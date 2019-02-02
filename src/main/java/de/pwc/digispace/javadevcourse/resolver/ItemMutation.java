package de.pwc.digispace.javadevcourse.resolver;

import java.util.Map;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.pwc.digispace.javadevcourse.backend.ItemRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Item;

/**
 * Mutation
 */
public class ItemMutation implements GraphQLMutationResolver {

    private ItemRepository itemRepository;

    public ItemMutation (ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Map<String,Object> createItemInput) {
        String name = null;
        if (createItemInput.containsKey("name")) {
            name = (String) createItemInput.get("name");
        }
        double price = 0;
        if (createItemInput.containsKey("price")) {
            price = Double.parseDouble(createItemInput.get("price").toString());
        }

        String description = "";
        if (createItemInput.containsKey("description")) {
            description = (String) createItemInput.get("price");
        }

        Item item = new Item(UUID.randomUUID().toString(), name, price, UUID.randomUUID().toString(), description);
        itemRepository.addItem(item);
        return item;
    }

    public Item updateItem(Map<String,Object> createItemInput) {
        String name = null;
        if (createItemInput.containsKey("name")) {
            name = createItemInput.get("name").toString();
        }
        Item oldItem = new Query(itemRepository).findItemByName(name);

        Double price = (createItemInput.get("price") != null) ? 
                        Double.parseDouble(createItemInput.get("price").toString()) :
                        oldItem.getPrice();

        String description = (createItemInput.get("description") != null) ? 
                                createItemInput.get("description").toString() :
                                oldItem.getDescription();


        Item newItem = new Item(oldItem.getItemId(), name, price, 
                                oldItem.getSuperItemId(), description);
        
        itemRepository.addItem(newItem);
        
        return newItem;
    }
    
}