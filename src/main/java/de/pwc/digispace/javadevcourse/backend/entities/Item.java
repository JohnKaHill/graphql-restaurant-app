package de.pwc.digispace.javadevcourse.backend.entities;

/**
 * 
 * @author john
 * Implements Item that can be ordered by customers
 * created: 30.01.2019
 *
 */
public class Item extends SuperItem{
		
	private final String itemId;
	private final String name;
	private final Double price;
    
	public Item( String itemId, String name, Double price, String superItemId, String description) {
		super(superItemId, description);
		this.itemId = itemId;
		this.name = name;
		this.price = price;
	}

    public String getName() {
        return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getItemId() {
		return itemId;
	}

}
