// package de.pwc.digispace.javadevcourse.resolver;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import com.coxautodev.graphql.tools.GraphQLResolver;

// import de.pwc.digispace.javadevcourse.backend.ItemRepository;
// import de.pwc.digispace.javadevcourse.backend.entities.Item;

// public class ItemResolver implements GraphQLResolver<Item> {
	
// 	private ItemRepository itemRepository;
	
// 	public ItemResolver(ItemRepository itemRepository) {
// 		this.itemRepository = itemRepository;
// 	}
	
// 	public List<Item> allItems() {
// 		Map<Double, Item> items = itemRepository.getItems();
// 		return new ArrayList(items.values());
// 	}
	
// 	public Item item(Double price) {
// 		return itemRepository.getItems().values().stream()
// 					.filter( item -> item.getPrice().equals(price))
// 					.findFirst()
// 					.orElseGet(null);
// 	}

// }
