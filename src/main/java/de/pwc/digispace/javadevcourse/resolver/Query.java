package de.pwc.digispace.javadevcourse.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.pwc.digispace.javadevcourse.backend.ItemRepository;
import de.pwc.digispace.javadevcourse.backend.entities.Item;

public class Query implements GraphQLQueryResolver{
	
	private ItemRepository itemRepository;

	public Query(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public List<Item> allItems() {
		Map<String, Item> items = itemRepository.getItems();
		return new ArrayList(items.values());
	}
	
	public Item item(String name) {
		return itemRepository.getItems().values().stream()
					.filter( item -> item.getName().equals(name))
					.findFirst()
					.orElseGet(null);
	}

}
