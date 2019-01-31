package de.pwc.digispace.javadevcourse.backend;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.pwc.digispace.javadevcourse.backend.entities.Item;

/**
 * ItemRepository
 */
public class ItemRepository {

    private Map<String, Item> items;

    public ItemRepository() {
        items = new HashMap<>();
        items.put("1", new Item("1", "Coke", 2.99, UUID.randomUUID().toString(), "from USA"));
        items.put("2", new Item("2", "Water", 0.99, UUID.randomUUID().toString(), "from South Africa"));
        items.put("3", new Item("3", "Beer", 3.99, UUID.randomUUID().toString(), "from Germany"));
        items.put("4", new Item("4", "Wine", 10.99, UUID.randomUUID().toString(), "from France"));
    }

    public Map<String,Item> getItems() {
        return items;
    }

    public void addItem( Item item ) {
        items.put(item.getItemId(), item);
    }

}